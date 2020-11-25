package ws.reservation;

import java.io.Console;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.axis2.AxisFault;

import ws.reservation.ReservationStub.MakeReservationResponse;

/**
 * 
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class Client {
	
	public static Scanner choiceS = new Scanner(System.in);
	
	//Fonction main qui lance le menu et permet au client de lancer des recherches
	public static void main(String[] args) throws AxisFault
	{
		int choice;
		ReservationStub stub = new ReservationStub();
		Console values = System.console();
		
		//On lance la connexion avec les identifiants du client et on obtient son identifiant
		String customer_id = AuthentCall.makeAuthent();
		
		//On propose au client deux choix, chercher les hotels disponibles 1) ou faire une reservation 2)
		while(true) {
		System.out.println("\n########## MENU ##########");
		System.out.println("1) Check hostels availability");
		System.out.println("2) Make a reservation");
		
		//On verifie la pertinence de sa reponse
		try {
			choice = Integer.parseInt(choiceS.nextLine());
		
		if(choice == 1) {
			System.out.println("You have chosen : Check hostels availability");
			listHotel(stub, values);
		}
		else if(choice == 2) {
			System.out.println("You have chosen : Make a reservation");
			MakeReservation(stub, values, customer_id);
		}
		else {
			System.out.println("No choice selected");
		}
		}
		catch (NumberFormatException e) {
			System.out.println("You must choose an integer, 1 or 2");
		}
	}
	}
	
	//Methode qui teste si les dates de debut et de fin saisies sont correctes pour faire la recherche
	//En fonction du resultat on envoi une reponse positive ou negative
	private static boolean dateChecking(String start_date, String end_date) { 
	    Date start = null;
	    Date end = null;
	    boolean response = false;
	    
			try {
				start = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
				end = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);
				//On test si les deux dates ne sont pas nulles
				if(start != null && end != null) 
				{
					int different = start.compareTo(end);
					//On teste si la date de fin est apres la date de debut
					if(different > 0 ) {
						System.out.println("Error in the selected dates, End date must be after Start date");
						response = false;
					}
					else {
						System.out.println("Dates of Start and End for reservation are ok");
						response = true;
					}
				}
				else {
					System.out.println("Dates Start and End must not be null");
					response = false;
				}
			} catch (ParseException e) {
				System.out.println("Dates formats are wrong");
			}
		
		return response;
	}
	//Methode qui teste si le nombre de place saisi est un entier positif et non null
	//En fonction du resultat on envoi une reponse positive ou negative
	private static boolean placeChecking(String nb_place) {
		boolean response = false;
		//On teste si nombre n'est pas null
		if(nb_place == null || nb_place.equals("")){
			nb_place="null";
			response = true;
		}else{
			try {
				//On test si le nombre saisi est un entier
				int count = Integer.parseInt(nb_place);  
				//On verifie que ce ne soit pas un entier null
				if(count <= 0) {
					System.out.println("Error in number of place(s) choosen it must be at least of 1");
					response = false;
				}
				else {
					response = true;
				}
			} catch (NumberFormatException e) {
				response = false;
				System.out.println("Number of place(s) must be an integer number");
			}
		}
		return response;
	}
	//Methode qui teste si le prix maximum saisi est un entier positif et non null	//En fonction du resultat on envoi une reponse positive ou negative
	//En fonction du resultat on envoi une reponse positive ou negative
	private static boolean maxPriceChecking(String max_price) {
		boolean response = false;
		int price;
		//On teste si le prix saisi n'est pas null
		if(max_price == null || max_price.equals("")){
			max_price="null";
			response = true;
		}else{
			try {
				//On teste si le prix saisi est bien un entier
				price = Integer.parseInt(max_price);
				//On teste si le prix saisi est bien positif et entier
				if(price <= 0) {
					System.out.println("Maximum price can't be negative of null");
					response = false;
				}
				else {
					response = true;
				}
			} catch (NumberFormatException e) {
				response = false;
				System.out.println("Price must be an integer number");	
			}
		}
		return response;
	}
	
	//Methode pour realiser une reservation, lit en entree les valeurs et les envoies au service reservation 
	// renvoi une reponse de succes ou d'echec de la reservation
	public static void MakeReservation(ReservationStub stub, Console values, String customer_id)
	{
		ReservationStub.MakeReservation informations = new ReservationStub.MakeReservation();
		boolean verification_dates = false;
		boolean verification_room_id = false;
		
		String room_id,start,end;
		
		//On lit le numero de chambre saisi, si le format est correcte on sort de la boucle.
		do {
		System.out.println("Id of the room [needed] : ");
		room_id = choiceS.nextLine();
		verification_room_id = room_idChecking(room_id);
		}while(verification_room_id == false);
		
		//On lit les dates de debut et de fin, si les formats sont correctes on sort de la boucle.
		do {
			System.out.println("Start date (dd/mm/yyyy) [needed] : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd/mm/yyyy) [needed] : ");
			end = choiceS.nextLine();
			verification_dates = dateChecking(start, end);
		} while(verification_dates == false);
		
		//On converti le format des dates dd/mm/yyyy au format yyyy-mm-dd
		start = convertionTime(start);
		end = convertionTime(end);
		
		//On passe les parametres au service reservation
		informations.setCustomer_id(customer_id);
		informations.setRoom_id(room_id);
		informations.setStart_date(start);
		informations.setEnd_date(end);
		
		//On recupere la reponse qui est directement formulee par le service reservation
		MakeReservationResponse response;
		try {
			response = stub.makeReservation(informations);
			String result = response.get_return();
			System.out.println("\n"+result);	
			
		} catch (RemoteException e) {
			System.out.println("Connection error to Reservation Webservice");	
		}
	}
	
	//Methode pour realiser une recherche de chambre d'hotel, lit en entree les valeurs et les envoies au service reservation 
	// renvoi une liste de chambre hotel correspondant aux critères.
	// Si un champ non obligatoire n'est pas saisi, on prend toutes les valeurs repondant a ce critere
	public static void listHotel(ReservationStub stub, Console values)
	{
		ReservationStub.ListHotel informations = new ReservationStub.ListHotel();
		
		boolean verification_places = false;
		boolean verification_dates = false;
		boolean verification_prices = false;
		boolean verification_location = false;
		
		String start, end, room_count,max_price, location;
		
		//On lit le prix maximum saisi, si le format est correcte on sort de la boucle
		//Cette saisie n'est pas obligatoire
		do {
			System.out.println("Max room price : ");
			max_price = choiceS.nextLine();
			verification_prices = maxPriceChecking(max_price);
		} while(verification_prices == false);
		
		//On lit le nombre de chambre saisi, si le format est correcte on sort de la boucle
		//Cette saisie n'est pas obligatoire
		do {
			System.out.println("Number of rooms : ");
			room_count = choiceS.nextLine();
			verification_places = placeChecking(room_count);
		} while(verification_places == false);
		
		//On lit la ville saisie, si le format est correcte on sort de la boucle
		//Cette saisie n'est pas obligatoire
		do {
		System.out.println("Asked city : ");
		location = choiceS.nextLine();
		verification_location = locationChecking(location);
		}while(verification_location == false);

		//On lit les dates de debut et de fin, si les formats sont correctes on sort de la boucle
		//Ces informations sont obligatoires
		do {
			System.out.println("Start date (dd/mm/yyyy) [needed] : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd/mm/yyyy) [needed] : ");
			end = choiceS.nextLine();
			verification_dates = dateChecking(start, end);
		} while(verification_dates == false);
		
		//On converti le format des dates dd/mm/yyyy au format yyyy-mm-dd
		start = convertionTime(start);
		end = convertionTime(end);
		
		//Si les valeurs saisie sont vides, on les définies sur null
		if(max_price == null || max_price.equals("")){
			max_price="null";
		}
		if(room_count == null || room_count.equals("")){
			room_count="null";
		}
		if(location == null || location.equals("")){
			location="null";
		}
		//On passe les parametres au service reservation
		informations.setMax_price(max_price);
		informations.setNb_place(room_count);
		informations.setLocation(location);
		informations.setStart_date(start);
		informations.setEnd_date(end);
		
		//Partie  affichage des resultats
		//On recupere les resultats sous forme d'une chaine de caractere
		//Chaque hotel a 10 parametres, on met en forme les resultats dans un tableau
		System.out.println("\n########## START RESEARCH ##########");
		ReservationStub.ListHotelResponse response;
		try {
			response = stub.listHotel(informations);
			String result = response.get_return();
			if(result.equals("False")) {
				//Si il n'y a pas de resultat repondant aux criteres on affiche ce message
				System.out.println("Sorry, we haven't found a room for you according to your criteria\n\nI'm sure you will find an other room !");
			}
			else {
				//On met en forme le tableau a partir de la chaine de caractere
				int nb_room = ((result.split(";")).length)/10;
				System.out.println("\nThere is "+nb_room+" room founded : \n");
				String [] datas = result.split(";");
				//On ajoute la legende de chaque colonne
				System.out.println("       HOTEL                      CITY             BED(S)   ID ROOM  PRICE(EUR)   PHONE NUMBER        WEBSITE\n");
				//Pour mettre en forme le tableau on recupere chaque hotel en decoupant la chaine
				for(int i=0; i<nb_room;i++) {
					for(int y=0;y<10;y++) {
						//On choisit les valeurs que l'on veut afficher
						int [] size = {0, 2, 4, 5, 6, 3, 1};
						//On parcours le tableau d'hotel a hotel
						int indice = y + (i*10);
						//Si on tombe sur le nom de l'hotel on affiche tout le reste
						if(y == 1) {
							for(int z = 0; z < 5; z++) {
								int lenght;
								//Pour avoir un tableau avec une forme qui se tient on regarde la longueur de la chaine et on ajoute des espaces pour completer
								int [] lenghtTab = { 5, 20 };
								if(z == 2 || z == 3 || z == 4 || z == 5) {
									lenght = lenghtTab[0];
								}
								else {
									lenght = lenghtTab[1];
								}
								if(datas[indice+size[z]].length() < lenght) {
									int add = lenght - datas[indice+size[z]].length();
										for(int t = 0 ; t < add; t++) {
											datas[indice+size[z]] = datas[indice+size[z]]+" ";
											}
										}
								}
							
							String line = "| "+datas[indice]+"  |  "+datas[indice+2]+"  |   "+datas[indice+4]+"|   "+datas[indice+5]+" |   "+datas[indice+6]+"  |  "+datas[indice+3]+"  |  "+datas[indice+1]+" |";
							String separation = "";
							for(int s=0; s<line.length();s++) {
								separation = separation + "~";
							}
							//On ajoute des separations entre chaque lignes
							System.out.println(separation);
							System.out.println(line);
							if(i == nb_room-1) {
								System.out.println(separation);
							}
							
						}
					}
				}
			}
			//On termine l'affichage des resultats, on informe le client de comment reserver une chambre
			System.out.println("\n######################################");
			System.out.println("\nYou can reserve a room by using it ID ROOM in '2) Make a reservation' Menu.");
			
		} catch (RemoteException e) {
			System.out.println("Connection error to Reservation Webservice");	
		}
	}
	
	//Methode qui teste si le numero de la chambre saisi est un entier
	private static boolean room_idChecking(String room_id) {
		boolean response = false;
		
		try {
			//On test si c'est un entier
			int id = Integer.parseInt(room_id);
			response = true;
		} catch (NumberFormatException e) {
			System.out.println("Room Id must be an number");	
			response = false;
		}
		return response;
	}
	//Methode qui teste si la vile saisie est une chaine de caracteres
	private static boolean locationChecking(String location) {
		boolean response = false;
		//On test que la ville ne soit pas nulle avant de tester son type
		if(location != null) {
			//On teste son type en verifiant qu'il ne contient pas de chiffre
			if(location.matches(".*\\d.*")== true) {
				response = false;
				System.out.println("The location must not be null or contain a number");
			}
			else {
				response = true;
			}
		}
		return response;
	}

	//Methode qui convertit les dates au format voulu
	private static String convertionTime(String time) {
		String day = time.substring(0, 2);
		String month = time.substring(3, 5);
		String year = time.substring(6, 10);
		String newDate = year+"-"+month+"-"+day;
		
		return newDate;
	}
}
