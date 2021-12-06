package es.udc.isd032.races.restservice.dto;

import es.udc.isd032.races.model.race.Race;

import java.util.ArrayList;
import java.util.List;

public class RaceToRestRaceDtoConversor {

    public static List<RestRaceDto> toRestRaceDtos(List<Race> races) {
        List<RestRaceDto> racesDtos = new ArrayList<>(races.size());
        for (int i = 0; i < races.size(); i++) {
            Race race = races.get(i);
            racesDtos.add(toRestRaceDto(race));
        }
        return racesDtos;
    }

    public static Race toRace(RestRaceDto raceDto) {

        return new Race(raceDto.getRaceId(),raceDto.getCity(),raceDto.getDescription(),
                raceDto.getDate(),raceDto.getPrice(),raceDto.getMaxParticipants(),raceDto.getInscriptions());
    }

    public static RestRaceDto toRestRaceDto(Race race) {
        return new RestRaceDto(race.getRaceId(),race.getCity(),race.getDescription(),race.getDate(),
                race.getPrice(),race.getMaxParticipants(),race.getInscriptions());
    }
}
