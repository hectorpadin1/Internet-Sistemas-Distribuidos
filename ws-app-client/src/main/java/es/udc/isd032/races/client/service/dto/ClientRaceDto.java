package es.udc.isd032.races.client.service.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ClientRaceDto {

    private Long raceId;
    private String city;
    private String description;
    private LocalDateTime date;
    private Float price;
    private short maxParticipants;
    private short places;

    public ClientRaceDto() {

    }

    public ClientRaceDto(Long raceId, String city, String description, LocalDateTime date, Float price,
                         short maxParticipants) {
        this.raceId = raceId;
        this.city = city;
        this.description = description;
        this.date = date;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.places = maxParticipants;
    }

    public ClientRaceDto(Long raceId, String city, String description, LocalDateTime date, float price, short participants, short places) {
        this(raceId,city,description,date,price,participants);
        this.places = places;
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

    public short getPlaces() {
        return places;
    }

    public void setPlaces(short places) {
        this.places = places;
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
                ", places avaliable=" + places +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRaceDto raceDto = (ClientRaceDto) o;
        return maxParticipants == raceDto.maxParticipants &&
                places == raceDto.places &&
                Objects.equals(raceId, raceDto.raceId) &&
                city.equals(raceDto.city) &&
                description.equals(raceDto.description) &&
                date.equals(raceDto.date) &&
                price.equals(raceDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(raceId, city, description, date, price, maxParticipants, places);
    }
}
