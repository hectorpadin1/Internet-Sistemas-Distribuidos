package es.udc.isd032.races.thriftservice;

import es.udc.isd032.races.model.inscription.Inscription;
import es.udc.isd032.races.thrift.ThriftInscriptionDto;

import java.util.ArrayList;
import java.util.List;

public class InscriptionToThriftInscriptionDtoConversor {


        public static ThriftInscriptionDto toThriftInscriptionDto(Inscription inscription) {

            return new ThriftInscriptionDto(inscription.getInscriptionId(), inscription.getUserEmail(), inscription.getRaceId(),
                    inscription.getCreditCardNumber(), inscription.getInscriptionDate().toString(), inscription.getDorsal() ,
                    inscription.isPicked() ? Byte.parseByte("1") : Byte.parseByte("0"));
        }

        public static List<ThriftInscriptionDto> toThriftInscriptionDtos(List<Inscription> inscriptions) {

            List<ThriftInscriptionDto> dtos = new ArrayList<>(inscriptions.size());

            for (Inscription race : inscriptions) {
                dtos.add(toThriftInscriptionDto(race));
            }
            return dtos;
        }

}
