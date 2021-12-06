package es.udc.isd032.races.client.service.thrift;

import es.udc.isd032.races.client.service.ClientRaceService;
import es.udc.isd032.races.client.service.dto.ClientInscriptionDto;
import es.udc.isd032.races.client.service.dto.ClientRaceDto;
import es.udc.isd032.races.client.service.exceptions.ClientAlreadyInscribedException;
import es.udc.isd032.races.client.service.exceptions.ClientInscriptionOutOfTimeException;
import es.udc.isd032.races.client.service.exceptions.ClientMaxParticipantsException;
import es.udc.isd032.races.thrift.*;
import es.udc.ws.util.configuration.ConfigurationParametersManager;
import es.udc.ws.util.exceptions.InputValidationException;
import es.udc.ws.util.exceptions.InstanceNotFoundException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ThriftClientRaceService implements ClientRaceService {

    private final static String ENDPOINT_ADDRESS_PARAMETER =
            "ThriftClientMovieService.endpointAddress";

    private final static String endpointAddress =
            ConfigurationParametersManager.getParameter(ENDPOINT_ADDRESS_PARAMETER);


    @Override
    public Long addRace(ClientRaceDto race) throws InputValidationException {

        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return client.addRace(ClientRaceDtoToThriftRaceDtoConversor.toThriftRaceDto(race));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public ClientRaceDto findRace(Long raceId) {
        throw new UnsupportedOperationException("Method findRace for client is not implemented yet.");
        /* Has some errors, is not finished
        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientRaceDtoToThriftRaceDtoConversor.toClientRaceDto(client.findRace(raceId));

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }*/

    }


    @Override
    public List<ClientRaceDto> findRaces(String date, String city) throws InputValidationException {

        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientRaceDtoToThriftRaceDtoConversor.toClientRaceDtos(client.findRaces(date,city));
        }catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }

    }

    @Override
    public long addInscription(String userEmail, Long raceId, String creditCardNumber)
            throws InstanceNotFoundException, InputValidationException, ClientInscriptionOutOfTimeException,
            ClientAlreadyInscribedException, ClientMaxParticipantsException {

        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try {

            transport.open();
            return client.createInscription(raceId, userEmail, creditCardNumber);
        } catch (ThriftInstanceNotFoundException e){
            throw new InstanceNotFoundException(e.getInstanceId(),e.getInstanceType());
        }catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        } catch (ThriftInscriptionOutOfTimeException e) {
            throw new ClientInscriptionOutOfTimeException(e.getRaceId(), LocalDateTime.parse(e.getDate(),
                    DateTimeFormatter.ISO_DATE_TIME));
        } catch (ThriftAlreadyInscribedException e) {
            throw new ClientAlreadyInscribedException(e.getUserEmail(),e.getRaceId());
        }catch (ThriftMaxParticipantsException e) {
            throw new ClientMaxParticipantsException(e.getRaceId());
        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public List<ClientInscriptionDto> findInscriptions(String userEmail) throws InputValidationException {
        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try  {

            transport.open();

            return ClientInscriptionDtoToThriftInscriptionDtoConversor.toClientInscriptionDtos(client.findIncription(userEmail));

        } catch (ThriftInputValidationException e) {
            throw new InputValidationException(e.getMessage());
        }catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            transport.close();
        }
    }

    @Override
    public void markPickUp(Long inscriptionId, String creditCardNumber) throws TException{
        ThriftRunFicService.Client client = getClient();
        TTransport transport = client.getInputProtocol().getTransport();

        try {
            transport.open();
            client.markPickUp(inscriptionId, creditCardNumber);
        }catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    private ThriftRunFicService.Client getClient() {

        try {

            TTransport transport = new THttpClient(endpointAddress);
            TProtocol protocol = new TBinaryProtocol(transport);

            return new ThriftRunFicService.Client(protocol);

        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }

    }
}
