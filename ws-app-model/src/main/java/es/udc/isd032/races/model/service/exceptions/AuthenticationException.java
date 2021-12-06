package es.udc.isd032.races.model.service.exceptions;

public class AuthenticationException extends Exception {

    private final String creditCardNumber;
    private final Long raceId;

    public AuthenticationException(String creditCard, Long raceId) {
        super("User with the asociated credit card \"" + creditCard + "\"" +
                " does not have been inscribed the race with id \"" + raceId + "\".");
        this.creditCardNumber = creditCard;
        this.raceId = raceId;
    }

    public String getCreditCard() {
        return creditCardNumber;
    }

    public Long getRaceId() {
        return raceId;
    }
}
