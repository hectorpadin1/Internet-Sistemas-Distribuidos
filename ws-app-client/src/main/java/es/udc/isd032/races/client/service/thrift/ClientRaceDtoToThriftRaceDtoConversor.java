package es.udc.isd032.races.client.service.thrift;

import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.isd032.races.thrift.ThriftRaceDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientRaceDtoToThriftRaceDtoConversor {

    public static ThriftRaceDto toThriftRaceDto(
            ClientRaceDto clientRaceDto) {

        Long raceId = clientRaceDto.getRaceId();

        return new ThriftRaceDto(
                raceId == null ? -1 : raceId.longValue(),
                clientRaceDto.getCity(),
                clientRaceDto.getDescription(),
                clientRaceDto.getDate().toString(),
                (float) clientRaceDto.getPrice(),
                (short) (clientRaceDto.getMaxParticipants()),
                (short) (clientRaceDto.getMaxParticipants() - clientRaceDto.getPlaces()));

    }

    public static List<ClientRaceDto> toClientRaceDtos(List<ThriftRaceDto> races) {

        List<ClientRaceDto> clientRaceDtos = new ArrayList<>(races.size());

        for (ThriftRaceDto race : races) {
            clientRaceDtos.add(toClientRaceDto(race));
        }
        return clientRaceDtos;

    }

    private static ClientRaceDto toClientRaceDto(ThriftRaceDto race) {

        return new ClientRaceDto(
                race.getRaceId(),
                race.getCity(),
                race.getDescription(),
                LocalDateTime.parse(race.getDate()),
                Double.valueOf(race.getPrice()).floatValue(),
                (short) (race.getMaxParticipants()),
                (short) (race.getMaxParticipants() - race.getInscriptions()));

    }

}
