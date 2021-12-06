package es.udc.isd032.races.model.race;

import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface SqlRaceDao {
    public Race create(Connection connection, Race race) throws RuntimeException;
    public Race find(Connection connection, Long raceId) throws InstanceNotFoundException;
    public List<Race> findRaces(Connection connection, LocalDate date, String city);
    public void update(Connection connection, Race race) throws InstanceNotFoundException ;
    public void remove(Connection connection, Long raceId) throws InstanceNotFoundException;
}
