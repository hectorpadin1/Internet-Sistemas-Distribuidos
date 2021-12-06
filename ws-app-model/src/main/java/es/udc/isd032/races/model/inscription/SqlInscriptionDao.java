package es.udc.isd032.races.model.inscription;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.util.List;

public interface SqlInscriptionDao {
    public Inscription create(Connection connection, Inscription inscription);
    public List<Inscription> findUserInscriptions(Connection connection, String userEmail);
    public Inscription findInscription(Connection connection, Long inscriptionId)
            throws InstanceNotFoundException;
    public boolean alreadyInscribed(Connection connection, String email, Long raceId);
    public void updateInscription(Connection connection, Inscription inscription)
            throws InstanceNotFoundException, InputValidationException;
    public void remove(Connection connection, Long inscriptionId)
            throws InstanceNotFoundException;
}
