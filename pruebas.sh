#! /bin/bash

#cd $HOME/software/ws-app/ws-app-client

cd ws-app-client


printLine() {
	echo ""
	echo ""
  echo "$(tput setaf 7)$(tput bold)"
	echo "----------------------------------------------------------------------------------------------"
	echo ""
	echo "Sleeping $(tput setaf 3)$(tput bold)10$(tput setaf 7)$(tput bold) seconds, review results!"
	echo "$(tput sgr0)"
	echo ""
}

clean() {
	
	cd $HOME/software/ws-app
	mvn sql:execute
	cd $HOME/software/ws-app/ws-app-client
}


addRaces() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 1 (A1): addRace"
	echo "$(tput setaf 4)$(tput bold)-----------------------------"
	echo "$(tput sgr0)"

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating Race 1:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '10Km. Campus Elviña' '2021-08-15T11:00' 10 2" -q

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating Race 2:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'Santiago' '5Km. Plaza Obradoiro' '2021-08-25T11:00' 5 100" -q

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating Race 3:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '7Km. Playa Riazor' '2021-10-15T11:00' 7 200" -q

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating Race 4:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '20Km. Obelisco' '2021-10-25T11:00' 20 300" -q


  echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Updating race date (id=4) from '2021-10-25T11:00' to '2020-11-25T10:00'"
	echo "$(tput sgr0)"
  mysql -u ws --password=ws ws -e "UPDATE Race SET raceDate='2020-11-25T10:00' WHERE raceId=4"


	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating a race with a past date $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '10Km. Torre Hércules' '2020-08-14T11:00' 10 100" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating a race with a negative price $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '10Km. Torre Hércules' '2021-08-14T11:00' -2 100" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating a race with max. participants <= 0 $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' '10Km. Torre Hércules' '2021-08-14T11:00' 10 0" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating a race with the field 'city' empty or null $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace ' ' '10Km. Torre Hércules' '2021-08-14T11:00' 10 100" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Creating a race with the field 'Description' empty $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-addRace 'A Coruña' ' ' '2021-08-14T11:00' 10 100" -q

}


findRaces() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 3 (A1): findRaces"
	echo "$(tput setaf 4)$(tput bold)-------------------------------"
	echo "$(tput sgr0)"
	
	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finds Race 1 and Race 3:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '2021-12-15' 'A Coruña'" -q
	
	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finds Race 1."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '2021-08-31' 'A Coruña'" -q
	
	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Does not find any race"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '2021-01-31' 'Santiago'" -q

	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finding races with date < now $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '2020-06-24' 'Santiago'" -q
	
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finding races with field 'city' empty or null $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '2021-08-31' ' '" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Looking for races with bad date $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRaces '20-08-31' 'A Coruña'" -q

}

addInscription() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 4 (A2): Inscribe"
	echo "$(tput setaf 4)$(tput bold)------------------------------"
	echo "$(tput sgr0)"

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Making inscription 1 (User 1 and Race 1):"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user1@udc.es' 1 '0123456789111111'" -q

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Making inscription 2 (User 2 and Race 1):"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user2@udc.es' 1 '0123456789222222'" -q	

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Making inscription 3 (User 2 and Race 2):"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user2@udc.es' 2 '0123456789222222'" -q	

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Making inscription 4 (User 1 and Race 2):"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user1@udc.es' 2 '0123456789111111'" -q


	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)No avaliable places into the race $(tput setaf 1)$(tput bold)(throws MaxParticipantsException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user3@udc.es' 1 '0123456789333333'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Inscription out of date $(tput setaf 1)$(tput bold)(throws InscriptionOutOfTimeException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user3@udc.es' 4 '0123456789333333'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)User was already inscribed $(tput setaf 1)$(tput bold)(throws AlreadyInscribedException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user1@udc.es' 2 '0123456789111111'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid mail $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user4' 2 '0123456789444444'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid credit card $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user4@udc.es' 2 '0123456789'" -q
	
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Inscribe into a non existant race. $(tput setaf 1)$(tput bold)(throws InstanceNotFoundException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-register 'user3@udc.es' 9 '0123456789444444'" -q
}

findInscription() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 5 (A2): findInscriptions"
	echo "$(tput setaf 4)$(tput bold)--------------------------------------"
	echo "$(tput sgr0)"

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finds inscriptions 1 (dorsal 1) and 4 (dorsal 2):"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRegisters 'user1@udc.es'" -q

	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Does not find any inscriptions:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRegisters 'user6@udc.es'" -q

	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Email is not valid $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRegisters 'user1'" -q

}

dorsal() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 6 (A3): MarkPickUp"
	echo "$(tput setaf 4)$(tput bold)--------------------------"
	echo "$(tput sgr0)"


	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Marks the pick-up:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-deliverNumber 1 '0123456789111111'" -q


	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"


	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Different credit card $(tput setaf 1)$(tput bold)(throws AuthenticationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-deliverNumber 1 '0123456789222222'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Already picked-up: $(tput setaf 1)$(tput bold)(throws AlreadyPickedUpException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-deliverNumber 1 '0123456789111111'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Credit card is not valid $(tput setaf 1)$(tput bold)(throws InputValidationException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-deliverNumber 1 '0123456789'" -q

	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Inscription does not exist $(tput setaf 1)$(tput bold)(throws InstanceNotFoundException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-deliverNumber 9 '0123456789111111'" -q
}

findRace() {
	echo "$(tput setaf 7)$(tput bold)"
	echo "Functionality 2 (A3): findRace"
	echo "$(tput setaf 4)$(tput bold)------------------------------"
	echo "$(tput sgr0)"


	echo "$(tput setaf 4)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Finds Race 2 and returns avaliable places:"
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRace 2" -q


	echo ""
	echo ""
	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Invalid sentences:"
	echo "$(tput setaf 1)$(tput bold)Throwing Exceptions..."
	echo "$(tput sgr0)"


	echo "$(tput setaf 1)$(tput bold)"
	echo "[+] $(tput setaf 7)$(tput bold)Does not find the Race 9 $(tput setaf 1)$(tput bold)(throws InstanceNotFoundException)$(tput sgr0)."
	echo "$(tput sgr0)"
	mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-findRace 9" -q

}

case "$1" in
	addRaces)
		addRaces
		exit 0
	;;
	findRaces)
		findRaces
		exit 0
	;;
	addInscription)
		addInscription
		exit 0
	;;
	findInscription)
		findInscription
		exit 0
	;;
	markPickUp)
		dorsal
		exit 0
	;;
	findRace)
		findRace
		exit 0
	;;
	clean)
		clean
		exit 0
	;;
    all)
    	echo "$(tput setaf 2)$(tput bold)"
    	echo "TESTING ALL FEATURES FOR THE PROYECT"
    	echo "$(tput sgr0)"
    	echo ""
        addRaces
        printLine
        sleep 10
        findRaces
        printLine
        sleep 10
        addInscription
        printLine
        sleep 10
        findInscription
        printLine
        sleep 10
        dorsal
        printLine
        sleep 10
        findRace
        printLine
        echo "$(tput setaf 2)$(tput bold)"
        echo "Cleaning BBDD."
        echo "$(tput sgr0)"
        sleep 10
        clean
        exit 0
    ;;
	**)
		echo "$(tput sgr0)"
		echo "$(tput bold)Arguments:"
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)all$(tput sgr0): Runs all the methods for the script."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)addRaces$(tput sgr0): Tests the addRace method, adds 4 valid races updates one of them, and test all possible error-cases."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)findRaces$(tput sgr0): Tests the findRaces method, executes 3 times find (2 races, 1 race, no races found), and test all possible error-cases."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)addInscription$(tput sgr0): Tests the addInscription method, adds 4 valid inscriptions, and test all possible error-cases."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)findInscription$(tput sgr0): Tests the findInscription method, executes 3 times find (2 inscriptions, exception), and test all possible error-cases."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)markPickUp$(tput sgr0): Test the Pick-Up of a dorsal in our proyect, and test all possible error-cases."
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)findRace$(tput sgr0): Test the method that allow to search by race ID"
		echo "$(tput setaf 4)$(tput bold)[+] $(tput setaf 7)clean$(tput sgr0): Cleans the database."
		exit 1
	;;
esac
	
