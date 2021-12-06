package es.udc.isd032.races.client.service.rest.race;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyInscribedException;
import es.udc.isd032.races.client.service.exceptions.ClientAuthenticationException;
import es.udc.isd032.races.client.service.exceptions.ClientInscriptionOutOfTimeException;
import es.udc.isd032.races.client.service.exceptions.ClientMaxParticipantsException;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyPickedUpException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;

/*

Lógica de excepciones propias:

- Permanentes: InputValidationException(400), InscriptionOutOfTimeException(410), AlreadyInscribedException(403),
    MaxParticipantsException(410), AlreadyPickedUpException(403).

- Temporales: InstanceNotFoundException(404)


*/

public class JsonToClientExceptionConversor {

    private static InputValidationException toInputValidationException(JsonNode rootNode) {
        String message = rootNode.get("message").textValue();
        return new InputValidationException(message);
    }

    private static InstanceNotFoundException toInstanceNotFoundException(JsonNode rootNode) {
        String instanceId = rootNode.get("instanceId").textValue();
        String instanceType = rootNode.get("instanceType").textValue();
        return new InstanceNotFoundException(instanceId, instanceType);
    }

    private static ClientInscriptionOutOfTimeException toInscriptionOutOfTimeException(JsonNode rootNode) {
        Long raceId = rootNode.get("raceId").longValue();
        String inscriptionDateStr = rootNode.get("inscriptionDate").textValue();
        LocalDateTime inscriptionDate = null;
        if (inscriptionDateStr != null) {
            inscriptionDate = LocalDateTime.parse(inscriptionDateStr);
        }
        return new ClientInscriptionOutOfTimeException(raceId, inscriptionDate);
    }

    private static ClientAlreadyInscribedException toAlreadyInscribedException(JsonNode rootNode) {
        String userEmail = rootNode.get("userEmail").textValue();
        Long raceId = rootNode.get("raceId").longValue();
        return new ClientAlreadyInscribedException(userEmail, raceId);
    }

    private static ClientAuthenticationException toAuthenticationException(JsonNode rootNode) {
        String creditCardNumber = rootNode.get("creditCardNumber").textValue();
        Long raceId = rootNode.get("raceId").longValue();
        return new ClientAuthenticationException(creditCardNumber, raceId);
    }

    private static ClientMaxParticipantsException toMaxParticipantsException(JsonNode rootNode) {
        Long raceId = rootNode.get("raceId").longValue();
        return new ClientMaxParticipantsException(raceId);
    }
    private static ClientAlreadyPickedUpException toAlreadyPickedUpException(JsonNode rootNode) {
        Long raceId = rootNode.get("raceId").longValue();
        Long inscriptionId = rootNode.get("inscriptionId").longValue();
        short dorsal = rootNode.get("dorsal").shortValue();
        return new ClientAlreadyPickedUpException(dorsal ,raceId, inscriptionId);
    }

    public static Exception fromBadRequestErrorCode(InputStream ex) throws ParsingException {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InputValidation")) {
                    return toInputValidationException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }

    }

    public static Exception fromNotFoundErrorCode(InputStream ex) throws ParsingException {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InstanceNotFound")) {
                    return toInstanceNotFoundException(rootNode);
                } else {
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }

    }

    public static Exception fromGoneErrorCode(InputStream ex) throws ParsingException {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                if (errorType.equals("InscriptionOutOfTime")) {
                    return toInscriptionOutOfTimeException(rootNode);
                } else if(errorType.equals("MaxParticipants")) {
                        return toMaxParticipantsException(rootNode);
                } else{
                    throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }

    }

    public static Exception fromForbiddenErrorCode(InputStream ex) throws ParsingException {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(ex);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                String errorType = rootNode.get("errorType").textValue();
                switch (errorType) {
                    case "AlreadyInscribed":
                        return toAlreadyInscribedException(rootNode);
                    case "AlreadyPickedUp":
                        return toAlreadyPickedUpException(rootNode);
                    case "Authentication":
                        return toAuthenticationException(rootNode);
                    default:
                        throw new ParsingException("Unrecognized error type: " + errorType);
                }
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }

    }

}
