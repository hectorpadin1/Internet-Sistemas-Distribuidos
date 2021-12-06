package es.udc.isd032.races.client.service.rest.race;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientInscriptionDtoConversor {

    public static ClientInscriptionDto toClientInscriptionDto(InputStream jsonInscription) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonInscription);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientInscriptionDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static ClientInscriptionDto toClientInscriptionDto(JsonNode inscriptionNode) throws ParsingException {
        try {
            if (inscriptionNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode inscriptionObject = (ObjectNode) inscriptionNode;

                JsonNode inscriptionIdNode = inscriptionObject.get("inscriptionId");
                Long inscriptionId = (inscriptionIdNode != null) ? inscriptionIdNode.longValue() : null;

                String userEmail = inscriptionObject.get("userEmail").textValue().trim();
                Long raceId = inscriptionObject.get("raceId").longValue();
                String creditCardNumber = inscriptionObject.get("creditCardNumber").textValue().trim();
                String inscriptionDate = inscriptionObject.get("inscriptionDate").textValue().trim();
                Short dorsal = inscriptionObject.get("dorsal").shortValue();
                boolean isPicked = inscriptionObject.get("isPicked").booleanValue();

                return new ClientInscriptionDto(inscriptionId, userEmail, raceId, creditCardNumber,
                        LocalDateTime.parse(inscriptionDate), dorsal, isPicked);

            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static List<ClientInscriptionDto> toClientInscriptionDtos(InputStream content) {

        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(content);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode inscriptions = (ArrayNode) rootNode;
                List<ClientInscriptionDto> inscriptionDtos = new ArrayList<>(inscriptions.size());
                for (JsonNode inscriptionNode : inscriptions) {
                    inscriptionDtos.add(toClientInscriptionDto(inscriptionNode));
                }

                return inscriptionDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}
