package ws.reservation;

import java.io.Console;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.axis2.AxisFault;

import ws.reservation.ReservationStub.MakeReservationResponse;

public class Client {
	
	private static Scanner choiceS = new Scanner(System.in);
	
	public static void main(String[] args) throws AxisFault
	{
		ReservationStub stub = new ReservationStub();
		Console values = System.console();
		
		//Set up connection with customer and get his id
		String customer_id = "0000";
		
		while(true) {
		System.out.println("1) Check hostels availability");
		System.out.println("2) Make a reservation");
		
		//String choice = values.readLine("Choice : ");
		int choice = Integer.parseInt(choiceS.nextLine());
		
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
	}
	
	//Method to check if date filled are correct to make a research
	private static boolean dateChecking(String start_date, String end_date) { 
	    Date start = null;
	    Date end = null;
	    boolean response = false;
	    
		try {
			start = new SimpleDateFormat("dd-MM-yyyy").parse(start_date);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		try {
			end = new SimpleDateFormat("dd-MM-yyyy").parse(end_date);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}  
		
		//START ET END_DATE ?????????????
		if(start != null && end_date != null) 
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
			System.out.println("Dates can't be null");
			response = false;
		}
		return response;
	}
	//Method to check if number of place filled is positive and isn't null
	private static boolean placeChecking(String nb_place) {
		boolean response = false;
		
		int count = Integer.parseInt(nb_place);  
		if(count <= 0) {
			System.out.println("Error in number of place(s) choosen it must be at least of 1");
			response = false;
		}
		else {
			response = true;
		}
		return response;
	}
	//Method to check if max price filled is positive and isn't null
	private static boolean maxPriceChecking(String max_price) {
		boolean response = false;
		
		int price = Integer.parseInt(max_price);  
		if(price <= 0) {
			System.out.println("Maximum price can't be negative of null");
			response = false;
		}
		else {
			response = true;
		}
		return response;
	}
	
	public static void MakeReservation(ReservationStub stub, Console values, String customer_id)
	{
		ReservationStub.MakeReservation informations = new ReservationStub.MakeReservation();
		boolean verification_dates = false;
		
		String room_id,start,end;
		
		//makeReservation(String customer_id, String room_id, String start_date, String end_date)
		
		do {
		System.out.println("Id of the room : ");
		room_id = choiceS.nextLine();
		if(room_id == null) {
			System.out.println("Room id not be null");
		}
		}while(room_id == null);
		
		do { //NE VEUT PAS BOUCLER SANS AUCUNE RAISON
			System.out.println("Start date (dd-mm-yyyy) : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd-mm-yyyy) : ");
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
			String result = response.toString();
			System.out.println(result);	
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void listHotel(ReservationStub stub, Console values)
	{
		ReservationStub.ListHotel informations = new ReservationStub.ListHotel();
		
		boolean verification_places = false;
		boolean verification_dates = false;
		boolean verification_prices = false;
		
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
		if(location == null) {
			System.out.println("Location not be null");
		}
		}while(location == null);

		do { //NE VEUT PAS BOUCLER
			System.out.println("Start date (dd/mm/yyyy) : ");
			start = choiceS.nextLine();
			System.out.println("End date (dd/mm/yyyy) : ");
			end = choiceS.nextLine();
			System.out.println("test");
			verification_dates = dateChecking(start, end);
		} while(verification_dates == false);
		
		start = convertionTime(start);
		end = convertionTime(end);
		
		informations.setMax_price(max_price);
		informations.setNb_place(room_count);
		informations.setLocation(location);
		informations.setStart_date(start);
		informations.setEnd_date(end);
		
		ReservationStub.ListHotelResponse response;
		try {
			response = stub.listHotel(informations);
			String result = response.toString();
			System.out.println(result);	
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
