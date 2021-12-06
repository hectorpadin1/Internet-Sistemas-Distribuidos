package es.udc.isd032.races.model.service.exceptions;

public class MaxParticipantsException extends Exception {
    private final Long raceId;

    public MaxParticipantsException(Long race) {
        super("Race with id \"" + race + "\" has reached the limit" +
                " of participants inscribed.");
        this.raceId = race;
    }

    public Long getRaceId() {
        return raceId;
    }
}
