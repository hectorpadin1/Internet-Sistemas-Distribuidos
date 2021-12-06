import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.model.inscription.SqlInscriptionDao;
import es.udc.isd032.races.model.inscription.SqlInscriptionDaoFactory;
import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.model.race.SqlRaceDao;
import es.udc.isd032.races.model.race.SqlRaceDaoFactory;
import es.udc.isd032.races.model.service.RunFicService;
import es.udc.isd032.races.model.service.RunFicServiceFactory;
import es.udc.isd032.races.model.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.sql.DataSourceLocator;
import es.udc.ws.util.sql.SimpleDataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static es.udc.isd032.races.model.util.ModelConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class RunFicServiceTest {

    private final Long NON_EXISTANT_RACEID = -1L;

    private final String VALID_CREDIT_CARD_NUMBER = "1234567890123456";
    private final String INVALID_CREDIT_CARD_NUMBER = "";

    private final String VALID_USER_EMAIL = "email@gmail.com";
    private final String INVALID_USER_EMAIL = "";

    private static RunFicService runFicService = null;

    private static SqlRaceDao raceDao = null;
    private static SqlInscriptionDao inscriptionDao = null;

    @BeforeAll
    public static void init() {
        /*
         * Create a simple data source and add it to "DataSourceLocator" (this
         * is needed to test "es.udc.isd032.races.model.service"
         */
        DataSource dataSource = new SimpleDataSource();
        /* Add "dataSource" to "DataSourceLocator". */
        DataSourceLocator.addDataSource(RUNFIC_DATA_SOURCE, dataSource);
        runFicService = RunFicServiceFactory.getService();
        raceDao = SqlRaceDaoFactory.getDao();
        inscriptionDao = SqlInscriptionDaoFactory.getDao();
    }

    private Race getValidRace(String city) {
        return new Race(city, "Basic Description", LocalDateTime.now().plusDays(20).withNano(0),
                5.0f, MAX_PARTICIPANTS);
    }

    private Race getValidRace(String city, LocalDateTime date) {
        return new Race(city, "Basic Description", date, 5.0f, (short) 500);
    }

    private Race getValidRace() {
        return getValidRace("Coruña");
    }


    private Race createRace(Race race) {
        Race addedRace ;
        try {
            addedRace = runFicService.createRace(race);
        } catch (InputValidationException e) {
            throw new RuntimeException(e);
        }
        return addedRace;
    }

    private Inscription createInscription(String userEmail, Long raceId) throws MaxParticipantsException {
        Inscription i = null;
        try{
            i = runFicService.createInscription(raceId,userEmail,VALID_CREDIT_CARD_NUMBER);
        } catch (InstanceNotFoundException | InscriptionOutOfTimeException | InputValidationException | AlreadyInscribedException e) {
            e.printStackTrace();
        }
        return i;
    }

    private void removeRace(Long raceId) throws InstanceNotFoundException {

        DataSource dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {

            try {
                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                raceDao.remove(connection, raceId);

                /* Commit. */
                connection.commit();

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

    public void removeInscription(Long inscriptionId) throws InstanceNotFoundException {

        DataSource dataSource = DataSourceLocator.getDataSource(RUNFIC_DATA_SOURCE);

        try (Connection connection = dataSource.getConnection()) {

            try {

                /* Prepare connection. */
                connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                connection.setAutoCommit(false);

                /* Do work. */
                inscriptionDao.remove(connection, inscriptionId);

                /* Commit. */
                connection.commit();

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


    @Test
    public void testAddRaceAndFindRace() throws InputValidationException, InstanceNotFoundException {
        Race race = getValidRace();
        Race addedRace = null;

        try {// Create Race
            LocalDateTime beforeCreationDate = LocalDateTime.now().withNano(0);

            addedRace = runFicService.createRace(race);

            LocalDateTime afterCreationDate = LocalDateTime.now().withNano(0);

            // Find Race
            Race foundRace = runFicService.findRace(addedRace.getRaceId());

            assertEquals(addedRace, foundRace);
            assertEquals(foundRace.getCity(),addedRace.getCity());
            assertEquals(foundRace.getDescription(),addedRace.getDescription());
            assertEquals(foundRace.getDate(),addedRace.getDate());
            assertEquals(foundRace.getPrice(),addedRace.getPrice());
            assertEquals(foundRace.getMaxParticipants(),addedRace.getMaxParticipants());
            assertTrue((foundRace.getCreationDate().compareTo(beforeCreationDate) >= 0)
                    && (foundRace.getCreationDate().compareTo(afterCreationDate) <= 0));
            assertEquals(foundRace.getInscriptions(),addedRace.getInscriptions());

        } finally {
            // Clear Database
            if (addedRace!=null) {
                removeRace(addedRace.getRaceId());
            }
        }
    }

    @Test
    public void testFindNonExistentRace() {
        assertThrows(InstanceNotFoundException.class, () -> runFicService.findRace(NON_EXISTANT_RACEID));
    }

    @Test
    public void testAddInvalidRace() {

        // Check race city not null
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setCity(null);
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race city not empty
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setCity("");
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race description not null
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setDescription(null);
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race description not empty
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setDescription("");
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race price >= 0
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setPrice(-7f);
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race price <= MAX_PRICE
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setPrice((float) (MAX_PRICE + 1));
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race max participants > 0
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setMaxParticipants((short) 0);
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race max participants <= MAX_PARTICIPANTS
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setMaxParticipants((short) (MAX_PARTICIPANTS + 1));
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race inscriptions >= 0
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setInscriptions((short) -5);
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        // Check race inscriptions <= MAX_PARTICIPANTS
        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setInscriptions((short) (MAX_PARTICIPANTS + 1));
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });

        assertThrows(InputValidationException.class, () -> {
            Race race = getValidRace();
            race.setDate(LocalDateTime.now().minusDays(4));
            Race addedRace = runFicService.createRace(race);
            removeRace(addedRace.getRaceId());
        });
    }

    @Test
    public void updateRace() throws InstanceNotFoundException, InputValidationException, InscriptionOutOfTimeException,
            AlreadyInscribedException, MaxParticipantsException {
        Race raceToUpdate = createRace(getValidRace());
        Inscription ins = null;
        try {
            assertEquals(0,raceToUpdate.getInscriptions());
            ins = runFicService.createInscription(raceToUpdate.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
            Race raceUpdated = runFicService.findRace(raceToUpdate.getRaceId());
            assertEquals(1,raceUpdated.getInscriptions());
            assertThrows(InstanceNotFoundException.class, () -> runFicService.createInscription(NON_EXISTANT_RACEID,
                    VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER));
            raceUpdated.setCity("");
            assertThrows(InputValidationException.class, () -> runFicService.createRace(raceUpdated));
        } finally {
            if(ins != null){
                removeInscription((ins.getInscriptionId()));
            }
            removeRace(raceToUpdate.getRaceId());
        }

    }


    @Test
    public void RemoveRace() throws InstanceNotFoundException {
        Race race = createRace(getValidRace());
        removeRace(race.getRaceId());
        // Trying to delete a race twice
        assertThrows(InstanceNotFoundException.class, () -> runFicService.findRace(race.getRaceId()));
    }

    @Test
    public void testRemoveNonExistentRace() {
        assertThrows(InstanceNotFoundException.class, () -> removeRace(NON_EXISTANT_RACEID));
    }

    @Test
    public void TestFindByCityOrDate() throws InputValidationException, InstanceNotFoundException {

        List<Race> races = new LinkedList<>();
        List<Race> races1 = new LinkedList<>();
        List<Race> races2 = new LinkedList<>();
        List<Race> emptylist = new LinkedList<>();
        LocalDateTime now = LocalDateTime.now().withNano(0);
        LocalDate nowD = LocalDate.now();

        // races which will be displayed
        Race race1 = createRace(getValidRace("Coruña", now.plusMonths(1).plusDays(2)));
        races.add(race1);
        races1.add(race1);
        races2.add(race1);

        Race race2 = createRace(getValidRace("Santiago", now.plusDays(20)));
        races.add(race2);
        races1.add(race2);
        races2.add(race2);

        Race race3 = createRace(getValidRace("Coruña", now.plusMonths(3).plusDays(12)));
        races.add(race3);
        races1.add(race3);

        Race race7 = createRace(getValidRace("A Coruña", now.plusMonths(2).plusDays(2)));
        //it's different "A Coruña" from "Coruña"
        races.add(race7);
        races1.add(race7);

        Race race4 = createRace(getValidRace("Vigo", now.plusMonths(4).plusDays(16)));
        races.add(race4);
        races1.add(race4);

        try {
            List<Race> foundRaces = runFicService.findRacesByDate(nowD.plusDays(10), null);
            assertEquals(emptylist, foundRaces);

            foundRaces = runFicService.findRacesByDate(nowD.plusMonths(2), null);
            for (Race r : foundRaces)
            assertEquals(2, foundRaces.size());
            assertEquals(races2, foundRaces);

            foundRaces = runFicService.findRacesByDate(nowD.plusYears(1), null);
            assertEquals(5, foundRaces.size());
            assertEquals(races1, foundRaces);

            foundRaces = runFicService.findRacesByDate(nowD.plusMonths(2), "Santiago");
            assertEquals(1, foundRaces.size());
            assertEquals(races.get(1), foundRaces.get(0));

            foundRaces = runFicService.findRacesByDate(nowD.plusMonths(3), "Ourense");
            assertEquals(0, foundRaces.size());

        } finally {
            // Clear Database
            for (Race race : races) {
                removeRace(race.getRaceId());
            }
        }
    }

    @Test
    public void TestFindRacesByDate() {
        assertThrows(InputValidationException.class, () ->
                runFicService.findRacesByDate(LocalDate.now().minusDays(20), null));
        assertThrows(InputValidationException.class, () ->
                runFicService.findRacesByDate(LocalDate.now(), null));
        assertThrows(InputValidationException.class, () ->
                runFicService.findRacesByDate(LocalDate.now().minusDays(20), "city"));
    }

    @Test
    public void testAddAndFindInscriptions() throws InputValidationException,
            InstanceNotFoundException, InscriptionOutOfTimeException, AlreadyInscribedException,
            MaxParticipantsException {
        Race race = createRace(getValidRace());
        Inscription inscription = null, inscription2 = null;

        try {// Create Inscription

            inscription = runFicService.createInscription(race.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
            // Find Inscription
            List<Inscription> list = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(1,list.size());
            assertEquals(inscription,list.get(0));
            Inscription foundInscription = list.get(0);

            assertEquals(inscription, foundInscription);
            assertEquals(foundInscription.getInscriptionId(),inscription.getInscriptionId());
            assertEquals(foundInscription.getUserEmail(),VALID_USER_EMAIL);
            assertEquals(foundInscription.getRaceId(),inscription.getRaceId());
            assertEquals(foundInscription.getCreditCardNumber(),VALID_CREDIT_CARD_NUMBER);
            assertTrue(foundInscription.getInscriptionDate().isEqual(inscription.getInscriptionDate()));
            assertEquals(foundInscription.getDorsal(),inscription.getDorsal());
            assertEquals(foundInscription.isPicked(),inscription.isPicked());
            assertEquals((short) 1, inscription.getDorsal());
            Race foundRace = runFicService.findRace(race.getRaceId());
            assertEquals(1,foundRace.getInscriptions());

            inscription2 = runFicService.createInscription(race.getRaceId(), "mail@newmail.es",
                    VALID_CREDIT_CARD_NUMBER);
            list = runFicService.getInscriptions("mail@newmail.es");
            assertEquals((short) 2, list.get(0).getDorsal());
            foundRace = runFicService.findRace(race.getRaceId());
            assertEquals(2,foundRace.getInscriptions());

        } finally {
            // Clear Database
            if (inscription!=null && inscription2 != null) {
                removeInscription(inscription.getInscriptionId());
                removeInscription(inscription2.getInscriptionId());
            }
            removeRace(race.getRaceId());
        }
    }

    @Test
    public void testAddInvalidInscription() throws InstanceNotFoundException {

        Race race = createRace(getValidRace());
        Race race2 = createRace(getValidRace());
        try {
            assertThrows(InputValidationException.class, () -> {
                Inscription inscription = runFicService.createInscription(race.getRaceId(), VALID_USER_EMAIL,
                        INVALID_CREDIT_CARD_NUMBER);
                removeInscription(inscription.getInscriptionId());
            });
            assertThrows(InputValidationException.class, () -> {
                Inscription inscription = runFicService.createInscription(race2.getRaceId(), VALID_USER_EMAIL,
                        INVALID_CREDIT_CARD_NUMBER);
                removeInscription(inscription.getInscriptionId());
            });
        } finally {
            // Clear database
            removeRace(race.getRaceId());
            removeRace(race2.getRaceId());
        }

    }

    @Test
    public void testAddInscriptionOutOfTime() throws InstanceNotFoundException {
        Race race = createRace(getValidRace("Coruna",LocalDateTime.now().withNano(0).plusHours(1L)));
        assertThrows(InscriptionOutOfTimeException.class, () ->
                runFicService.createInscription(race.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER));
        removeRace(race.getRaceId());
    }

    @Test
    public void testAddInscriptionTwiceAndMaxParticipants() throws InstanceNotFoundException,
            InputValidationException, InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException {
        //trying to make an inscription twice
        Race race = runFicService.createRace(getValidRace());
        Inscription inscription = runFicService.createInscription(race.getRaceId(),
                VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
        assertThrows(AlreadyInscribedException.class, () ->  {
            Inscription ins = runFicService.createInscription(race.getRaceId(),
                    VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
            removeInscription(ins.getInscriptionId());
        });
        removeInscription(inscription.getInscriptionId());
        removeRace(race.getRaceId());

        Race race2 = runFicService.createRace(new Race("Coruña","desc",
                LocalDateTime.now().plusMonths(2).withNano(0), 5.0f,MAX_PARTICIPANTS,MAX_PARTICIPANTS));
        assertThrows(MaxParticipantsException.class, () ->  {
            Inscription ins = runFicService.createInscription(race2.getRaceId(), VALID_USER_EMAIL,
                    VALID_CREDIT_CARD_NUMBER);
            removeInscription(ins.getInscriptionId());
        });
        removeRace(race2.getRaceId());
    }

    @Test
    public void testAddInscriptionNonExistentRace() {
        assertThrows(InstanceNotFoundException.class, () ->
                runFicService.createInscription(NON_EXISTANT_RACEID, VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER));
    }

    @Test
    public void testFindNonExistentEmail() {
        assertThrows(InputValidationException.class, () -> runFicService.getInscriptions(INVALID_USER_EMAIL));
        assertThrows(InputValidationException.class, () -> runFicService.getInscriptions("noemail"));
    }

    @Test
    public void testRemoveNonExistentInscription() {
        assertThrows(InstanceNotFoundException.class, () -> removeInscription(0L));
    }

    @Test
    public void testFindInscriptions() throws InstanceNotFoundException, InputValidationException,
            MaxParticipantsException {
        //Add inscriptions
        List<Inscription> inscriptions = new LinkedList<>();
        List<Inscription> inscriptions2 = new LinkedList<>();
        List<Race> races = new LinkedList<>();
        List<Inscription> foundIns;
        LocalDateTime now = LocalDateTime.now().withNano(0);

        Race race1 = createRace(getValidRace("Coruna", now.plusMonths(3).plusDays(2)));
        Inscription i1 = createInscription("email@gmail.com",race1.getRaceId()) ;
        races.add(race1);
        inscriptions.add(i1);

        Race race2 = createRace(getValidRace("Ourense",now.plusMonths(4).plusDays(20)));
        Inscription i2 = createInscription("elegido@gmail.com",race2.getRaceId()) ;
        races.add(race2);
        inscriptions.add(i2);
        inscriptions2.add(i2);

        Race race3 = createRace(getValidRace("Santiago",now.plusDays(40)));
        Inscription i3 = createInscription("elegido@gmail.com",race3.getRaceId()) ;
        races.add(race3);
        inscriptions.add(i3);
        inscriptions2.add(i3);

        try {
            foundIns = runFicService.getInscriptions("email@gmail.com");
            assertEquals(i1, foundIns.get(0));

            foundIns = runFicService.getInscriptions("elegido@gmail.com");
            assertEquals(inscriptions2, foundIns);

        } finally {
            // Clear Database
            for (Inscription ins : inscriptions) {
                removeInscription((ins.getInscriptionId()));
            }
            for (Race race : races) {
                removeRace(race.getRaceId());
            }
        }
    }

    @Test
    public void testPickedUp() throws InstanceNotFoundException, InputValidationException,
            InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException,
            AuthenticationException, AlreadyPickedUpException {
        String city = "Coruna";
        Race race1, race2, race3, race4;
        Inscription i1, i2, i3, i4;
        List<Inscription> list = null;
        LocalDateTime now = LocalDateTime.now();

        race1 = runFicService.createRace(getValidRace(city, now.plusMonths(3)));
        race2 = runFicService.createRace(getValidRace(city, now.plusYears(1).plusMonths(3)));
        race3 = runFicService.createRace(getValidRace(city, now.plusMonths(4).plusHours(12)));
        race4 = runFicService.createRace(getValidRace(city, now.plusMonths(8).plusDays(12).plusHours(8)));
        i1 = runFicService.createInscription(race1.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
        i2 = runFicService.createInscription(race2.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
        i3 = runFicService.createInscription(race3.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);
        i4 = runFicService.createInscription(race4.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);

        try {
            list = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(4, list.size());
            assertEquals(list.get(0).isPicked(), false);

            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i1.getInscriptionId());
            list = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(true, list.get(0).isPicked());
            assertEquals(false, list.get(1).isPicked());
            assertEquals(false, list.get(2).isPicked());
            assertEquals(false, list.get(3).isPicked());

            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i3.getInscriptionId());
            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i4.getInscriptionId());
            list = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(true, list.get(0).isPicked());
            assertEquals(false, list.get(1).isPicked());
            assertEquals(true, list.get(2).isPicked());
            assertEquals(true, list.get(3).isPicked());
        } finally {
            for (Inscription i : list) {
                if (i != null)
                    removeInscription(i.getInscriptionId());
            }
            if (race1!=null)
                removeRace(race1.getRaceId());
            if (race2!=null)
                removeRace(race2.getRaceId());
            if (race3!=null)
                removeRace(race3.getRaceId());
            if (race4!=null)
                removeRace(race4.getRaceId());
        }
    }

    @Test
    public void testAlreadyPickedUp() throws InstanceNotFoundException, InputValidationException,
            InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException, AuthenticationException,
            AlreadyPickedUpException {
        String city = "Coruna";
        Inscription ins = null;
        Race race1 = null;
        Inscription i1;

        try {
            race1 = runFicService.createRace(getValidRace(city, LocalDateTime.now().plusMonths(4).withNano(0)));
            i1 = runFicService.createInscription(race1.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);

            List<Inscription> list = runFicService.getInscriptions(VALID_USER_EMAIL);
            ins = list.get(0);
            assertEquals(ins.isPicked(), false);

            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i1.getInscriptionId());

            list = runFicService.getInscriptions(VALID_USER_EMAIL);
            ins = list.get(0);
            assertEquals(ins.isPicked(), true);

            assertThrows(AlreadyPickedUpException.class, () ->
                    runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i1.getInscriptionId()));
        } finally {
            if (ins!=null || race1!=null) {
                removeInscription(ins.getInscriptionId());
                removeRace(race1.getRaceId());
            }
        }
    }

    @Test
    public void testPickedUpInvalidData() throws InstanceNotFoundException, InputValidationException,
            InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException {
        String city = "Coruna";
        Race race1 = null;
        Inscription i1 = null;
        long INVALID_ID = 0;

        try {
            race1 = runFicService.createRace(getValidRace(city, LocalDateTime.now().plusMonths(2)));
            i1 = runFicService.createInscription(race1.getRaceId(), VALID_USER_EMAIL, VALID_CREDIT_CARD_NUMBER);

            Inscription finalI = i1;
            assertThrows(InputValidationException.class, () ->
                    runFicService.markPickUp(INVALID_CREDIT_CARD_NUMBER, finalI.getInscriptionId()));
            assertThrows(InstanceNotFoundException.class, () ->
                    runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, INVALID_ID));
            assertThrows(AuthenticationException.class, () ->
                    runFicService.markPickUp("1234567890123400",finalI.getInscriptionId()));

        } finally {
            if (i1!=null || race1!=null) {
                removeInscription(i1.getInscriptionId());
                removeRace(race1.getRaceId());
            }
        }
    }


    @Test
    public void realSimulation() throws InstanceNotFoundException, InputValidationException,
            InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException,
            AuthenticationException, AlreadyPickedUpException {
        List<Race> rlist = new LinkedList<>();
        List<Inscription> ilist = new LinkedList<>();
        List<Inscription> userList = new LinkedList<>();
        LocalDateTime now = LocalDateTime.now().withNano(0);

        Race r1 = createRace(getValidRace());
        rlist.add(r1);
        Race r2 = createRace(getValidRace());
        rlist.add(r2);
        Race r3 = createRace(getValidRace());
        rlist.add(r3);
        Race r4 = createRace(getValidRace());
        rlist.add(r4);
        Race r5 = createRace(getValidRace("Santiago",now.plusMonths(3)));
        rlist.add(r5);

        try {
            //client starts looking for races and searched for an specified date
            assertEquals(5,runFicService.findRacesByDate(LocalDate.now().plusMonths(4), null).size());
            assertEquals(4,runFicService.findRacesByDate(LocalDate.now().plusMonths(4), "Coruña").size());
            //decides to participate in all races in coruña

            Long id1 = r1.getRaceId();
            assertThrows(InputValidationException.class, () -> {    //user makes a mistake introducing an unvalid credit card number
                Inscription ins = runFicService.createInscription(id1,VALID_USER_EMAIL,INVALID_CREDIT_CARD_NUMBER);
                removeInscription(ins.getInscriptionId());
            });

            Inscription i1 = runFicService.createInscription(r1.getRaceId(),VALID_USER_EMAIL,VALID_CREDIT_CARD_NUMBER);
            userList.add(i1);ilist.add(i1);  //wants to participate into race1

            //another user starts making inscriptions at the same time
            Inscription i12 = runFicService.createInscription(r1.getRaceId(),
                    "user@gmail.com",VALID_CREDIT_CARD_NUMBER);
            ilist.add(i12);
            r1 = runFicService.findRace(r1.getRaceId());
            assertEquals(2,r1.getInscriptions());

            Inscription i2 = runFicService.createInscription(r2.getRaceId(),VALID_USER_EMAIL,VALID_CREDIT_CARD_NUMBER);
            userList.add(i2);ilist.add(i2);  //also in the rest of races...
            r2 = runFicService.findRace(r2.getRaceId());
            assertEquals(1,r2.getInscriptions());
            Inscription i3 = runFicService.createInscription(r3.getRaceId(),VALID_USER_EMAIL,VALID_CREDIT_CARD_NUMBER);
            userList.add(i3);ilist.add(i3);
            r3 = runFicService.findRace(r3.getRaceId());
            assertEquals(1,r3.getInscriptions());
            assertThrows(AlreadyInscribedException.class, () -> {    //user makes a mistake registering itself into the previous race again
                Inscription ins = runFicService.createInscription(id1,VALID_USER_EMAIL,VALID_CREDIT_CARD_NUMBER);
                removeInscription(ins.getInscriptionId());
            });
            Inscription i13 = runFicService.createInscription(r3.getRaceId(),"user@gmail.com",
                    VALID_CREDIT_CARD_NUMBER);
            ilist.add(i13);
            r3 = runFicService.findRace(r3.getRaceId());
            assertEquals(2,r3.getInscriptions());
            Race finalR1 = r4;
            assertThrows(InputValidationException.class, () -> {    //also introduced a invalid mail
                Inscription ins = runFicService.createInscription(finalR1.getRaceId(),
                        "this is not an email",VALID_CREDIT_CARD_NUMBER);
                removeInscription(ins.getInscriptionId());
            });
            Inscription i4 = runFicService.createInscription(r4.getRaceId(),VALID_USER_EMAIL,VALID_CREDIT_CARD_NUMBER);
            userList.add(i4);ilist.add(i4);
            r4 = runFicService.findRace(r4.getRaceId());
            assertEquals(1,r4.getInscriptions());

            assertEquals(userList,runFicService.getInscriptions(VALID_USER_EMAIL));

            //now, client starts to pick up dorsal
            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i1.getInscriptionId());
            List<Inscription> inscriptions = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(true,inscriptions.get(0).isPicked());
            assertEquals(false,inscriptions.get(1).isPicked());
            assertEquals(false,inscriptions.get(2).isPicked());
            assertEquals(false,inscriptions.get(3).isPicked());

            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i2.getInscriptionId());
            inscriptions = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(true,inscriptions.get(0).isPicked());
            assertEquals(true,inscriptions.get(1).isPicked());
            assertEquals(false,inscriptions.get(2).isPicked());
            assertEquals(false,inscriptions.get(3).isPicked());
            //makes another mistake
            assertThrows(AlreadyPickedUpException.class, () -> runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER,
                    i2.getInscriptionId()));

            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i3.getInscriptionId());
            runFicService.markPickUp(VALID_CREDIT_CARD_NUMBER, i4.getInscriptionId());
            inscriptions = runFicService.getInscriptions(VALID_USER_EMAIL);
            assertEquals(true,inscriptions.get(0).isPicked());
            assertEquals(true,inscriptions.get(1).isPicked());
            assertEquals(true,inscriptions.get(2).isPicked());
            assertEquals(true,inscriptions.get(3).isPicked());
            
        } finally {
            for (Inscription i : ilist) {
                removeInscription(i.getInscriptionId());
            }
            for (Race r : rlist) {
                removeRace(r.getRaceId());
            }
        }

    }
}
