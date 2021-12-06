package es.udc.isd032.races.model.race;

import java.time.LocalDateTime;
import java.util.Objects;

public class Race {
    private Long raceId;
    private String city;
    private String description;
    private LocalDateTime date;
    private Float price;
    private short maxParticipants;
    private LocalDateTime creationDate;
    private short inscriptions;


    public Race(String city, String description, LocalDateTime date, Float price, short maxParticipants) {
        this.city = city;
        this.description = description;
        this.date = date;
        this.price = price;
        this.maxParticipants = maxParticipants;
    }

    public Race(Long raceId, String city, String description, LocalDateTime date, Float price, short maxParticipants, LocalDateTime creationDate, short inscriptions) {
        this(city, description, date, price, maxParticipants);
        this.raceId = raceId;
        this.creationDate = (creationDate != null) ? creationDate.withNano(0) : null;
        this.inscriptions = inscriptions;
    }

    public Race(String city, String description, LocalDateTime date, float price, short maxParticipants, short inscriptions) {
        this(city, description, date, price, maxParticipants);
        this.inscriptions = inscriptions;
    }

    public Race(Long raceId, String city, String description, LocalDateTime date, Float price, short maxParticipants, short inscriptions) {
        this(city, description, date, price, maxParticipants);
        this.raceId = raceId;
        this.inscriptions = inscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Race race = (Race) o;
        return maxParticipants == race.maxParticipants &&
                inscriptions == race.inscriptions &&
                Objects.equals(raceId, race.raceId) &&
                Objects.equals(city, race.city) &&
                Objects.equals(description, race.description) &&
                Objects.equals(date, race.date) &&
                Objects.equals(price, race.price) &&
                Objects.equals(creationDate, race.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId, city, description, date, price, maxParticipants, creationDate, inscriptions);
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = (creationDate != null) ? creationDate.withNano(0) : null;
    }

    public short getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(short inscriptions) {
        this.inscriptions = inscriptions;
    }

    @Override
    public String toString() {
        return "Race{" +
                "raceId=" + raceId +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", maxParticipants=" + maxParticipants +
                ", creationDate=" + creationDate +
                ", inscriptions=" + inscriptions +
                '}';
    }
}
