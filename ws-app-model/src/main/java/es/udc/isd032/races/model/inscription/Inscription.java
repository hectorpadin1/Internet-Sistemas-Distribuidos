package es.udc.isd032.races.model.inscription;

import java.time.LocalDateTime;
import java.util.Objects;

public class Inscription {
    private Long inscriptionId;
    private String userEmail;
    private Long raceId;
    private String creditCardNumber;
    private LocalDateTime inscriptionDate;
    private short dorsal;
    private boolean isPicked;

    @Override
    public String toString() {
        return "Inscription{" +
                "inscriptionId=" + inscriptionId +
                ", userEmail='" + userEmail + '\'' +
                ", raceId=" + raceId +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", inscriptionDate=" + inscriptionDate +
                ", dorsal=" + dorsal +
                ", isPicked=" + isPicked +
                '}';
    }

    public Inscription(Long inscriptionId, String userEmail, Long raceId, String creditCardNumber, LocalDateTime inscriptionDate, Short dorsal, boolean isPicked) {
        this.inscriptionId = inscriptionId;
        this.userEmail = userEmail;
        this.raceId = raceId;
        this.creditCardNumber = creditCardNumber;
        this.inscriptionDate = inscriptionDate;
        this.dorsal = dorsal;
        this.isPicked = isPicked;
    }

    public Inscription( String userEmail, Long raceId, String creditCardNumber, LocalDateTime inscriptionDate, Short dorsal, boolean isPicked) {
        this.userEmail = userEmail;
        this.raceId = raceId;
        this.creditCardNumber = creditCardNumber;
        this.inscriptionDate = inscriptionDate;
        this.dorsal = dorsal;
        this.isPicked = isPicked;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return dorsal == that.dorsal &&
                isPicked == that.isPicked &&
                Objects.equals(inscriptionId, that.inscriptionId) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(raceId, that.raceId) &&
                Objects.equals(creditCardNumber, that.creditCardNumber) &&
                Objects.equals(inscriptionDate, that.inscriptionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inscriptionId, userEmail, raceId, creditCardNumber, inscriptionDate, dorsal, isPicked);
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public LocalDateTime getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(LocalDateTime inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    public Short getDorsal() {
        return dorsal;
    }

    public void setDorsal(Short dorsal) {
        this.dorsal = dorsal;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }


}
