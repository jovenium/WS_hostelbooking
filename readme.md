
### I. PRE-REQUIS

a) Téléchargez le repository.

b) Importez les 4 projets sur l'IDE de votre choix (de préférence Eclipse IEE) et vérifiez que vous avez configurez un serveur Tomcat en version 7.0, Axis2, ainsi que JAVA en J2SE 1.5.

c) Installer MySQL en version console.

<br/>
<br/>

### II. BASE DE DONNEES

a) Ouvrez le fichier ```/DB_HOTEL/bdd-init.txt``` puis copiez son contenu et collez le sur votre console MySQL. Cela mettra en place la base de données pour vous avec un ensemble de données tests.

b) Ouvrez les fichiers ```WS_hostelbooking/WS_HotelFiltering/src/service/DatabaseService.java``` ainsi que ```WS_hostelbooking/WS_HotelAuthent/src/service/DatabaseService.java``` et remplacez l'utilisateur et le mot de passe d'accès à la base de données, par vos identifiants MySQL :

```
DatabaseService.co = DriverManager.getConnection(url,"user","password");
```

<br/>
<br/>

### III. SERVER TOMCAT 7.0

a) Sur la configuration de votre serveur Tomcat ```server.xml```, changé le port ouvert par le port 8081 :

```
<Connector connectionTimeout="20000" port="8081" protocol="HTTP/1.1" redirectPort="8443"/>
```

b) Ajoutez web-service ```WS_HotelReservation``` à votre serveur Tomcat en le définissant comme "Axis 2".

c) Rajoutez les web-services ```WS_HotelFiltering``` et ```HotelAuthent``` à votre serveur Tomcat.

d) lancez le serveur avec les 3 web-services.

<br/>
<br/>

### IV. LE CLIENT

a) Compilez et executez le Client (```WS_HotelCLient/src/ws.reservation/Client.java```).

7- Un menu se lance : 
	Dans un premier temps il nous invite à nous connecter nous vous avons créé des indentifiants :
	
	Nom : projetWeb | Mot de passe : ensiie2020 

	Ensuite un menu s'affiche vous proposant deux choix :

       		1) Recherche des chambres d'hôtel par critères
       		2) Réserver un chambre d'hotel

	Si aucune chambre n'est trouvée avec vos critères, on vous propose de faire une nouvelle recherche.

<br/>
<br/>

### V. BONUS Listes des routes

Voici la liste des routes de nos web-services :

WS_HotelFiltering :
-	/filter/params:\<params>
-	/reservation

WS_HotelReservation :
-	/listHotel  
-	/makeReservation

WS_HotelAuthent :  
-	/connexion