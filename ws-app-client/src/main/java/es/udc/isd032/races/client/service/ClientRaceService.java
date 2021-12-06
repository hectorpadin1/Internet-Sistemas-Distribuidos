package es.udc.isd032.races.client.service;

import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyInscribedException;
import es.udc.isd032.races.client.service.exceptions.ClientAuthenticationException;
import es.udc.isd032.races.client.service.exceptions.ClientInscriptionOutOfTimeException;
import es.udc.isd032.races.client.service.exceptions.ClientMaxParticipantsException;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyPickedUpException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;

import java.util.List;

public interface ClientRaceService {

    Long addRace(ClientRaceDto race) throws InputValidationException;

    ClientRaceDto findRace(Long raceId) throws InstanceNotFoundException, InputValidationException;

    List<ClientRaceDto> findRaces(String date, String city) throws InputValidationException;

    long addInscription(String userEmail, Long inscriptionId, String creditCardNumber) throws InstanceNotFoundException,
            InputValidationException, ClientInscriptionOutOfTimeException, ClientAlreadyInscribedException,
            ClientMaxParticipantsException;

    List<ClientInscriptionDto> findInscriptions(String userEmail) throws InputValidationException;

    void markPickUp(Long inscriptionId, String creditCardNumber) throws TException, ClientAlreadyPickedUpException, ClientAuthenticationException, InputValidationException, InstanceNotFoundException;
}
