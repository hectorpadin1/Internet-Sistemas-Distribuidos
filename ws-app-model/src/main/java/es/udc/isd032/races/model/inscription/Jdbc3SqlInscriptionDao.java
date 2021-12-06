package es.udc.isd032.races.model.inscription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc3SqlInscriptionDao extends AbstractInscriptionDao {

    @Override
    public Inscription create(Connection connection, Inscription inscription) {
        /*Create "queryString" */
        String queryString = "INSERT INTO Inscription" 
                             + " (userEmail, raceId, creditCardNumber, inscriptionDate,dorsal, picked_up )"
                             + " VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                queryString, Statement.RETURN_GENERATED_KEYS)) {

            /* Fill "preparedStatement". */

            //Inserting all attributes
            int i = 1;

            //userEmail
            preparedStatement.setString(i++, inscription.getUserEmail());

            //raceId
            preparedStatement.setLong(i++, inscription.getRaceId());

            //creditCardNumber
            preparedStatement.setString(i++, inscription.getCreditCardNumber());

            //inscriptionDate
            preparedStatement.setTimestamp(i++, Timestamp.valueOf(inscription.getInscriptionDate()));

            //dorsal
            preparedStatement.setShort(i++, inscription.getDorsal() );

            //picked
            preparedStatement.setBoolean(i++, inscription.isPicked());


            /* Execute query. */
            preparedStatement.executeUpdate();

            /* Get generated identifier. */
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SQLException(
                        "JDBC driver did not return generated key.");
            }
            Long inscriptionId = resultSet.getLong(1);

            /* Return inscription. */
            return new Inscription(inscriptionId, inscription.getUserEmail(),
                                  inscription.getRaceId() , inscription.getCreditCardNumber(), 
                                  inscription.getInscriptionDate(), inscription.getDorsal(), inscription.isPicked());
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
