package es.udc.isd032.races.client.service.exceptions;

public class ClientMaxParticipantsException extends Exception {
    private final Long raceId;

    public ClientMaxParticipantsException(Long race) {
        super("Race with id \"" + race + "\" has reached the limit" +
                " of participants inscribed.");
        this.raceId = race;
    }

    public Long getRaceId() {
            return raceId;
        }
}
