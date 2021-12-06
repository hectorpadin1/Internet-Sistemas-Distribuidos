package es.udc.isd032.races.thriftservice;

import es.udc.isd032.races.model.race.Race;
import es.udc.isd032.races.thrift.ThriftRaceDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RaceToThriftRaceDtoConversor {

    public static Race toRace(ThriftRaceDto race) {
        return new Race(race.getRaceId(), race.getCity(), race.getDescription(),
                LocalDateTime.parse(race.getDate()), Double.valueOf(race.getPrice()).floatValue(),
                race.getMaxParticipants(), race.getInscriptions());
    }

    public static List<ThriftRaceDto> toThriftRaceDtos(List<Race> races) {

        List<ThriftRaceDto> dtos = new ArrayList<>(races.size());

        for (Race race : races) {
            dtos.add(toThriftRaceDto(race));
        }
        return dtos;

    }

    public static ThriftRaceDto toThriftRaceDto(Race race) {

        return new ThriftRaceDto(race.getRaceId(), race.getCity(),
                race.getDescription(), race.getDate().toString(), race.getPrice(),
                race.getMaxParticipants(), race.getMaxParticipants());

    }

}
