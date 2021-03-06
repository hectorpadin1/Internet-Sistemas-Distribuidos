package es.udc.isd032.races.model.race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc3SqlRaceDao extends AbstractRaceDao{

    @Override
    public Race create(Connection connection, Race race) {

        /* Create "queryString". */
        String queryString = "INSERT INTO Race"
                + " (city, description, raceDate, price, maxParticipants, creationDate, inscriptions)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString,
                Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */
            //starting at first index
            int i = 1;
            //insert city into database
            preparedStatement.setString(i++, race.getCity());
            //insert description into database
            preparedStatement.setString(i++, race.getDescription());
            //insert race date into database
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(race.getDate()));
            //insert price into database
            preparedStatement.setFloat(i++, race.getPrice());
            //insert max participants into database
            preparedStatement.setShort(i++, race.getMaxParticipants());
            //insert creation date into database
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(race.getCreationDate()));
            //insert number of inscriptions into database
            preparedStatement.setShort(i++, race.getInscriptions());

            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier.*/
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next())
                throw new SQLException("JDBC driver did not return generated key.");

            //raceId is autogenerated by the database
            Long raceId = resultSet.getLong(1);
            //returns the object race
            return new Race(raceId, race.getCity(), race.getDescription(), race.getDate(), race.getPrice(),
            race.getMaxParticipants(), race.getCreationDate(), race.getInscriptions());

        } catch (SQLException e) {
            //throws a runtime exception if something unexpected happened while inserting into database
            throw new RuntimeException(e);
        }
    }
}
