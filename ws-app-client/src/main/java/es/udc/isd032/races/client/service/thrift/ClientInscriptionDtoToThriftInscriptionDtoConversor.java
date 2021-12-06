package es.udc.isd032.races.client.service.thrift;

import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.isd032.races.thrift.ThriftInscriptionDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientInscriptionDtoToThriftInscriptionDtoConversor {

    public static ThriftInscriptionDto toThriftInscriptionDto(
            ClientInscriptionDto clientInscriptionDto) {


        Long inscriptionId = clientInscriptionDto.getInscriptionId();

        return new ThriftInscriptionDto(
                inscriptionId == null ? -1 : inscriptionId.longValue(),
                clientInscriptionDto.getUserEmail(),
                clientInscriptionDto.getRaceId(),
                clientInscriptionDto.getCreditCardNumber(),
                clientInscriptionDto.getInscriptionDate().toString(),
                clientInscriptionDto.getDorsal(),
                clientInscriptionDto.isPicked() ? Byte.parseByte("1") : Byte.parseByte("0"));

    }

    public static List<ClientInscriptionDto> toClientInscriptionDtos(List<ThriftInscriptionDto> inscriptions) {

        List<ClientInscriptionDto> clientInscriptionDtos = new ArrayList<>(inscriptions.size());

        for (ThriftInscriptionDto inscription : inscriptions) {
            clientInscriptionDtos.add(toClientInscriptionDto(inscription));
        }
        return clientInscriptionDtos;

    }

    public static ClientInscriptionDto toClientInscriptionDto(ThriftInscriptionDto inscription) {

        return new ClientInscriptionDto(
                inscription.getInscriptionId(),
                inscription.getUserEmail(),
                inscription.getRaceId(),
                inscription.getCreditCardNumber(),
                LocalDateTime.parse(inscription.getInscriptionDate()),
                inscription.getDorsal(),
                inscription.getIsPicked() == Byte.parseByte("1"));

    }
}
