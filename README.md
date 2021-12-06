# Internet-Sistemas-Distribuidos

Se desarrollará una aplicación que siga una arquitectura en capas como la estudiada en la asignatura, incluyendo la capa acceso a datos, la capa lógica de negocio, la capa servicios, la capa acceso a servicios y la capa interfaz de usuario. La aplicación podrá ser invocada remotamente usando REST y, opcionalmente, Apache Thrift. La capa acceso a datos utilizará una base de datos relacional para guardar la información pertinente.

# RunFic 

We have a file with the maven and sql commands to test our proyect. Also a script for linux distributions.

### Using script
To test all the proyect use <code>./pruebas.sh all</code>. To run a specific functionality, for example *addRaces* 
run <code>./pruebas.sh addRaces</code>. To clear Database run <code>./pruebas.sh clean</code>.

### Manual set-up:

The DataBases used in this proyect were <code>ws</code> for the service layer and normal execution and 
<code>wstest</code> for model layer testing. Commands to enter into databases:

<code>mysql -u ws --password=ws ws</code><br>

<code>mysql -u ws --password=ws wstest</code>

To start databases:

<code>cd $HOME/software/ws-app</code><br>

<code>mvn sql:execute install</code>

To start REST or Thrift Service (depends of which one was selected):

<code>cd $HOME/software/ws-app/ws-app-service</code><br>

<code>mvn jetty:run</code>

After the service started, we can execute client part and make petitions to the service layer:

<code>cd $HOME/software/ws-app/ws-app-client</code><br>

<code>mvn exec:java -Dexec.mainClass="es.udc.isd032.races.client.ui.RaceServiceClient" -Dexec.args="-'option' 'args'"</code>

### Functionalities fully implemented
* <b>Functionality 1 (A1)</b>: *addRace()* (REST & THRIFT).
* <b>Functionality 3 (A1)</b>: *findRaces()* (REST & THRIFT).
* <b>Functionality 4 (A2)</b>: *createInscription()* (REST & THRIFT).
* <b>Functionality 5 (A2)</b>: *findInscriptions()* (REST & THRIFT).
* <b>Functionality 2 (A3)</b>: *findRace()* (REST).
* <b>Functionality 6 (A3)</b>: *markPickUp()* (REST).

