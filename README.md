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

# Commits

In this part contains the commits that were made by each student, specifying the functionality that was implemented
in each one.

### A1: Padín Torrente, Héctor (<a href="mailto:hector.padin@udc.es">hector.padin@udc.es</a>)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7fa62ba07614615c2b464c12f2e46afae7e1385b">7fa62ba0</a>: [Common] Initial commit.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/58ec112635a62919c5b7ab92a83c5e96f28e2bb6">58ec1126</a>: [Common] Bassic classes and model structure created.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/79284d1163c6cd356ae0a029a950e1720e40f349">79284d11</a>: [Common] Model constants created (Model).
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5dcf1ec8b830428c3917fd7b4073d9c0a7f2068f">5dcf1ec8</a>: [Common] Updated fields of the Mysql script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/825a3624b2edbce18248eae3ce717b850a96dd78">825a3624</a>: [Common] Fixing some errors at database script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a5fd61a9e18937eae08eabb4344a0e6ac3720164">a5fd61a9</a>: [Func-1] Method *create()* ready for revision.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/20bd8cc9db8083ae83ef18a47de7b523eb7d3041">20bd8cc9</a>: [Common] Fixed a small error at database script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/854c029c17b7a67a00fbe824d4dad1d43b718a5e">854c029c</a>: [Func-1] Finished *validateRace()* reviewed by A2 & A3.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d87f26bed564cf177cbae9a552ac1653af1e8cb7">d87f26be</a>: [Func-1] Fixed some errors at *createRace()*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/cc5396e9b89a602120c8af08fa6b7a79a6992017">cc5396e9</a>: [Func-1] [Func-2] created some test for *createRace()* and uploaded the method *findRace()*, which was made by A3, but had some troubles to upload it's code by a git conflict and I uploaded it for him.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/649bd1d9f40a7181ae9d4fb0efb4bd795256c8a6">649bd1d9</a>: [Func-3] First implementation of *findRaces()*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d7765cc993a689d921f145e95e42f1d3712b0812">d7765cc9</a>: [Func-3] Finished implementation of *findRaces()*, ready for Tests.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d9e91dfc815f14bd6d8cc512825431a0ea711066">d9e91dfc</a>: [Func-3] Fixing small errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4e192a951fdc4bfeb76ea8db8b40f1fb4d34b4c9">4e192a95</a>: [Func-1] [Func-3] Making first tests.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/508c9960ca6c939d392a6a6684f0645da11ca463">508c9960</a>: [Func-1] [Func-3] Making tests stronger.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7faa4630f2f61f0223cde9645844b157298aebd8">7faa4630</a>: [Common] Trying to fix misunderstanding of commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/cc5396e9b89a602120c8af08fa6b7a79a6992017">cc5396e9</a>.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9ae5fccd3342da8c2486e2642ad417aeffa94736">9ae5fccd</a>: [Common] Merge conflicts.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/051ef35a87983f833efa20c1b77c4adf72364796">051ef35a</a>: [Func-5] Reviewing method *findInscription()*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d2ac1b14270211b8aa76507bec53c2277e8718c0">d2ac1b14</a>: [Common] Fixing some overall errors in tests.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/57861f0c96e6041a8c3d037d06e533c63b3ca477">57861f0c</a>: [Common] Optimized code and removed unused imports.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bac62304b648b43fd16eefe3732cd57be453df15">bac62304</a>: [Func-1] [Func-3] Fixed model errors for *A1*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/642d18a3c0328af65b0cc445b6f29e2d920e0b47">642d18a3</a>: [Func-1] [Func-3] Fixed model errors for *A1* pt.2.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/78397a405d2b8cefc08321a1c01937aaea038cc5">78397a40</a>: [Func-1] [Func-3] Fixed model errors for *A1* pt.3.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5de67684e7f0cd04d2d090e9e656b100e832fd23">5de67684</a>: [Common] Fixed overall errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ae560a3b27debcc3a3c1f5f6392ddaaa050910d5">ae560a3b</a>: [Common] Fixed overall errors pt.2.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b23b24170b7b2244db4f87c767ba16a6db46c108">b23b2417</a>: [Common] Fixed overall errors pt.3.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ee62ac9f239b4f9693b79bd3c9630102940cb43c">ee62ac9f</a>: [Common] Creating Client structure.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b764f89273318864aead7a06d36691baa398ac19">b764f892</a>: [Common] Fixing model errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f9d1d50df77b9246075b206ca70a72855a5e7d34">f9d1d50d</a>: [Common] Creating Client-Service structure.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8a8f146b7e4f9c19f576d106380d37e8a68fe9f4">8a8f146b</a>: [Func-1] *doPost()* method created in *RaceServlet*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d7707b006d6d7d1e3d3ee304956d02c66f9dcd2c">d7707b00</a>: [Func-1] Basic Client-Service structure for A1 created.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5ca29d0d76d0f22714d18813681515fe4d3e824e">5ca29d0d</a>: [Func-1] [Func-3] Tried to start-up server, but failed.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8cd77c29c22a847f525a71cea1379f70c489ad7f">8cd77c29</a>: [Func-1] [Func-3] Server started.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b7b989097d53e1121f88b3766800389e8a08624c">b7b98909</a>: [Func-1] [Func-3] *createRace()* & *findRaces()* are working properly.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b2fc89f4eb285f10e2bf643d25bac75718e89f2d">b2fc89f4</a>: [Func-1] [Func-3] Improving A1 func. for service-client, IMPORTANT!: TODO: throw exceptions for methods.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0dcaaf9286be1d935151ae63368ebc2441270f54">0dcaaf92</a>: [Common] Detailing Commits at README.md.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8809d5b5a51b479224818411a0d99b38dc68e720">8809d5b5</a>: [Common] Markdown for A1.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f82dabe679e81e3e037ebaf551b103430661c42e">f82dabe6</a>: [Func-1] [Func-3] Reviewing exceptions for Func. 1 and fixed some common errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/71d33fbc84d0f24606f6e1171e8cbc0c3b57a967">71d33fbc</a>: [Func-1] [Func-3] Improving exception cases for client.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5a4e333c77a2fc98756bb5ee6fcaaeb3927f50d2">5a4e333c</a>: [Func-1] Improving exception cases in *RaceServlet*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/31493df6a82819f4f9fa826b4050ceb8b74d8afb">31493df6</a>: Merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/10dc0e89bd20bbeff9b50546e898cf37a50d0f93">10dc0e89</a>: [Func-1] [Func-3] Finished functionalities for A1.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/17bda928a9d151734a7b63eae490cb29982e01e7">17bda928</a>: [Func-1] [Func-3] Finished REST functionalities for A1.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5125e174c0a00929a24e63961b31cca8b4f189b2">5125e174</a>: Merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/879dbc8db026cb14df43dfb75cab3ed39f0f61d2">879dbc8d</a>: [Func-1] [Func-3] [Common]: Setting Thrift and script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5b9cd2eebffbfe61ea069733288f2880a6a4b05a">5b9cd2ee</a>: [Common] renaming script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f345f7f5881cf10bf8a804aedd423130f991aefe">f345f7f5</a>: [Common] adding links.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e150c2fe692d1d92994f2c75367272714ebd2c7c">e150c2fe</a>: [Common] improving script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/821d4a3eafcbafb40693f72e0e6f8afcd60ae390">821d4a3e</a>: [Func-1] [Func-3] Upgrading script and implementing thrift.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b908599417319a92cac4e04ff5f8571fba6b890d">b9085994</a>: [Func-1] [Func-3] Failed attempt to set up thrift.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7fb2723872a319b16018a8602f353b3dd39362eb">7fb27238</a>: [Func-1] *addRaces*(Thrift) is working.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fa5fa8b9cd33e9b982f3b5950d63dbff7e40cf38">fa5fa8b9</a>: [Func-3] *findRaces* finished for Thrift.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/01dd03f8a13ddb00a9de32404974f2a6b56c157d">01dd03f8</a>: [Common] improving script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/db501958f61106073038ef08edba0dd02e46e349">db501958</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/756c57b58ac360660c8fe915b2b5a44e7c92e981">756c57b5</a>: [Common] another merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b3d551231da15487e01595a2cbfebd3292636e2c">b3d55123</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/71b9bdbc812c63e4acb02b9cca42431c912de877">71b9bdbc</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5bdff0102077d2b4f0968b6164fcd54d1ca887a7">5bdff010</a>: [Func-4] main for addInscription created.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c2a55e87d756f28faf946643614cd5f38aeb4101">c2a55e87</a>: [Common] improving script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f1f9069d5920e86d1c0b863e55300de84df39d4a">f1f9069d</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9b71dbc96a3270ec0aaf6d64d8d5fc83730f8c08">9b71dbc9</a>: [Func-4] fixing error at *RaceServiceClient*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0f181c1220e207326e31913e8787ade235f23129">0f181c12</a>: [Func-4] fixing small errors at *RaceServiceClient*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/54193006682304f3424105926d2b34b391ff18d4">54193006</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f00692bab09d63e46420d40282461886448b5bab">f00692ba</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f04418bfd0e249f7513d89b3bc18b944081a9f0c">f04418bf</a>: [Common] reviewing code.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7d6de58d12967236c12347af8dbd8ca2425f0721">7d6de58d</a>: [Common] removing log.txt
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9d1b1ae209a33d2178b345623cf4aa1ff34e998e">9d1b1ae2</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9683aaf12fb376c2fa9ce8a13a254607900f02bc">9683aaf1</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0e1e56430b95e0b7518f076f4995734c0c83f41a">0e1e5643</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/df52fbf4ea043d129887a289970ee01ee7193d94">df52fbf4</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5215ed6b87f60be96e4b7c027e898078ee0f4693">5215ed6b</a>: [Common] improving script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4d8cfb18f07463d36ae761819f526969d99b5d7c">4d8cfb18</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7809f551c43ee5f03c48df203c996e994f292e42">7809f551</a>: [Common] fixing repository errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f087995d51eef0a92e91300efa127a09499e1eb0">f087995d</a>: [Common] improving script.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/56cc1d8aa4bfbf2a4216031a1b02728a5bae5063">56cc1d8a</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/729421d2602fa0bf17afd58852415c2c11c5b311">729421d2</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1bea3cd56bd636e10a9e74d83446a88f608ee0da">1bea3cd5</a>: [Common] reviewing proyect.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d47e2eccf5421cc99abaead4876744c59a9caaf6">d47e2ecc</a>: [Func-1] Reviewing func1.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/696dda16dc7df629a0c9cb3f3a15b4f5a8f02be0">696dda16</a>: [Func-3] Added an extra test in PRUEBAS.txt.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4684ceb99afe50b565274ee930b11e3597e944e2">4684ceb9</a>: [Func-1] [Func-3] Fixing Thrift errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a15dfa415be1c75790b3c823073e941240102c12">a15dfa41</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f295f7deff72ca53597edc15ada2f4b975cf6458">f295f7de</a>: [Common] MarkDown for A1.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fa27b47126e46791d771b20168965ecc0deab360">fa27b471</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b17fd2445000a0319edd4f995bb3fc44d693a06d">b17fd244</a>: [Func-4] fixed small error throwing thrift exceptions.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e9114cb5d4306e532cb9cd344a6489ed29907bab">e9114cb5</a>: [Func-3] fixed small error throwing thrift exceptions.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/6f4eb3a3708728bfb6d57b4fc9e192c300aeb1ff">6f4eb3a3</a>: [Common] Reviewing proyect adding *TODOs*.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e70bcd4ce3a88df41a029a680219b37f109e5032">e70bcd4c</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d672883e83bbd911cc3a03c580d3615e392396e7">d672883e</a>: [Common] defining exceptions.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/db55b708fc1828f12762ebe375a22f29e6bf23b2">db55b708</a>: [Common] merge.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/64a8f74ce1ded45e56477ef34506f6f7f8d29248">64a8f74c</a>: [Common] fixing common errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1c5237e736277d15624989062c15cba21fbf02a1">1c5237e7</a>: [Common] Markdown for A1
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/be45f542d38d57203d4c3727f3bcc0e99c1c17f8">be45f542</a>: [Common] reviewing proyect.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b256c4c6652870f70fe538fc3b60674dea1ce7f7">b256c4c6</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/419ae19b776ef7328246a7ef9461f2382e0d2cd9">419ae19b</a>: [Func-2] Fixing method DoGet (review).
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fe5f87e4960f5bf3dd0a1d2d1c03ea20a0e0b312">fe5f87e4</a>: [Fun-2] Finishing method DoGet. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c75095cf9cfc0199ce63a9b24a29b1a500ebb957">c75095cf</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e68ed8f733fd773ba5dab508bcc34233b5cb3bb8">e68ed8f7</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/367642f6c10e042dde3519f59547e4c06c9b4358">367642f6</a>: [Func-6] Fixing small errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bf349adefac100fa3f77efa2d155ec51a333fd0d">bf349ade</a>: [Func-3] Fixing some thrift errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a44f24078a4d5eb50651e19e5a58f787a259385c">a44f2407</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ea6c0c501944027ad523e20438677ed0769c1dd2">ea6c0c50</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/069dedcd09f49ce2e1d5fa033ece8685776fc281">069dedcd</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/aed261a5d1d5a7ff3cad04d9e06b3639ad8e3a1f">aed261a5</a>: [Common] Fixing client errors.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/2c078313507938e1d9e9a3b1d04fba2b6f9fe702">2c078313</a>: [Common] TODO: Client exception 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/6c678bc4061439fe0bfb082cae21a1ddc926aa3c">6c678bc4</a>: [Common] ClientAlreadyInscribedException done
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b3dd0ee815b3d8585e3dfbdc077cb7928195fd1f">b3dd0ee8</a>: [Common] ClientAlreadyPickedUpException && ClientAuthenticationException done
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3189f924da50a9d66e592e44bf04bbe937a9ff52">3189f924</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/47337a85633c712579aa8305bbe08a6a641f36b8">47337a85</a>: [Common] Changin exceptions at client.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/372d79fdd573feb4c825bdbadf0bfa211b55e72b">372d79fd</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a66b4b1b02189840a1969afe06bc5e2aca2eb7c2">a66b4b1b</a>: [Common] Fixing errors and testing exception throwing.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c3cb2540724856fcaafc4cf8788679f585519067">c3cb2540</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/76bec83ce4861917c4938d2e39c7b460509e3e48">76bec83c</a>: [Func-2] [Func-6] added to PRUEBAS.txt
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1790dbbe019586663e82e11b772c5fa5896a4ce0">1790dbbe</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e22ced62e55b96320eb4c7aaedd38d7ad715503f">e22ced62</a>: [Common] Reviewed proyect
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/787507dae8df651f3d8d8a247a10af300e8a7ba6">787507da</a>: [Common] finished review
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/501b7a7e51eacafb0f7cdace565fb5e8a9e29728">501b7a7e</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fd51a3ae4d1d8a46ee458ae6d49f272da7f5e2bb">fd51a3ae</a>: [Func-5] Fixed a small error in thrift. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/28ca9ae11a984157982aa7464d427bfd5f39b4af">28ca9ae1</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1ca9f8ad044840339c5e86294093d085c79d8526">1ca9f8ad</a>: [Func-2] Fixed path access error. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/76e02d01a2bf60d251d9457de4d2ad498644784b">76e02d01</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f3417d74530a7631b3cfe479390867dd9330c810">f3417d74</a>: [Common] merge
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e9a5a7a77ec0da36d004edcc0a1606b06c2e4cc1">e9a5a7a7</a>: [Common] Last review 

    
### A2: Cascón Padrón, Luis (<a href="mailto:luis.cascon@udc.es">luis.cascon@udc.es</a>)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/afeb91dc925fbc5a5906da0285fb5b787cbc14cb">afeb91dc</a>: [Common] File inscription and Jdbc3SqlInscriptionDao finished. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/eee9093e77ffa96b3417a2a7471341a84dc559bc">eee9093e</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/dd15500f3686a9e620ca3bbbfa5997d9ca507fd9">dd15500f</a>: [Common] Solving merge conflicts on RunFicService and RunFicServiceImplementation. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/324669d3802231ba629bcf034c6b4df131cd40c8">324669d3</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9b3caf3256103835ed6ba0e1059cf450a63d500f">9b3caf32</a>: [Func-4] Finished createInscription (RunFicServiceImplementation). 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/2e52f011367da3e61b8f860d24538d25176a6399">2e52f011</a>: [Func-5] findUserInscriptions (RunFicServiceImplementation) trying. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/21ce41c83bb6247f1cee817b958390a167d18bff">21ce41c8</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9a5093127c04b71465f21e4301546e1150603026">9a509312</a>: [Common] Doing AbstractInscriptionDao and doing small changes. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/355b7937ec9cf2ab9498b8962810a79702afedc4">9a509312</a>: [Common] Merge remote-tracking branch 'origin/master'.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3ae4111b1b6608c786d58cbff27b6ba93cac108a">3ae4111b</a>: [Func-5] Finished findInscription (AbstractInscriptionDao & RunFicServiceImplementation) and testAddInscription, ready to revision. Some new functions for the test.  
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/92b5adccdefc3ad90d9940d0c5aff24dead370b3">92b5adcc</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e40ba40b7d57fdb4d7af765f16c3c575890ed8c8">e40ba40b</a>: [Common] Small changes on RunFicService and RunFicServiceImplementation. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/82cb6d02fc404d070749ad8bccb9b87289198303">82cb6d02</a>: [Func-4] Finished remove on AbstractInscriptionDao. InscriptionOutOfTimeException created. Some new functions on test and test for addInscription created. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/118fe4ad0929bfa7e30dfdc750b6e00820c5b5b5">118fe4ad</a>: [Func-5] Finished testFindInscriptions and testAddAndFindInscription. Problems with NumberFormatException.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ad95d1da3aa74115b00766ab898d49bb8606faf8">ad95d1da</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a73ba96fa31415ef14f86e1c14aa093e360f5685">a73ba96f</a>: [Func-5] testFindInscription NumberFormatException not solved. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5e1961d70cbcaef1577db6283d6a915bd7501327">5e1961d7</a>: [Func-5] testFindInscription NumberFormatException not solved. (2)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f51b49c3502b29b98152b98028cbc7f9e643b9a6">f51b49c3</a>: [Func-5] testFindInscription doesn't work. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f5c896783d09b4b141f73d0a33c4b6a43d43afb5">f5c89678</a>: [Func-5] Finished testFindInscription . 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fb36aed95c4ef6b1a5b25d2e9b83f1a0bc1afecd">fb36aed9</a>: [Func-5] Finished testFindInscription(Eliminated some system.out.println). 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4de366507fcba0098ffed666b177c3a9a53b120f">4de36650</a>: [Common] removeInscription updated(RunFicServiceImplementation) . 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d7ecf4ce5fcac83b300c68904056955bc88c0bd4">d7ecf4ce</a>: [Func-5] testFindInscriptions updated. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5d99000896e447af92d612b35f7bb11348b10527">5d990008</a>: [Common] Merge remote-tracking branch 'origin/master'. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/140e517d859d1360d044fb178a636710486c7205">140e517d</a>: [Common] Tests A2 working. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7fd0346fa3553a131e948ce2aaf3f5475b9f9d1c">7fd0346f</a>: [Common] Update function(AbstractRaceDao & RunFicServiceTest) updated but doesnt work. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/acb7a0b1bcc6598b6cc027453aab6e70f2cfc15e">acb7a0b1</a>: [Common] Update working . 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0eaad247dfbf88d00096954c2abd440a566a9b6e">0eaad247</a>: [Common] Dorsal values now working. 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a56e55f50973437f3b54b19a2cd7ddd9174395c8">a56e55f5</a>: [Common] JsonToExceptionConversor finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/efe3d771d0ea226d5a0d9e34dd493e50c0b641a8">efe3d771</a>: [Common] JsonToRestInscriptionDtoConversor finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/349fa22ca182eff1e7d86f30e004c9a4051efd4b">349fa22c</a>: [Common] InscriptionServlet doPost finished, doGet init
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4c0dfe75ea2c94f4ad8868be62d29e1bd9fb0ad0">4c0dfe75</a>: [Common] doGet(InscriptionServlet) and toArrayNode(JsonToRestInscriptionDtoConversor)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8939e7faf1388f198ed47217182631eb0e6edece">8939e7fa</a>: [Common] ClientInscriptionDto finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ce6ca2d638013e95aea0f070eb8c8268e426eb28">ce6ca2d6</a>: [Common] Changes on script prueba.sh, JsonToClientInscriptionDtoConversor and JsonToClientInscriptionDtoConversor finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/567c1552e0199586b3da701d055a4b086f3ff1e6">567c1552</a>: [Common] Changes on runfic.thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/81561427ac5df41d23cac777f3da724a7593225b">81561427</a>: [Common] Changes on runfic.thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/6f4bb6a96b11e955d832e2719a11543720b5a22d">6f4bb6a9</a>: [Common] Changes on runfic.thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d6e0f953c335e26cdf1afaf0127464f4a8cd54f6">d6e0f953</a>: [Common] Changes on runfic.thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1b704cdf9be96ceda155f385cb3ca4e4fc7fa4cd">1b704cdf</a>: [Func-4] [Func-5] Changes on runfic.thrift and ThriftRunFicServiceImpl (createInscription and findInscription)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bd8e63acc8b58ba52b733367baa8f3f5ded3f596">bd8e63ac</a>: [Common] Changes on JsonToClientExceptionConversor(toInscriptionOutOfTimeException), RestClientRaceService, ClientRaceService
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7ec941b5b73ffa29f4dc3dfff08564bbb8e672d4">7ec941b5</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1ca17a1ce06e92a53f235fc55ed0b00dcf3677c4">1ca17a1c</a>: [Func-4] Changes on JsonToClientExceptionConversor, RestClientRaceService(addInscription), ClientRaceService
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c11151e6879292acc3571c01dab81fd3c684cc06">c11151e6</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/635ce6f328d0b0576d609250993687721ef774e8">635ce6f3</a>: [Func-5] Changes on JsonToClientInscriptionDtoConversor, RestClientRaceService(findInscriptions), ThriftClientRaceService(creating methods)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b7d0174f9c6576eef5c46fa19a725a4797590000">b7d0174f</a>: [Common] Changes on InscriptionServlet and pruebas.sh
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f325be7c224b8da54260f2c938a2fb8d7f251f77">f325be7c</a>: [Common] Changes on JsonToClientInscriptionDtoConversor and JsonToRestInscriptionDtoConversor
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/220599538183d2e88a9c5b47081c4b1cfc7f8e22">22059953</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/208acc5ad5af980d70d3a65c3e94cab05fcedd0c">208acc5a</a>: [Func-5] Changes on RaceServiceClient (operation findInscriptions) and JsonToClientInscriptionDtoConversor
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/698be696cfe17a821020e99a3b531ec30bddfbca">698be696</a>: [Common] Changes on InscriptionServlet (Exceptions on doPost)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/02a633d835cf77c55b7a703f03de1d911950cf8a">02a633d8</a>: [Common] Changes on JsonToClientExceptionConversor(Error codes), RestClientRaceService(Error codes) and AuthenticationException.
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d1365b2a7d82df03606ea84a9d9e8fae7f322c85">d1365b2a</a>: [Common] Changes on exceptions(InscriptionServelet and JsonToClientExceptionConversor)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1fa4194f1d925d8eb2470634d6b6814ecb5dd8a3">1fa4194f</a>: [Common] Changes on exceptions (JsonToClientExceptionConversor and JsonToExceptionConversor)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7079778a2af660ff8de4b98f1d93cb274d52def4">7079778a</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b96e952c0b27915a71af34d801cecd44235d805a">b96e952c</a>: [Common] Exceptions changes(JsonToClientExceptionConversor)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/81c1f49f2a730797ca3550809bc873f562514010">81c1f49f</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/13f8b6a5aae005f33f038638d194acc5da62acc0">13f8b6a5</a>: [Common] Thrift A2 finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/62765050e455d2f90387079632b1fe50511b6d45">62765050</a>: [Common] Thrift A2 finished (Problem with ThriftRunFicServiceIface)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c4b3d7604ffe948a55f2d9145e440c177159aeab">c4b3d760</a>: [Common] Thrift A2 finished (Problem with ThriftRunFicServiceIface)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/fe1260b7b2fb7f3eeb9b0e890837349d32607362">fe1260b7</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1061d8a9b9d87d03dab1bfbee0f4f1556a9e9aea">1061d8a9</a>: [Common] Cambios por problemas con los commits
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4debda42153118c598c1f369e105a9ba02c5c47a">4debda42</a>: [Common] Thrift A2 finished 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/35bb37fc96a414eaa162e1bb12d1e17da9e9b21c">35bb37fc</a>: [Common] Changes on pruebas.sh and A2 commits(IT1) added to README.md
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8881b8e6fb2c9dbdce3cbf95c9b9a16b4d8ced46">8881b8e6</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9932e6e6a332e74a2827f5f6de7b5bbfa762e81e">9932e6e6</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/02178ffa7fd62f67305bb30483c4439914a33951">02178ffa</a>: [Common] ifIsPicked deleted
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/61928a2f40e4706a24f75546b8335c03bfe941ce">61928a2f</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0406586d01530dab1f9ded7c111c7297d08f0c9c">0406586d</a>: [Common] Pruebas.txt A2 finished
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1ffcc41dbb33630c84b772d4470dad8eeb52c24f">1ffcc41d</a>: [Common] Now throwing InputValidationException in RestClientRaceService
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/84620fd4bddd59278f87541c28d3345e95561674">84620fd4</a>: [Common] ThriftInstanceNotFoundException created
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c66f8c4ddd325fad2fab6110cdc01926308c8040">c66f8c4d</a>: [Common] All thrift exceptions (A2) fixed up
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7e14165f9ffc8bc7ba19b56612ad343ffcbfa02b">7e14165f</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4c3e3f323d9fb1452f411369284b1b2cf0a1f819">4c3e3f32</a>: [Common] Commits A2 added on README.md
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5d4039bafb1f44d7a1f3a874826e1819f1c9f396">5d4039ba</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8f61f27d55de7271ae7cabc71a826a4f4aa66950">8f61f27d</a>: [Common] Minor changes
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f72dc6646118dc43f5dad811e9271b7501c820ee">f72dc664</a>: [Common] Change on prueba.sh(one line)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b00ea2b4317f0103375fe568026b64ce275a00e2">b00ea2b4</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/8b20e418a0af9e968f92522829427df56b433ede">8b20e418</a>: [Common] change codeError(AlreadyInscribedException)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/cbcb4bfc9d9683c44988a677f884049a1aee3e64">cbcb4bfc</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bdf59faa6389b7a6caaf1378a4d26cfdee656d41">bdf59faa</a>: [Common] Commits on README A2
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/dc907a737a2e0d8d086c0e91989b3044bae68b15">dc907a73</a>: [Common] Commits on README A2
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9363faf5b090a4d0743ab3c8d00bdc6ea8cbb195">9363faf5</a>: [Common] Fixing AlreadyPickedUpException
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5a771dd689f2be610df7ceadb09a516d4c9866d0">5a771dd6</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/64d9020e9aa90985cb6870d04b27a8706410a842">64d9020e</a>: [Common] Fixing AlreadyPickedUpException on thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/dd96bc3a7fa3f43b4f7e7f7dd582d66a218e0b99">dd96bc3a</a>: [Common][Func-2][Func-6] Changes on doGet(InscriptionServelet), property validator killed. Changes on -delivereNumber and -findRace(RaceServiceClient). Changes on FinRace exceptions (RestClientRaceService) 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1180acbec2bf1259e245e4759d3877db10f30992">1180acbe</a>: [Common] Changes on -delivereNumber (RaceServiceClient).Changes on doPost InscriptionServlet
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a976fd1f0588bfee29d66d6a437b8fbd73c86ea2">a976fd1f</a>: [Common] Client exceptions created
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/37d5be52eefd19f65d05f1028617045487a900af">37d5be52</a>: [Common] Client exceptions changed on JsonToClientExceptionConversor
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9277e69af8ff6214e654e33cc49dadc99b6b6758">9277e69a</a>: [Common] Problems with ClientAlreadyInscribedException
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/f2ac78366dff09d351de8ef3d1ff5fe18cc537f9">f2ac7836</a>: [Common] Added some commits to README.md
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c7e2dea5e6605a2f3c73c5e497fcc9bb03154af0">c7e2dea5</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5732ae490c71f10f4a9babdcb03a776c93e48426">5732ae49</a>: [Common] [Func-6] Created ThriftAlreadyPickedUpException and throwing it
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c2e89c36f432e3e21293791645a9fe28b1b53db0">c2e89c36</a>: [Common] Changes on thrift
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/d0fb8dd1f506bcdbab4dca1047f0d2f212cd7b64">d0fb8dd1</a>: [Common] Changes on rest
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b8a0b993492667dde7455c270482022fa3b929ee">b8a0b993</a>: [Common] Merge remote-tracking branch 'origin/master'
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3df401c97f2ce1a0608c19d8c8f666c77dd694e9">3df401c9</a>: [Common] Commits added to README.md
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/5545af8c57cae8884b60b1878122ea89b7eccf13">5545af8c</a>: [Common] Optimized imports
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/831a39858f234288d5c910996c6e79d102b81030">831a3985</a>: [Common] Merge remote-tracking branch 'origin/master' 


### A3: Rodríguez Muñoz, Xavier (<a href="mailto:xavier.rodriguez.muñoz@udc.es">xavier.rodriguez.muñoz@udc.es</a>)

* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/588ab8d09a15b30c5242d7803b7ace915908c851">588ab8d0</a>: [Common] Creadas las tablas para las carreras y las incripciones para la BBDD 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0016f072a5b96448a7593bd8147f22a848dc2080">0016f072</a>: [Common] Empezado el update de las inscripciones (Sin terminar) 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/9104aae408a856c2cb2e630e4362debdaf114836">9104aae4</a>: [Func-6] Completado el contenido correspondiente con la tarea 6 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/06d2a0d816863eb5c7ef714c2c36e79fc19e1394">06d2a0d8</a>: [Common] Solucionado problema con el nombre de la funcion update 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/7211683c677610b60572f0ae5b6eba0bf8a4460d">7211683c</a>: [Func-2] Update AbstractRaceDao.java, fixing task2 implementation 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/99ccf35e6e05cdf193a3699bb9464ae125e6f557">99ccf35e</a>: [Func-2] Update AbstractRaceDao.java, implementation of task2 completed 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/11b47229ea99ec90479eeb9c73f1b041c9721607">11b47229</a>: [Func-2] Fixing findRace implementation 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/32031d86f381c893e172b72f4153d4149b7083b2">32031d86</a>: [Func-2] findRace method in service completed
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/0e31f8d878afdfc2b23118c2d80708e2a09e23a8">0e31f8d8</a>: [Func-6] Finished implementation of markPickUp in RunFicServiceImplementation
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3e40cd0b88dafde4e8caa777df36326c8e9d1b50">3e40cd0b</a>: [Common] A method in the test had movie in the name instead of race 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3a05f515e9e143a6eaedb7f0f42a7230e26282c9">3a05f515</a>: [Func-6] All tests added for the markPickUp method 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4702995ed85553b01a9e5f01ffeb9f1b483632d7">4702995e</a>: [Common] Solved problem in the last three tests with names of variables 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/1065509028ac620af8db77212afe40c591c8aaf2">10655090</a>: [Common] Solved problem compairing strings 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/05618bdf1adfcb40b5788028857d891999048ab4">05618bdf</a>: [Func-6] Fixed issue with excpetions on the DAO and Service
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e098a7c14fb33c01c6455d305150d56ff8ef78eb">e098a7c1</a>: [Common] Tag it-1 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bb944b3ea9c2ca69037eeb30024c04ef64dca1ec">bb944b3e</a>: [Common]  Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/2fa75780f60ff75602f1aeb186364fb292a643cc">2fa75780</a>: [Func-6] Fixed some issues with markPickUp and updateInscription 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c7ee6527515bfa3361abd37cd0016590b31ffa31">c7ee6527</a>: [Func-2] Added findByInscriptionId in Service and DAO, fixed validation issues in markPickedUp and the corresponding tests
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/01aa8966929584cc1da61f892e0376c56dfcd8af">01aa8966</a>: [Common] Implemented Inscriptions for RestServiceDto + Conversor 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/718c917665454a853498601046980694822b998e">718c9176</a>: [Common] Fixed naming issues in ClienteRaceDtoToThriftRaceDtoConversor, started modifying GET request to fit findRace
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b84b5cb0c156b5ed297d79df4fa498465b0e92b0">b84b5cb0</a>: [Common]  Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/aabc2344220eb85fc6b8af603222f2e8f4605664">aabc2344</a>: [Func-2] Finished findRace
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a18d14e7b1e8653838a9592640884f79f588890b">a18d14e7</a>: [Func-2] Fixed issues with thrift implementation for findRace 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/4dadeddba81cbb3a0ae7f44b7f21251655ea8b5f">4dadeddb</a>: [Common] Fixed naming issues in ClienteRaceDtoToThriftRaceDtoConversor, started modifying GET request to fit findRace
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/c424a66eb04f1f598877660b039e013948d426a3">c424a66e</a>: [Common] merge 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/25984b5c1d0438f1c213ab5c1fa618d4a2c4dad4">25984b5c</a>: [Common] Merge remote-tracking branch 'refs/remotes/origin/master' 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/2fe6da8a2b4fcb912abb2f4bb2ffc0e09cccc9ef">2fe6da8a</a>: [Func-2] Finished implementing findRace in the client
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/e5bb67e59fd83e5ab9be0314192d13a7cbd0f749">e5bb67e5</a>: [Func-2] Fixing issues with findRace 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/786bda434c2b3a315a74446b1389adb7e9e3663b">786bda43</a>: [Func-2] Fixed issues with findRace in RestClientRaceService 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/bccab5b8546331ae65730dbcc958925bfd28a1ef">bccab5b8</a>: [Func-6] Started implementing markPickUp (Not working properly yet)
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/3d1d2a5d0aba1b2c26b00d2cd13431f66b10ca4e">3d1d2a5d</a>: [Func-6] Incompatible types error in RaceServiceClient, working on it 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/433db335696942cd10e98320df294d7a79b5d3c7">433db335</a>: [Func-6] Started implementing PUT function for markPickUp
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/69ab5dfdcf978d3aa9981e5125e4c8c39f922ad9">69ab5dfd</a>: [Func-6] Commented PUT function starting to implement markPickUp with POST
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/ff1b068124672e41669b33d0a61536546d3b7657">ff1b0681</a>: [Func-6] Finished markPickUp 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/66baa4246ab17d22dcd006c52fd4f75cdc33d750">66baa424</a>: [Common] A3 Fixed issues with errors and exceptions, re-implemented findRace and markPickUp 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/b816ed86444078894e8678dda841b9cb7d5ab359">b816ed86</a>: [Common] A3 deleted temporary comments 
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/a25c9caf57087d86033922ef4accca419fac0189">a25c9caf</a>: [Common] A3 Updated Exception names in pruebas.sh
* Commit <a href="https://git.fic.udc.es/docencia-isd-2021/isd032/commit/48baae4b6f9a1cfe7177f22d3f604bfbcc8ccc63">48baae4b</a>: [Common] Merge branch 'master' of git.fic.udc.es:docencia-isd-2021/isd032
