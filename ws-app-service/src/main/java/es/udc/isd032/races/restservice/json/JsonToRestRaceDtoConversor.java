package es.udc.isd032.races.restservice.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.udc.isd032.races.restservice.dto.RestRaceDto;

import es.udc.ws.util.json.ObjectMapperFactory;
import es.udc.ws.util.json.exceptions.ParsingException;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

public class JsonToRestRaceDtoConversor {

    public static ObjectNode toObjectNode(RestRaceDto race) {

        ObjectNode raceObject = JsonNodeFactory.instance.objectNode();

        if (race.getRaceId() != null)
            raceObject.put("raceId", race.getRaceId());
        raceObject.put("city",race.getCity()).put("description",race.getDescription())
                .put("date",race.getDate().toString()).put("price",race.getPrice())
                .put("maxParticipants",race.getMaxParticipants())
                .put("inscriptions",race.getInscriptions());

        return raceObject;
    }

    public static RestRaceDto toServiceRaceDto(InputStream jsonRace) throws ParsingException {
        try {
            ObjectMapper objectMapper = ObjectMapperFactory.instance();
            JsonNode rootNode = objectMapper.readTree(jsonRace);

            if (rootNode.getNodeType() != JsonNodeType.OBJECT) {
                throw new ParsingException("Unrecognized JSON (object expected)");
            } else {
                ObjectNode raceObject = (ObjectNode) rootNode;

                JsonNode raceIdN = raceObject.get("raceId");
                Long raceId = (raceIdN != null) ? raceIdN.longValue() : null;
                String city = raceObject.get("city").textValue().trim();
                String description = raceObject.get("description").textValue().trim();
                String dateString = raceObject.get("date").textValue().trim();
                LocalDateTime date = LocalDateTime.parse(dateString);
                float price = raceObject.get("price").floatValue();
                short participants = raceObject.get("maxParticipants").shortValue();
                short inscriptions = raceObject.get("inscriptions").shortValue();

                return new RestRaceDto(raceId,city,description,date,price,participants,inscriptions);
            }
        } catch (ParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new ParsingException(e);
        }
    }

    public static ArrayNode toArrayNode(List<RestRaceDto> races) {

        ArrayNode racesNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < races.size(); i++) {
            RestRaceDto raceDto = races.get(i);
            ObjectNode raceObject = toObjectNode(raceDto);
            racesNode.add(raceObject);
        }

        return racesNode;
    }
}
