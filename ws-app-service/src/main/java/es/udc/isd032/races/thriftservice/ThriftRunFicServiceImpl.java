package es.udc.isd032.races.thriftservice;

import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.model.service.RunFicServiceFactory;
import es.udc.isd032.races.model.service.exceptions.AlreadyInscribedException;
import es.udc.isd032.races.model.service.exceptions.InscriptionOutOfTimeException;
import es.udc.isd032.races.model.service.exceptions.MaxParticipantsException;
import es.udc.isd032.races.thrift.*;
import es.udc.isd032.races.model.service.exceptions.AlreadyPickedUpException;
import es.udc.isd032.races.model.service.exceptions.AuthenticationException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.validation.PropertyValidator;
import org.apache.thrift.TException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ThriftRunFicServiceImpl implements ThriftRunFicService.Iface {

    @Override
    public long addRace(ThriftRaceDto raceDto) throws ThriftInputValidationException, TException {

        Race race = RaceToThriftRaceDtoConversor.toRace(raceDto);

        try {
            return RunFicServiceFactory.getService().createRace(race).getRaceId();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public ThriftRaceDto findRace(long raceId) throws TException {
        Race race = null;
        try {
            PropertyValidator.validateMandatoryString("raceId", Long.toString(raceId));
            race = RunFicServiceFactory.getService().findRace(raceId);
        } catch (InputValidationException | InstanceNotFoundException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

        return RaceToThriftRaceDtoConversor.toThriftRaceDto(race);
    }

    @Override
    public List<ThriftRaceDto> findRaces(String date, String city) throws TException {

        if (city == null) {
            throw new ThriftInputValidationException("Invalid Request: city is null.");
        }
        LocalDate raceDate = null;
        try {
            raceDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new ThriftInputValidationException("Invalid Request: Field date cannot be null, and " +
                    "must represent a valid date.");
        }
        if (city == null)
            throw new ThriftInputValidationException("Invalid Request: city is null.");
        List<Race> races = null;
        try {
            races = RunFicServiceFactory.getService().findRacesByDate(raceDate, city);
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

        return RaceToThriftRaceDtoConversor.toThriftRaceDtos(races);

    }

    @Override
    public long createInscription(long raceId, String userEmail, String creditCardNumber)
            throws ThriftInputValidationException, ThriftInscriptionOutOfTimeException,
            ThriftAlreadyInscribedException, ThriftMaxParticipantsException, TException {

        try {

            Inscription inscription = RunFicServiceFactory.getService().createInscription(raceId,userEmail,creditCardNumber);
            return inscription.getInscriptionId();

        } catch (InputValidationException | InstanceNotFoundException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (MaxParticipantsException e) {
            throw new ThriftMaxParticipantsException(e.getRaceId());
        } catch (AlreadyInscribedException e) {
            throw new ThriftAlreadyInscribedException(e.getEmail(),e.getRaceId());
        } catch (InscriptionOutOfTimeException e) {
            throw new ThriftInscriptionOutOfTimeException(e.getRaceId(),e.getDate().toString());
        }
    }

    @Override
    public List<ThriftInscriptionDto> findIncription(String userEmail) throws TException {
        try {
            List<Inscription> inscriptions = RunFicServiceFactory.getService().getInscriptions(userEmail);
            return InscriptionToThriftInscriptionDtoConversor.toThriftInscriptionDtos(inscriptions);

        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        }

    }

    @Override
    public void markPickUp(long inscriptionId, String creditCardNumber) throws TException{
        try {
            throw new UnsupportedOperationException("Method markPickUp is not supported yet for thrift.");
            /* Does not work properly
            RunFicServiceFactory.getService().markPickUp(creditCardNumber, inscriptionId);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InputValidationException e) {
            throw new ThriftInputValidationException(e.getMessage());
        } catch (AlreadyPickedUpException alreadyPickedUp) {
            throw new ThriftAlreadyInscribedException();
        }*/
        } catch (UnsupportedOperationException e) {
            throw new ThriftUnsupportedOperationException(e.getMessage());
        }

    }
}
