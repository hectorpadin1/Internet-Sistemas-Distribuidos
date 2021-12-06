package es.udc.isd032.races.model.service.exceptions;

public class AlreadyInscribedException extends Exception {
    private final String email;
    private final Long raceId;

    public AlreadyInscribedException(String email, Long race) {
        super("User with email \"" + email + "\" has been already" +
                " inscribed in race with raceId \"" + race + "\".");
        this.email = email;
        this.raceId = race;
    }

    public Long getRaceId(){ return raceId; }

    public String getEmail(){ return email; }
}
