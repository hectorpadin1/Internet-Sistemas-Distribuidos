package es.udc.isd032.races.client.service.exceptions;

import java.time.LocalDateTime;

public class ClientInscriptionOutOfTimeException extends Exception{

    private Long raceId;
    private LocalDateTime date;

    public ClientInscriptionOutOfTimeException(Long raceId, LocalDateTime date) {
        super("Race with id=\"" + raceId +
                "\" has closed the inscription on (date = \"" +
                date + "\").");
        this.raceId = raceId;
        this.date = date;
    }

    public Long getRaceId() {
        return raceId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }
}
