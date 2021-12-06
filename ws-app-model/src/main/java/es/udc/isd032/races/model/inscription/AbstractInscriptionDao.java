package es.udc.isd032.races.model.inscription;

import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.SQLException;

import java.time.LocalDateTime;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractInscriptionDao implements SqlInscriptionDao {

    public Inscription findInscription(Connection connection, Long inscriptionId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "SELECT userEmail, raceId,"
                + " creditCardNumber, inscriptionDate, dorsal, picked_up FROM Inscription WHERE inscriptionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscriptionId);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(inscriptionId,
                        Inscription.class.getName());
            }

            /* Get results. */
            i = 1;
            String userEmail = resultSet.getString(i++);
            Long raceId = resultSet.getLong(i++);
            String creditCardNumber = resultSet.getString(i++);
            Timestamp inscriptionDateAsTimestamp = resultSet.getTimestamp(i++);
            LocalDateTime inscriptionDate = inscriptionDateAsTimestamp != null
                    ? inscriptionDateAsTimestamp.toLocalDateTime().withNano(0)
                    : null;
            short dorsal = resultSet.getShort(i++);
            boolean picked_up = resultSet.getBoolean(i++);

            /* Add inscription. */
            return new Inscription(inscriptionId, userEmail, raceId, creditCardNumber,
                    inscriptionDate, dorsal, picked_up);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Inscription> findUserInscriptions(Connection connection, String userEmail) {

        /* Create "queryString". */
        String queryString = "SELECT inscriptionId, userEmail, raceId,"
                + " creditCardNumber, inscriptionDate, dorsal, picked_up FROM Inscription WHERE userEmail = ?";

        /*Create List*/
        List<Inscription> inscriptions = new LinkedList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setString(i++, userEmail);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return inscriptions;
            } else do{
                /* Get results. */
                i = 1;
                Long inscriptionId = resultSet.getLong(i++);
                userEmail = resultSet.getString(i++);
                Long raceId = resultSet.getLong(i++);
                String creditCardNumber = resultSet.getString(i++);
                Timestamp inscriptionDateAsTimestamp = resultSet.getTimestamp(i++);
                LocalDateTime inscriptionDate = inscriptionDateAsTimestamp != null
                        ? inscriptionDateAsTimestamp.toLocalDateTime().withNano(0)
                        : null;
                Short dorsal = resultSet.getShort(i++);
                boolean picked_up = resultSet.getBoolean(i++);

                /* Add inscription. */
                inscriptions.add(new Inscription(inscriptionId, userEmail, raceId, creditCardNumber, inscriptionDate, dorsal, picked_up));
            }  while (resultSet.next());

            return inscriptions;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean alreadyInscribed(Connection connection, String email, Long raceId) {

        List<Inscription> inscriptions = findUserInscriptions(connection, email);

        for (Inscription i : inscriptions) {
            if (i.getRaceId()==raceId) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void updateInscription(Connection connection, Inscription inscription) throws
            InstanceNotFoundException, InputValidationException{

        /*In the future if we expand the uses of our app we would only update the values thar are not null, for now
        * the only possibility is to update based on the inscriptionId and creditCardNumber*/

        Long inscriptionId = inscription.getInscriptionId();
        String creditCardNumber = inscription.getCreditCardNumber();

        /* Create "queryString". */
        String queryString = "SELECT inscriptionId, userEmail, raceId,"
                + " creditCardNumber, inscriptionDate, dorsal, picked_up FROM Inscription WHERE inscriptionId = ?"
                + " AND creditCardNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscriptionId);
            preparedStatement.setString(i++, creditCardNumber);

            /* Execute query. */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new InstanceNotFoundException(inscriptionId.toString() + " // " + creditCardNumber, Inscription.class.getName());
            }

            /* Get results. */
            i = 1;
            i++;
            String userEmail = resultSet.getString(i++);
            Long raceId = resultSet.getLong(i++);
            i++;
            Timestamp inscriptionDateAsTimestamp = resultSet.getTimestamp(i++);
            short dorsal = resultSet.getShort(i++);
            boolean picked_up = resultSet.getBoolean(i++);

            if(!picked_up) {
                queryString = "UPDATE Inscription"
                        + " SET inscriptionId = ?, userEmail = ?, raceId = ?,"
                        + " creditCardNumber = ?, inscriptionDate = ?, dorsal = ?, picked_up  = ? WHERE inscriptionId = ?"
                        + " AND creditCardNumber = ?";

                try (PreparedStatement updateStatement = connection.prepareStatement(queryString)) {
                    /* Fill "preparedStatement". */
                    i = 1;
                    updateStatement.setLong(i++, inscriptionId);
                    updateStatement.setString(i++, userEmail);
                    updateStatement.setLong(i++, raceId);
                    updateStatement.setString(i++, creditCardNumber);
                    updateStatement.setTimestamp(i++, inscriptionDateAsTimestamp);
                    updateStatement.setShort(i++, dorsal);
                    updateStatement.setBoolean(i++, true);
                    updateStatement.setLong(i++, inscriptionId);
                    updateStatement.setString(i++, creditCardNumber);

                    int rows = updateStatement.executeUpdate();

                    if(rows == 0) {
                        throw new RuntimeException();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new InputValidationException("Number has already been picked-up");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Connection connection, Long inscriptionId)
            throws InstanceNotFoundException {

        /* Create "queryString". */
        String queryString = "DELETE FROM Inscription WHERE" + " inscriptionId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryString)) {

            /* Fill "preparedStatement". */
            int i = 1;
            preparedStatement.setLong(i++, inscriptionId);

            /* Execute query. */
            int removedRows = preparedStatement.executeUpdate();

            if (removedRows == 0) {
                throw new InstanceNotFoundException(inscriptionId, Inscription.class.getName());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
