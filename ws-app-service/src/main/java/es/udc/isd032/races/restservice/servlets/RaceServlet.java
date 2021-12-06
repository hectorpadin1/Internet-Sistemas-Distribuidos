package es.udc.isd032.races.restservice.servlets;

import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.model.service.RunFicServiceFactory;
import es.udc.isd032.races.restservice.dto.RaceToRestRaceDtoConversor;
import es.udc.isd032.races.restservice.dto.RestRaceDto;
import es.udc.isd032.races.restservice.json.JsonToExceptionConversor;
import es.udc.isd032.races.restservice.json.JsonToRestRaceDtoConversor;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import es.udc.ws.util.json.exceptions.ParsingException;
import es.udc.ws.util.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());

        if (path != null && path.length() > 0) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                            .toInputValidationException(new InputValidationException("Invalid Request: " + "invalid path " + path)),
                    null);
            return;
        }

        RestRaceDto raceDto;
        try {
            raceDto = JsonToRestRaceDtoConversor.toServiceRaceDto(req.getInputStream());
        } catch (ParsingException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST, JsonToExceptionConversor
                    .toInputValidationException(new InputValidationException(e.getMessage())), null);
            return;
        }
        Race race = RaceToRestRaceDtoConversor.toRace(raceDto);

        try {
            race = RunFicServiceFactory.getService().createRace(race);
        } catch (InputValidationException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(e), null);
            return;
        }
        raceDto = RaceToRestRaceDtoConversor.toRestRaceDto(race);

        String raceUrl = ServletUtils.normalizePath(req.getRequestURL().toString()) + "/" + race.getRaceId();
        Map<String, String> headers = new HashMap<>(1);
        headers.put("location",raceUrl);

        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_CREATED,
                JsonToRestRaceDtoConversor.toObjectNode(raceDto),headers);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = ServletUtils.normalizePath(req.getPathInfo());
        if (path == null || path.length() == 0) {

            String dateStr = req.getParameter("date");
            LocalDate date;
            if (dateStr==null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: Field date is mandatory.")),
                        null);
                return;
            }
            try {
                date = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: Field date must represent a valid date.")),
                        null);
                return;
            }
            String city = req.getParameter("city");
            if (city==null) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: Field city is mandatory.")),
                        null);
                return;
            }
            List<Race> races = null;
            try {
                races = RunFicServiceFactory.getService().findRacesByDate(date, city);
            } catch (InputValidationException e) {
                ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        JsonToExceptionConversor.toInputValidationException(
                                new InputValidationException("Invalid Request: " + e.getMessage())),
                        null);
                return;
            }
            List<RestRaceDto> raceDtos = RaceToRestRaceDtoConversor.toRestRaceDtos(races);
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                    JsonToRestRaceDtoConversor.toArrayNode(raceDtos), null);
            return;
        }
        String[] args = path.split("/");
        if (args.length > 2) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: invalid path " + path)), null);
            return;
        }
        String raceIdAsString = args[1];
        Long raceId;
        try {
            raceId = Long.valueOf(raceIdAsString);
        } catch (NumberFormatException ex) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    JsonToExceptionConversor.toInputValidationException(
                            new InputValidationException("Invalid Request: " + "invalid raceId '" + raceIdAsString)),
                    null);
            return;
        }
        Race race;
        try {
            race = RunFicServiceFactory.getService().findRace(raceId);
        } catch (InstanceNotFoundException e) {
            ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    JsonToExceptionConversor.toInstanceNotFoundException(e), null);
            return;
        }

        RestRaceDto raceDto = RaceToRestRaceDtoConversor.toRestRaceDto(race);
        ServletUtils.writeServiceResponse(resp, HttpServletResponse.SC_OK,
                JsonToRestRaceDtoConversor.toObjectNode(raceDto), null);

    }
}

