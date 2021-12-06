package es.udc.isd032.races.model.service;

import java.time.LocalDate;
import java.util.List;

import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.model.service.exceptions.*;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public interface RunFicService {
    public Race createRace(Race race) throws InputValidationException;
    public Race findRace(Long raceId) throws InstanceNotFoundException;
    public List<Race> findRacesByDate(LocalDate date, String city) throws InputValidationException;
    public Inscription createInscription(Long raceId, String userEmail, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException, InscriptionOutOfTimeException, AlreadyInscribedException, MaxParticipantsException;
    public List<Inscription> getInscriptions(String userEmail) throws InputValidationException;
    public void markPickUp(String creditCardNumber, Long code) throws InputValidationException, InstanceNotFoundException, AlreadyPickedUpException, AuthenticationException;
}