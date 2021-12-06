package es.udc.isd032.races.client.ui;

import es.udc.isd032.races.client.service.ClientRaceService;
import es.udc.isd032.races.client.service.ClientRaceServiceFactory;
import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.ws.util.exceptions.InputValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RaceServiceClient {

    public static void main(String[] args) {

        if (args.length==0) {
            printUsage();
            System.exit(-1);
        }

        ClientRaceService clientRaceService = ClientRaceServiceFactory.getService();

        //-addRace 'A Coruña' '10km. Torre de Hércules' '2021-02-20' 2.50 500
        if("-addRace".equalsIgnoreCase(args[0])) {
            validateArgs(args,6,new int[] {4,5});

            try {
                LocalDateTime date = LocalDateTime.parse(args[3]);
                Long raceId = clientRaceService.addRace(new ClientRaceDto(null,
                        args[1], args[2], date, Float.valueOf(args[4]),
                        Short.valueOf(args[5])));
                System.out.println("Race " + raceId + " created sucessfully.");
            } catch (DateTimeParseException | NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        //-findRaces '2021-02-20' 'A Coruña'
        } else if("-findRaces".equalsIgnoreCase(args[0])) {
            validateArgs(args,3,new int[] {});

            try {
                List<ClientRaceDto> races = clientRaceService.findRaces(args[1],args[2]);
                System.out.println("Found " + races.size() +
                        " race(s) in '" + args[2] + "' " + "which will be celebrated before " + args[1] + ".");
                for (int i = 0; i < races.size(); i++) {
                    ClientRaceDto raceDto = races.get(i);
                    System.out.println("Id: " + raceDto.getRaceId() +
                            ", City: " + raceDto.getCity() +
                            ", Description: " + raceDto.getDescription() +
                            ", Date: " + raceDto.getDate() +
                            ", Price: " + raceDto.getPrice() +
                            ", Max. Participants: " + raceDto.getMaxParticipants() +
                            ", Avaliable places: " + raceDto.getPlaces() + ".");
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        //-register 'user1@udc.es' 1 '0123456789111111'
        } else if("-register".equalsIgnoreCase(args[0])) {
            validateArgs(args,4,new int[] {2,3});

            try {
                Long raceId = Long.parseLong(args[2]);
                Long inscriptionId = clientRaceService.addInscription(args[1],raceId,args[3]);
                System.out.println("Inscription created successfully. Yor code is: " + inscriptionId + "\n" +
                        "To pick-up the dorsal you must hand over the credit card number which was used" +
                        " to make the inscription and this code given before.");
            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception e) {
                e.printStackTrace();
            }

        //-findRegisters 'user1@udc.es'
        } else if("-findRegisters".equalsIgnoreCase(args[0])) {
            validateArgs(args,2,new int[] {});

            try {
                List<ClientInscriptionDto> inscriptions = clientRaceService.findInscriptions(args[1]);
                System.out.println("Found " + inscriptions.size() +
                        " inscription(s) for the user with email: '" + args[1] +"'.");
                for (ClientInscriptionDto inscriptionDto : inscriptions) {
                    System.out.println("Inscription Id: " + inscriptionDto.getInscriptionId() +
                            ", User Email: " + inscriptionDto.getUserEmail() +
                            ", Race Id: " + inscriptionDto.getRaceId() +
                            ", Credit Card Number: " + inscriptionDto.getCreditCardNumber() +
                            ", Inscription Date: " + inscriptionDto.getInscriptionDate().toString() +
                            ", Dorsal: " + inscriptionDto.getDorsal() +
                            ", Is Picked: " + (inscriptionDto.isPicked() ? "yes" : "no") + ".");
                }
            } catch (NumberFormatException | InputValidationException ex) {
                ex.printStackTrace(System.err);
            } catch (Exception e) {
                e.printStackTrace();
            }

        //-findRace 1
        }else if("-findRace".equalsIgnoreCase(args[0])) {
            validateArgs(args, 2, new int[] {1});

            try {
                Long raceId = Long.parseLong(args[1]);
                ClientRaceDto race = clientRaceService.findRace(raceId);

                System.out.println("Found Race: ");
                System.out.println("Id: " + race.getRaceId() +
                        ", City: " + race.getCity() +
                        ", Description: " + race.getDescription() +
                        ", Date: " + race.getDate() +
                        ", Price: " + race.getPrice() +
                        ", Max. Participants: " + race.getMaxParticipants() +
                        ", Available places: " + race.getPlaces() + ".");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        //-deliverNumber 1 '0123456789111111'
        }else if("-deliverNumber".equalsIgnoreCase(args[0])) {
            validateArgs(args, 3, new int[] {1,2});

            try {
                clientRaceService.markPickUp(Long.parseLong(args[1]), args[2]);
                System.out.println("Dorsal picked up successfully");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }

        } else printUsage();
    }

    public static void validateArgs(String[] args, int expectedArgs, int[] numericArguments) {
        if(expectedArgs != args.length) {
            printUsage();
            System.exit(-1);
        }
        for(int i = 0 ; i< numericArguments.length ; i++) {
            int position = numericArguments[i];
            try {
                Double.parseDouble(args[position]);
            } catch(NumberFormatException n) {
                printUsage();
                System.exit(-1);
            }
        }
    }

    private static void printUsage() {

        System.err.println("Usage:\n" +
                "\t[addRaces]\t\t-addRace <city> <description> <date> <price> <maxParticipants>\n" +
                "\t[findRace]\t\t-findRace <raceId>\n" +
                "\t[findRaces]\t\t-findRaces <date> <city>\n" +
                "\t[addInscription]\t-register <email> <raceId> <credit card>\n" +
                "\t[findInscription]\t-findRegisters <email>\n" +
                "\t[markPickUp]\t\t-deliverNumber <inscriptionId> <creditCardNumber>");
    }
}
