package es.udc.isd032.races.restservice.dto;

import es.udc.isd032.races.model.inscription.Inscription;

import java.util.ArrayList;
import java.util.List;

public class InscriptionToRestInscriptionDtoConversor {

    public static List<RestInscriptionDto> toRestInscritionDtos(List<Inscription> inscriptions) {
        List<RestInscriptionDto> inscriptionsDtos = new ArrayList<>(inscriptions.size());
        for (int i = 0; i < inscriptions.size(); i++) {
            Inscription inscription = inscriptions.get(i);
            inscriptionsDtos.add(toRestInscriptionDto(inscription));
        }
        return inscriptionsDtos;
    }

    public static Inscription toInscription(RestInscriptionDto inscriptionDto) {

        return new Inscription(inscriptionDto.getInscriptionId(),inscriptionDto.getUserEmail(),inscriptionDto.getRaceId(),
                inscriptionDto.getCreditCardNumber(),inscriptionDto.getInscriptionDate(),inscriptionDto.getDorsal(),inscriptionDto.isPicked());
    }

    public static RestInscriptionDto toRestInscriptionDto(Inscription inscription) {
        return new RestInscriptionDto(inscription.getInscriptionId(),inscription.getUserEmail(),inscription.getRaceId(),inscription.getCreditCardNumber(),
                inscription.getInscriptionDate(),inscription.getDorsal(),inscription.isPicked());
    }

}
