namespace java es.udc.isd032.races.thrift

struct ThriftRaceDto {
    1: i64 raceId;
    2: string city;
    3: string description;
    4: string date;
    5: double price;
    6: i16 maxParticipants;
    7: i16 inscriptions;
}

struct ThriftInscriptionDto {
    1: i64 inscriptionId;
    2: string userEmail;
    3: i64 raceId;
    4: string creditCardNumber;
    5: string inscriptionDate;
    6: i16 dorsal;
    7: byte isPicked;
}

exception ThriftInputValidationException {
    1: string message
}

exception ThriftInscriptionOutOfTimeException {
    1: i64 raceId
    2: string date
}

exception ThriftAlreadyInscribedException {
    1: string userEmail
    2: i64 raceId
}

exception ThriftMaxParticipantsException {
    1: i64 raceId
}

exception ThriftInstanceNotFoundException {
    1: string instanceId
    2: string instanceType
}

exception ThriftUnsupportedOperationException {
    1: string message;
}


service ThriftRunFicService {

   i64 addRace(1: ThriftRaceDto raceDto) throws (1: ThriftInputValidationException e)

   ThriftRaceDto findRace(1: i64 raceId)

   list<ThriftRaceDto> findRaces(1: string date, 2: string city) throws (1: ThriftInputValidationException e)

   i64 createInscription(1: i64 raceId,2: string userEmail,3: string creditCardNumber)
   throws (1: ThriftInputValidationException e, 2: ThriftInscriptionOutOfTimeException ee,
    3: ThriftAlreadyInscribedException eee, 4:  ThriftMaxParticipantsException eeee, 5: ThriftInstanceNotFoundException eeeee)

   list<ThriftInscriptionDto> findIncription(1: string userEmail) throws (1: ThriftInputValidationException e)

   void markPickUp(1: i64 inscriptionId, 2: string creditCardNumber) throws (1: ThriftUnsupportedOperationException e)
}
