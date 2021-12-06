package es.udc.isd032.races.restservice.json;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import es.udc.isd032.races.model.service.exceptions.MaxParticipantsException;
import es.udc.isd032.races.model.service.exceptions.AlreadyInscribedException;
import es.udc.isd032.races.model.service.exceptions.AlreadyPickedUpException;
import es.udc.isd032.races.model.service.exceptions.AuthenticationException;
import es.udc.isd032.races.model.service.exceptions.InscriptionOutOfTimeException;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;

public class JsonToExceptionConversor {

        public static ObjectNode toInputValidationException(InputValidationException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "InputValidation");
                exceptionObject.put("message", ex.getMessage());

                return exceptionObject;
        }

        public static ObjectNode toInstanceNotFoundException(InstanceNotFoundException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();
                ObjectNode dataObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "InstanceNotFound");
                exceptionObject.put("instanceId", (ex.getInstanceId() != null) ?
                        ex.getInstanceId().toString() : null);
                exceptionObject.put("instanceType",
                        ex.getInstanceType().substring(ex.getInstanceType().lastIndexOf('.') + 1));

                return exceptionObject;
        }

        public static ObjectNode toAlreadyInscribedException(AlreadyInscribedException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "AlreadyInscribed");
                exceptionObject.put("raceId", (ex.getRaceId() != null) ? ex.getRaceId() : null);
                if (ex.getEmail() != null) {
                        exceptionObject.put("userEmail", ex.getEmail());
                } else {
                        exceptionObject.set("userEmail", null);
                }

                return exceptionObject;
        }

        public static ObjectNode toAlreadyPickedUpException(AlreadyPickedUpException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "AlreadyPickedUp");
                exceptionObject.put("raceId", (ex.getRaceId() != null) ? ex.getRaceId() : null);
                exceptionObject.put("dorsal", (ex.getDorsal()));
                if (ex.getCode() != null) {
                        exceptionObject.put("inscriptionId", ex.getCode());
                } else {
                        exceptionObject.set("inscriptionId", null);
                }

                return exceptionObject;
        }

        public static ObjectNode toAuthenticationException(AuthenticationException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "Authentication");
                exceptionObject.put("raceId", (ex.getRaceId() != null) ? ex.getRaceId() : null);
                if (ex.getCreditCard() != null) {
                        exceptionObject.put("creditCardNumber", ex.getCreditCard());
                } else {
                        exceptionObject.set("creditCardNumber", null);
                }

                return exceptionObject;
        }

        public static ObjectNode toInscriptionOutOfTimeException(InscriptionOutOfTimeException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "InscriptionOutOfTime");
                exceptionObject.put("raceId", (ex.getRaceId() != null) ? ex.getRaceId() : null);
                if (ex.getDate() != null) {
                        exceptionObject.put("inscriptionDate", ex.getDate().toString());
                } else {
                        exceptionObject.set("inscriptionDate", null);
                }

                return exceptionObject;

        }

        public static ObjectNode toMaxParticipantsException(MaxParticipantsException ex) {

                ObjectNode exceptionObject = JsonNodeFactory.instance.objectNode();

                exceptionObject.put("errorType", "MaxParticipants");
                exceptionObject.put("raceId", (ex.getRaceId() != null) ? ex.getRaceId() : null);

                return exceptionObject;

        }
}