package es.udc.isd032.races.model.race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import es.udc.ws.util.exceptions.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRaceDao implements SqlRaceDao {

    @Override
    public Race find(Connection connection, Long raceId) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "SELECT city, description, raceDate, price, maxParticipants, "
                + "creationDate, inscriptions FROM Race WHERE raceId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, raceId.longValue());

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(raceId, Race.class.getName());
            }

            /* Get results. */
            i = 1;
            String city = resultSet.getString(i++);
            String description = resultSet.getString(i++);
            Timestamp raceDateAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime raceDate = raceDateAsTimestamp.toLocalDateTime();
            Float price = resultSet.getFloat(i++);
            short maxParticipants = resultSet.getShort(i++);
            Timestamp creationDateAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime creationDate = creationDateAsTimestamp.toLocalDateTime();
            short inscriptions = resultSet.getShort(i++);

            /* Return race. */
            return new Race(raceId, city, description, raceDate, price, maxParticipants,
                    creationDate, inscriptions);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Race> findRaces(Connection connection, LocalDate date, String City) {

        /* Create "queryString". */
        String queryString = "SELECT raceId, city, description, raceDate, price, maxParticipants, " +
                "creationDate, inscriptions FROM Race";
        queryString += " WHERE raceDate between '" + LocalDate.now().toString() +
                "' AND '" + date.toString() + "'";
        if (City != null)
            queryString += " AND city = '" + City + "'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            /* Read races. */
            List<Race> races = new ArrayList<Race>();
            /* Gets all the races in the result */
            while (resultSet.next()) {

                int i = 1;
                Long raceId = Long.valueOf(resultSet.getLong(i++));
                String city = resultSet.getString(i++);
                String description = resultSet.getString(i++);
                Timestamp raceDateAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime raceDate = raceDateAsTimestamp.toLocalDateTime();
                float price = resultSet.getFloat(i++);
                short maxParticipants = resultSet.getShort(i++);
                Timestamp creationDateAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime creationDate = creationDateAsTimestamp.toLocalDateTime();
                short inscriptions = resultSet.getShort(i++);
                /* each race is added to a list of races */
                races.add(new Race(raceId, city, description, raceDate, price, maxParticipants,
                        creationDate, inscriptions));

            }

            /* Returns the list of races. */
            return races;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Connection connection, Race race)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "UPDATE Race"
                + " SET city = ?, description = ?, "
                + "price = ?, maxParticipants = ?, inscriptions = ? WHERE raceId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {


            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, race.getCity());
            preparedStatement.setString(i++, race.getDescription());
            preparedStatement.setFloat(i++, race.getPrice());
            preparedStatement.setShort(i++, race.getMaxParticipants());
            preparedStatement.setShort(i++, race.getInscriptions());
            preparedStatement.setLong(i++, race.getRaceId());

            /* Execute query. */
            int updatedRows = preparedStatement.executeUpdate();
            /* Bf update a race, we've to find it. So if the race does not exist, find will throw a InstanceNotFound, which
            means update will never throw a InstanceNotFound  but we might need this in the future*/
            if (updatedRows == 0) {
                throw new InstanceNotFoundException(race.getRaceId(),
                        Race.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void remove(Connection connection, Long raceId) throws InstanceNotFoundException {
        /* Create "queryString". */
        String queryString = "DELETE FROM Race WHERE" + " raceId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, raceId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(raceId, Race.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
