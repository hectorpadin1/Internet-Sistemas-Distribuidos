package es.udc.isd032.races.client.service.exceptions;

public class ClientAlreadyPickedUpException extends Exception {
    private final short dorsal;
    private final Long raceId;
    private final Long inscriptionId;

    public ClientAlreadyPickedUpException(short dorsal, Long raceId, Long inscriptionId) {
        super("Dorsal \"" + dorsal+ "\" for race \"" + raceId
                + "\" has been already picked up with code \"" + inscriptionId + "\".");
        this.dorsal = dorsal;
        this.raceId = raceId;
        this.inscriptionId = inscriptionId;
    }

    public short getDorsal() {
        return dorsal;
    }

    public Long getRaceId() {
        return raceId;
    }

    public Long getCode() {
        return inscriptionId;
    }
}
