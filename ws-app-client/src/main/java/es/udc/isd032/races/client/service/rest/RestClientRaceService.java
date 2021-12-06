package es.udc.isd032.races.client.service.rest;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.udc.isd032.races.client.service.ClientRaceService;
import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyInscribedException;
import es.udc.isd032.races.client.service.exceptions.ClientAuthenticationException;
import es.udc.isd032.races.client.service.exceptions.ClientInscriptionOutOfTimeException;
import es.udc.isd032.races.client.service.exceptions.ClientMaxParticipantsException;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyPickedUpException;
import es.udc.isd032.races.client.service.rest.race.JsonToClientExceptionConversor;
import es.udc.isd032.races.client.service.rest.race.JsonToClientInscriptionDtoConversor;
import es.udc.isd032.races.client.service.rest.race.JsonToClientRaceDtoConversor;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class RestClientRaceService implements ClientRaceService {

    private final static String ENDPOINT_ADDRESS_PARAMETER = "RestClientRaceService.endpointAddress";
    private String endpointAddress;

    @Override
    public Long addRace(ClientRaceDto race) throws InputValidationException {

        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "races").
                    bodyStream(toInputStream(race), ContentType.create("application/json")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientRaceDtoConversor.toClientRaceDto(response.getEntity().getContent()).getRaceId();

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ClientRaceDto findRace(Long raceId) throws InstanceNotFoundException, InputValidationException {
        try {

            HttpResponse response = Request.Get(getEndpointAddress() + "races/"
                    + URLEncoder.encode(Long.toString(raceId), "UTF-8")).execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientRaceDtoConversor.toClientRaceDto(response.getEntity()
                    .getContent());

        } catch (InstanceNotFoundException | InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientRaceDto> findRaces(String date, String city) throws InputValidationException {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "races?date="
                    + URLEncoder.encode(date, "UTF-8") + "&city="
                    + URLEncoder.encode(city, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientRaceDtoConversor.toClientRaceDtos(response.getEntity()
                    .getContent());

        } catch (InputValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long addInscription(String userEmail, Long inscriptionId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException, ClientInscriptionOutOfTimeException,
            ClientAlreadyInscribedException, ClientMaxParticipantsException {
        try {

            HttpResponse response = Request.Post(getEndpointAddress() + "inscriptions").
                    bodyForm(
                            Form.form().
                                    add("email", userEmail).
                                    add("raceId", Long.toString(inscriptionId)).
                                    add("creditCardNumber", creditCardNumber).
                                    build()).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

            return JsonToClientInscriptionDtoConversor.toClientInscriptionDto(
                    response.getEntity().getContent()).getInscriptionId();

        } catch (InputValidationException | ClientInscriptionOutOfTimeException | ClientAlreadyInscribedException
                | ClientMaxParticipantsException  | InstanceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ClientInscriptionDto> findInscriptions(String userEmail) throws InputValidationException {
        try {
            HttpResponse response = Request.Get(getEndpointAddress() + "inscriptions?userEmail="
                    + URLEncoder.encode(userEmail, "UTF-8")).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_OK, response);

            return JsonToClientInscriptionDtoConversor.toClientInscriptionDtos(response.
                    getEntity().getContent());
        } catch (InputValidationException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void markPickUp(Long inscriptionId, String creditCardNumber) throws ClientAlreadyPickedUpException,
            ClientAuthenticationException, InputValidationException, InstanceNotFoundException {
        try {
            HttpResponse response = Request.Post(getEndpointAddress() + "inscriptions/" + inscriptionId + "/markPickUp").
                    bodyForm(
                            Form.form().
                                    add("creditCardNumber", creditCardNumber).
                                    build()).
                    execute().returnResponse();

            validateStatusCode(HttpStatus.SC_CREATED, response);

        } catch(ClientAlreadyPickedUpException | ClientAuthenticationException | InputValidationException
                | InstanceNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized String getEndpointAddress() {
        if (endpointAddress == null) {
            endpointAddress = ConfigurationParametersManager
                    .getParameter(ENDPOINT_ADDRESS_PARAMETER);
        }
        return endpointAddress;
    }

    private InputStream toInputStream(ClientRaceDto race) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            objectMapper.writer(new DefaultPrettyPrinter()).writeValue(outputStream,
                    JsonToClientRaceDtoConversor.toObjectNode(race));

            return new ByteArrayInputStream(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void validateStatusCode(int successCode, HttpResponse response) throws Exception {

        try {

            int statusCode = response.getStatusLine().getStatusCode();

            /* Success? */
            if (statusCode == successCode) {
                return;
            }

            /* Handler error. */
            switch (statusCode) {
                case HttpStatus.SC_BAD_REQUEST:
                    throw JsonToClientExceptionConversor.fromBadRequestErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_FORBIDDEN:
                    throw JsonToClientExceptionConversor.fromForbiddenErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_NOT_FOUND:
                    throw JsonToClientExceptionConversor.fromNotFoundErrorCode(
                            response.getEntity().getContent());

                case HttpStatus.SC_GONE:
                    throw JsonToClientExceptionConversor.fromGoneErrorCode(
                            response.getEntity().getContent());

                default:
                    throw new RuntimeException("HTTP error; status code = "
                            + statusCode);
            }

        } catch (IOException e ) {
            throw new RuntimeException(e);
        }

    }
}
