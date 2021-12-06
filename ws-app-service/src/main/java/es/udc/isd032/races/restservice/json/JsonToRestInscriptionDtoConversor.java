package es.udc.isd032.races.restservice.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.isd032.races.restservice.dto.RestInscriptionDto;

import java.util.List;

public class JsonToRestInscriptionDtoConversor {

    public static ObjectNode toObjectNode(RestInscriptionDto inscription) {

        ObjectNode inscriptionNode = JsonNodeFactory.instance.objectNode();

        if (inscription.getRaceId() != null) {
            inscriptionNode.put("inscriptionId", inscription.getInscriptionId());
        }
        inscriptionNode.put("userEmail", inscription.getUserEmail()).
                put("raceId", inscription.getRaceId()).
                put("creditCardNumber", inscription.getCreditCardNumber()).
                put("inscriptionDate", inscription.getInscriptionDate().toString()).
                put("dorsal", inscription.getDorsal()).
                put("isPicked", inscription.isPicked());

        return inscriptionNode;
    }

    public static ArrayNode toArrayNode(List<RestInscriptionDto> inscriptions) {

        ArrayNode inscriptionNode = JsonNodeFactory.instance.arrayNode();
        for (int i = 0; i < inscriptions.size(); i++) {
            RestInscriptionDto inscriptionDto = inscriptions.get(i);
            ObjectNode inscriptionObject = toObjectNode(inscriptionDto);
            inscriptionNode.add(inscriptionObject);
        }

        return inscriptionNode;
    }
}
