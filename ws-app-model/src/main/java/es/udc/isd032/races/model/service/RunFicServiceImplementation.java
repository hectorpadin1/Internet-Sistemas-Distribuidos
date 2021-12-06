package es.udc.isd032.races.model.service;

import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.model.inscription.SqlInscriptionDao;
import es.udc.isd032.races.model.inscription.SqlInscriptionDaoFactory;
import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.model.race.SqlRaceDao;
import es.udc.isd032.races.model.race.SqlRaceDaoFactory;
import es.udc.isd032.races.model.service.exceptions.AlreadyInscribedException;
import es.udc.isd032.races.model.service.exceptions.AlreadyPickedUpException;
import es.udc.isd032.races.model.service.exceptions.InscriptionOutOfTimeException;
import es.udc.isd032.races.model.service.exceptions.MaxParticipantsException;
import es.udc.isd032.races.model.service.exceptions.AuthenticationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;

import es.udc.ws.util.sql.DataSourceLocator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import static es.udc.isd032.races.model.util.ModelConstants.*;

public class RunFicServiceImplementation implements RunFicService {
    private final DataSource dataSource;
    private SqlRaceDao raceDao = null;
    private SqlInscriptionDao inscriptionDao = null;

    public RunFicServiceImplementation() {
        this.dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);
        this.raceDao = SqlRaceDaoFactory.getDao();
        this.inscriptionDao = SqlInscriptionDaoFactory.getDao();
    }

    private void validateRace(Race race) throws InputValidationException {

        PropertyValidator.validateMandatoryString("city", race.getCity());
        PropertyValidator.validateMandatoryString("description", race.getDescription());
        PropertyValidator.validateDouble("price", race.getPrice(), 0, MAX_PRICE);
        PropertyValidator.validateLong("maxParticipants", race.getMaxParticipants(),
                1, MAX_PARTICIPANTS);
        PropertyValidator.validateLong("inscriptions", race.getInscriptions(),
                0, MAX_PARTICIPANTS);
        if (race.getDate().isBefore(LocalDateTime.now())) {
            throw new InputValidationException("Field 'raceDate' must be a future date.");
        }
    }

    private void validateMail(String mail) throws InputValidationException {
        PropertyValidator.validateMandatoryString("email", mail);
        if (!mail.contains("@"))
            throw new InputValidationException("Please, introduce a valid email.");
    }

    @Override
    public Race createRace(Race race) throws InputValidationException {
        //validates the given race, if it's not ok throws an InputValidationException
        validateRace(race);
        //we have to set the creation date
        race.setCreationDate(LocalDateTime.now());

        try (Connection connection = dataSource.getConnection()) {
            try {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                Race createdRace = raceDao.create(connection, race);

                /* Commit.*/
                connection.commit();

                return createdRace;

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Race findRace(Long raceId) throws InstanceNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            return raceDao.find(connection, raceId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findRacesByDate(LocalDate date, String city) throws InputValidationException {
        /* If the field city is introduced, cannot be empty. */
        if (city!=null)
            PropertyValidator.validateMandatoryString("city", city);

        try (Connection connection = dataSource.getConnection()) {
            LocalDate now = LocalDate.now();
            /* Cannot introduce a past date. */
            if (date.isAfter(now)) {
                return raceDao.findRaces(connection, date, city);
            } else {
                throw new InputValidationException("The field 'date' must be a future date.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Inscription createInscription(Long raceId, String userEmail, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException, InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException {

        PropertyValidator.validateCreditCard(creditCardNumber);
        validateMail(userEmail);

        try (Connection connection = dataSource.getConnection()) {
            try {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                //Lanza InstanceNotFoundException
                Race race = raceDao.find(connection, raceId);
                if (inscriptionDao.alreadyInscribed(connection, userEmail, raceId))
                    throw new AlreadyInscribedException(userEmail, raceId);
                if (race.getInscriptions() >= race.getMaxParticipants())
                    throw new MaxParticipantsException(raceId);
                race.setInscriptions((short)(race.getInscriptions() + 1));
                short dorsal = race.getInscriptions();
                Inscription inscription;
                LocalDateTime now = LocalDateTime.now().withNano(0);
                if (race.getDate().isAfter(now.plusHours(24L))) {
                    inscription = inscriptionDao.create(connection, new Inscription(userEmail, raceId,
                            creditCardNumber, LocalDateTime.now().withNano(0) , dorsal, false));
                } else {
                    throw new InscriptionOutOfTimeException(raceId, race.getDate().minusHours(24L));
                }
                raceDao.update(connection,race);
                /* Commit. */
                connection.commit();
                return inscription;

            } catch (InstanceNotFoundException e) {
                connection.commit();
                throw e;
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (RuntimeException | Error e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscription> getInscriptions(String userEmail) throws InputValidationException {

        validateMail(userEmail);

        try (Connection connection = dataSource.getConnection()) {

            List<Inscription> inscriptions = inscriptionDao.findUserInscriptions(connection, userEmail);

            return inscriptions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void markPickUp(String creditCardNumber, Long code) throws InputValidationException, InstanceNotFoundException, AlreadyPickedUpException, AuthenticationException {

        DataSource dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);

        PropertyValidator.validateCreditCard(creditCardNumber);

        try (Connection connection = dataSource.getConnection()) {

            Inscription inscription = inscriptionDao.findInscription(connection,code);
            if (!inscription.getCreditCardNumber().equals(creditCardNumber))
                throw new AuthenticationException(creditCardNumber,code);
            if (inscription.isPicked())
                throw new AlreadyPickedUpException(inscription.getDorsal(),inscription.getRaceId(),
                        inscription.getInscriptionId());
            inscriptionDao.updateInscription(connection, inscription);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}