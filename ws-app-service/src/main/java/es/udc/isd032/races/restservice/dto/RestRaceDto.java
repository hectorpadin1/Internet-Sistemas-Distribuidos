package es.udc.isd032.races.restservice.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class RestRaceDto {

    private Long raceId;
    private String city;
    private String description;
    private LocalDateTime date;
    private Float price;
    private short maxParticipants;
    private short inscriptions;

    public RestRaceDto() {

    }

    public RestRaceDto(Long raceId, String city, String description, LocalDateTime date, Float price,
                       short maxParticipants, short inscriptions) {
        this.raceId = raceId;
        this.city = city;
        this.description = description;
        this.date = date;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.inscriptions = inscriptions;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public short getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(short maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public short getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(short inscriptions) {
        this.inscriptions = inscriptions;
    }

    @Override
    public String toString() {
        return "RestRaceDto{" +
                "raceId=" + raceId +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", maxParticipants=" + maxParticipants +
                ", inscriptions=" + inscriptions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestRaceDto that = (RestRaceDto) o;
        return maxParticipants == that.maxParticipants &&
                inscriptions == that.inscriptions &&
                Objects.equals(raceId, that.raceId) &&
                city.equals(that.city) &&
                description.equals(that.description) &&
                date.equals(that.date) &&
                price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId, city, description, date, price, maxParticipants, inscriptions);
    }
}
