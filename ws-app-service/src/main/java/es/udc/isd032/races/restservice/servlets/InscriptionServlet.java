package es.udc.isd032.races.restservice.servlets;

import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.model.service.RunFicServiceFactory;
import es.udc.isd032.races.model.service.exceptions.MaxParticipantsException;
import es.udc.isd032.races.model.service.exceptions.AlreadyInscribedException;
import es.udc.isd032.races.model.service.exceptions.AlreadyPickedUpException;
import es.udc.isd032.races.model.service.exceptions.AuthenticationException;
import es.udc.isd032.races.model.service.exceptions.InscriptionOutOfTimeException;
import es.udc.isd032.races.restservice.dto.InscriptionToRestInscriptionDtoConversor;
import es.udc.isd032.races.restservice.dto.RestInscriptionDto;
import es.udc.isd032.races.restservice.json.JsonToExceptionConversor;
import es.udc.isd032.races.restservice.json.JsonToRestInscriptionDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InscriptionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());

        /* overloaded post */
        if (path != null && path.length() > 0) {

            String[] args = path.split("/");
            String inscriptionIdAsString = args[1];
            String operation = args[2];
            Long inscriptionId;
            try {
                inscriptionId = Long.parseLong(inscriptionIdAsString);
            } catch (NumberFormatException ex) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                                "Invalid Request: " + "parameter 'inscriptionId' is invalid '" + inscriptionIdAsString + "'" +
                                        ex.getMessage())), null);
                return;
            }
            if (operation == null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: " + "invalid path " + path)), null);
                return;
            }
            if (!operation.equalsIgnoreCase("markPickUp")) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: " + " operation " + operation
                                        + " not supported.")), null);
                return;
            }

            String creditCardNumber = req.getParameter("creditCardNumber");
            try {
                RunFicServiceFactory.getService().markPickUp(creditCardNumber, inscriptionId);
            } catch (InstanceNotFoundException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        JsonToExceptionConversor.toInstanceNotFoundException(e), null);
                return;
            } catch (AuthenticationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                        JsonToExceptionConversor.toAuthenticationException(e), null);
                return;
            } catch (InputValidationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(e), null);
                return;
            } catch (AlreadyPickedUpException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                        JsonToExceptionConversor.toAlreadyPickedUpException(e), null);
                return;
            }

            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                    null, null);
            return;
        }

        String raceIdParameter = req.getParameter("raceId");
        if (raceIdParameter == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "parameter 'raceId' is mandatory")),
                    null);
            return;
        }
        Long raceId;
        try {
            raceId = Long.parseLong(raceIdParameter);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "parameter 'raceId' is invalid '" + raceIdParameter + "'")),
                    null);
                return;
        }
        String email = req.getParameter("email");
        if (email == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "parameter 'email' is mandatory")),
                    null);
            return;
        }
        String creditCardNumber = req.getParameter("creditCardNumber");
        if (creditCardNumber == null) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(new InputValidationException(
                            "Invalid Request: " + "parameter 'creditCardNumber' is mandatory")),
                    null);
                return;
        }
        Inscription inscription;
        try {
            inscription = RunFicServiceFactory.getService().createInscription(raceId, email, creditCardNumber);
        } catch (InstanceNotFoundException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(ex), null);
            return;
        } catch (InputValidationException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(ex), null);
            return;
        } catch (InscriptionOutOfTimeException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toInscriptionOutOfTimeException(ex), null);
            return;
        } catch (AlreadyInscribedException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_FORBIDDEN,
                    JsonToExceptionConversor.toAlreadyInscribedException(ex), null);
            return;
        } catch (MaxParticipantsException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_GONE,
                    JsonToExceptionConversor.toMaxParticipantsException(ex), null);
            return;
        }
        RestInscriptionDto inscriptionDto = InscriptionToRestInscriptionDtoConversor.toRestInscriptionDto(inscription);

        String inscriptionURL = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + inscription.getRaceId().toString();

        Map<String, String> headers = new HashMap<>(1);

        headers.put("Location", inscriptionURL);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestInscriptionDtoConversor.toObjectNode(inscriptionDto), headers);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {

            String userEmail = req.getParameter("userEmail");
            if (userEmail==null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: Field userEmail is mandatory.")),
                        null);
                return;
            }
            List<Inscription> inscriptions = null;
            try {
                inscriptions = RunFicServiceFactory.getService().getInscriptions(userEmail);
            } catch (InputValidationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: " + e.getMessage())),
                        null);
                return;
            }
            List<RestInscriptionDto> inscriptionDtos = InscriptionToRestInscriptionDtoConversor.toRestInscritionDtos(inscriptions);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestInscriptionDtoConversor.toArrayNode(inscriptionDtos), null);
        } else {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid path")),
                    null);
        }

    }
}