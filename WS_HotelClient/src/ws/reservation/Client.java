package ws.reservation;

import java.io.Console;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.apache.axis2.AxisFault;

import ws.reservation.ReservationStub.MakeReservationResponse;

public class Client {
	
	public static Scanner choiceS = new Scanner(System.in);
	
	public static void main(String[] args) throws AxisFault
	{
		int choice;
		ReservationStub stub = new ReservationStub();
		Console values = System.console();
		
		//Set up connection with customer and get his id
		String customer_id = AuthentCall.makeAuthent();
		
		while(true) {
		System.out.println("\n########## MENU ##########");
		System.out.println("1) Check hostels availability");
		System.out.println("2) Make a reservation");
		
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
	
	//Method to check if date filled are correct to make a research
	private static boolean dateChecking(String start_date, String end_date) { 
	    Date start = null;
	    Date end = null;
	    boolean response = false;
	    
			try {
				//System.out.println(start_date);
				//System.out.println(end_date);
				start = new SimpleDateFormat("dd/MM/yyyy").parse(start_date);
				end = new SimpleDateFormat("dd/MM/yyyy").parse(end_date);
				if(start != null && end != null) 
				{
					int different = start.compareTo(end);
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
	//Method to check if number of place filled is positive and isn't null
	private static boolean placeChecking(String nb_place) {
		boolean response = false;
		if(nb_place == null || nb_place.equals("")){
			nb_place="null";
			response = true;
		}else{
			try {
				int count = Integer.parseInt(nb_place);  
				if(count <= 0) {
					System.out.println("Error in number of place(s) choosen it must be at least of 1");
					response = false;
				}
				else {
					response = true;
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				response = false;
				System.out.println("Number of place(s) must be an integer number");
			}
		}
		return response;
	}
	//Method to check if max price filled is positive and isn't null
	private static boolean maxPriceChecking(String max_price) {
		boolean response = false;
		int price;
		
		if(max_price == null || max_price.equals("")){
			max_price="null";
			response = true;
		}else{
			try {
				price = Integer.parseInt(max_price);
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
	
	public static void MakeReservation(ReservationStub stub, Console values, String customer_id)
	{
		ReservationStub.MakeReservation informations = new ReservationStub.MakeReservation();
		boolean verification_dates = false;
		boolean verification_room_id = false;
		
		String room_id,start,end;
		
		//makeReservation(String customer_id, String room_id, String start_date, String end_date)
		
		do {
		System.out.println("Id of the room [needed] : ");
		room_id = choiceS.nextLine();
		verification_room_id = room_idChecking(room_id);
		}while(verification_room_id == false);
		
		do {
			System.out.println("Start date (dd/mm/yyyy) [needed] : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd/mm/yyyy) [needed] : ");
			end = choiceS.nextLine();
			verification_dates = dateChecking(start, end);
		} while(verification_dates == false);
		
		start = convertionTime(start);
		end = convertionTime(end);
		
		informations.setCustomer_id(customer_id);
		informations.setRoom_id(room_id);
		informations.setStart_date(start);
		informations.setEnd_date(end);
		
		MakeReservationResponse response;
		try {
			response = stub.makeReservation(informations);
			String result = response.get_return();
			System.out.println("\n"+result);	
			
		} catch (RemoteException e) {
			System.out.println("Connection error to Reservation Webservice");	
		}
	}
	public static void listHotel(ReservationStub stub, Console values)
	{
		ReservationStub.ListHotel informations = new ReservationStub.ListHotel();
		
		boolean verification_places = false;
		boolean verification_dates = false;
		boolean verification_prices = false;
		boolean verification_location = false;
		
		String start, end, room_count,max_price, location;
		//listHotel(String max_price, String nb_place,String location,String start_date, String end_date)
		
		do {
			System.out.println("Max room price : ");
			max_price = choiceS.nextLine();
			verification_prices = maxPriceChecking(max_price);
		} while(verification_prices == false);
		
		do {
			System.out.println("Number of rooms : ");
			room_count = choiceS.nextLine();
			verification_places = placeChecking(room_count);
		} while(verification_places == false);
		
		do {
		System.out.println("Asked city : ");
		location = choiceS.nextLine();
		verification_location = locationChecking(location);
		}while(verification_location == false);

		do { //NE VEUT PAS BOUCLER
			System.out.println("Start date (dd/mm/yyyy) [needed] : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd/mm/yyyy) [needed] : ");
			end = choiceS.nextLine();
			verification_dates = dateChecking(start, end);
		} while(verification_dates == false);
		
		start = convertionTime(start);
		end = convertionTime(end);
		
		//ajout par lucas
		if(max_price == null || max_price.equals("")){
			max_price="null";
		}
		if(room_count == null || room_count.equals("")){
			room_count="null";
		}
		if(location == null || location.equals("")){
			location="null";
		}
		informations.setMax_price(max_price);
		informations.setNb_place(room_count);
		informations.setLocation(location);
		informations.setStart_date(start);
		informations.setEnd_date(end);
		
		System.out.println("\n########## START RESEARCH ##########");
		ReservationStub.ListHotelResponse response;
		try {
			response = stub.listHotel(informations);
			String result = response.get_return();
			if(result.equals("False")) {
				System.out.println("Sorry, we haven't found a room for you according to your criteria\n\nI'm sure you will find an other room !");
			}
			else {
				int nb_room = ((result.split(";")).length)/10;
				System.out.println("\nThere is "+nb_room+" room founded : \n");
				String [] datas = result.split(";");
				System.out.println("       HOTEL                      CITY             BED(S)   ID ROOM  PRICE(EUR)   PHONE NUMBER        WEBSITE\n");
				for(int i=0; i<nb_room;i++) {
					for(int y=0;y<10;y++) {
						int [] size = {0, 2, 4, 5, 6, 3, 1};
						int indice = y + (i*10);

						if(y == 1) {
							for(int z = 0; z < 5; z++) {
								int lenght;
								int [] lenghtTab = { 5, 20 };
								if(z == 2 || z == 3 || z == 4 || z == 5) {
									lenght = lenghtTab[0];
									/*if( z == 4) {
										datas[indice+size[z]] = datas[indice+size[z]]+"€";
									}*/
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
							System.out.println(separation);
							System.out.println(line);
							if(i == nb_room-1) {
								System.out.println(separation);
							}
							
						}
					}
				}
			}
			System.out.println("\n######################################");
			System.out.println("\nYou can reserve a room by using it ID ROOM in '2) Make a reservation' Menu.");
			
		} catch (RemoteException e) {
			System.out.println("Connection error to Reservation Webservice");	
		}
	}
	
	private static boolean room_idChecking(String room_id) {
		boolean response = false;
		
		try {
			int id = Integer.parseInt(room_id);
			response = true;
		} catch (NumberFormatException e) {
			System.out.println("Room Id must be an number");	
			response = false;
		}
		return response;
	}
	private static boolean locationChecking(String location) {
		boolean response = false;
		if(location != null) {
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

	//Method to convert date format asked to database format needed
	private static String convertionTime(String time) {
		String day = time.substring(0, 2);
		String month = time.substring(3, 5);
		String year = time.substring(6, 10);
		String newDate = year+"-"+month+"-"+day;
		
		//System.out.println("Old date :"+time);
		//System.out.println("New date :"+newDate);
		return newDate;
	}
}
