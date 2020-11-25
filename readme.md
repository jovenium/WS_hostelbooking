- setup db + les 2 pwd
- setup port tomcat
- lancement WS
- lancement client
- liste des routes pas WS (je les ai sur postman)



/*
        	 * ################### MOT DE PASSE UTILISATEUR MYSQL #####################
        	 */
        	DatabaseService.co = DriverManager.getConnection(url,"root","root");


Tutoriel :

1- Importer les dossiers du Github.

2- Importer les dossiers dans l'IDE Eclipse IEE

3- Pour installer la base de donnée :

	- Télécharger Mysql
	- Importer le contenu de bdd-init.txt dans la base de donnée depuis le dossier BD_HOTEL/

3- Créer un serveur Tomcat 

4- Ouvrir le dossier du serveur Tomcat / ouvrir server.xml / à la ligne 64 modifier le port : port="8081"

5- Créer un web-service, cocher "Axis 2" et selectionner l'ensemble des web-services : - WS_HotelAuthent
										       - WS_HotelClient
										       - WS_HotelFiltering
										       - WS_HotelReservation

6- Selectionnez client.java dans WS_HotelCLient/Java Resources/src/ws.reservation/ Vous pouvez maintenant lancer le serveur.

7- Un menu se lance : 
	Dans un premier temps il nous invite à nous connecter nous vous avons créé des indentifiants :
	
	Nom : projetWeb | Mot de passe : ensiie2020 

	Ensuite un menu s'affiche vous proposant deux choix :

       		1) Recherche des chambres d'hôtel par critères
       		2) Réserver un chambre d'hotel

	Si aucune chambre n'est trouvée avec vos critère, on vous propose de faire une nouvelle recherche.

