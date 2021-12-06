package es.udc.isd032.races.client.service.rest.race;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonToClientRaceDtoConversor {

    public static ObjectNode toObjectNode(ClientRaceDto race) {

        ObjectNode raceObject = JsonNodeFactory.instance.objectNode();

        if (race.getRaceId() != null) {
            raceObject.put("raceId", race.getRaceId());
        }
        raceObject.put("city", race.getCity()).
                put("description", race.getDescription()).
                put("date", race.getDate().toString()).
                put("price", race.getPrice()).
                put("maxParticipants",race.getMaxParticipants()).
                put("inscriptions",race.getMaxParticipants()-race.getPlaces());

        return raceObject;
    }

    public static ClientRaceDto toClientRaceDto(InputStream jsonRace) throws ParsingException {

        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonRace);
            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                return toClientRaceDto(rootNode);
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    private static ClientRaceDto toClientRaceDto(JsonNode raceNode) throws ParsingException {
        if (raceNode.getNodeType() != JsonNodeType.OBJECT) {
            throw new ParsingException("Unrecognized JSON (object expected)");
        } else {
            ObjectNode raceObject = (ObjectNode) raceNode;

            JsonNode raceIdN = raceObject.get("raceId");

            Long raceId = (raceIdN != null) ? raceIdN.longValue() : null;
            String city = raceObject.get("city").textValue().trim();
            String description = raceObject.get("description").textValue().trim();
            String dateString = raceObject.get("date").textValue().trim();
            LocalDateTime date = LocalDateTime.parse(dateString);
            float price = raceObject.get("price").floatValue();
            short participants = raceObject.get("maxParticipants").shortValue();
            short inscriptions = raceObject.get("inscriptions").shortValue();

            return new ClientRaceDto(raceId,city,description,date,price,participants,(short) (participants-inscriptions));
        }
    }

    public static List<ClientRaceDto> toClientRaceDtos(InputStream content) {
        try {

            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(content);
            if (rootNode.getNodeType() != JsonNodeType.ARRAY) {
                throw new ParsingException("Unrecognized JSON (array expected)");
            } else {
                ArrayNode races = (ArrayNode) rootNode;
                List<ClientRaceDto> raceDtos = new ArrayList<>(races.size());
                for (JsonNode raceNode : races) {
                    raceDtos.add(toClientRaceDto(raceNode));
                }

                return raceDtos;
            }
        } catch (ParsingException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }
}
