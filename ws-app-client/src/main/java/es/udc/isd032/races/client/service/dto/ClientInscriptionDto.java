package es.udc.isd032.races.client.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ClientInscriptionDto {
    //The object Inscription which will be shown to client
    private Long inscriptionId;
    private String userEmail;
    private Long raceId;
    private String creditCardNumber;
    private LocalDateTime inscriptionDate;
    private short dorsal;
    private boolean isPicked;

    public ClientInscriptionDto(){ }

    public ClientInscriptionDto(Long inscriptionId, String userEmail, Long raceId, String creditCardNumber,
                                LocalDateTime inscriptionDate, short dorsal, boolean isPicked) {
        this.inscriptionId = inscriptionId;
        this.userEmail = userEmail;
        this.raceId = raceId;
        this.creditCardNumber = creditCardNumber;
        this.inscriptionDate = inscriptionDate;
        this.dorsal = dorsal;
        this.isPicked = isPicked;
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

    public short getDorsal() {
        return dorsal;
    }

    public void setDorsal(short dorsal) {
        this.dorsal = dorsal;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    @Override
    public String toString() {
        return "RestInscriptionDto{" +
                "inscriptionId=" + inscriptionId +
                ", userEmail='" + userEmail + '\'' +
                ", raceId='" + raceId + '\'' +
                ", creditCardNumber=" + creditCardNumber +
                ", inscriptionDate=" + inscriptionDate +
                ", dorsal=" + dorsal +
                ", is picked=" + (this.isPicked() ? "yes" : "no") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientInscriptionDto that = (ClientInscriptionDto) o;
        return dorsal == that.dorsal &&
                isPicked == that.isPicked &&
                Objects.equals(inscriptionId, that.inscriptionId) &&
                userEmail.equals(that.userEmail) &&
                raceId.equals(that.raceId) &&
                creditCardNumber.equals(that.creditCardNumber) &&
                inscriptionDate.equals(that.inscriptionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inscriptionId, userEmail, raceId, creditCardNumber, inscriptionDate, dorsal, isPicked);
    }
}
