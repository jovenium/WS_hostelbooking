package ws.reservation;

import java.io.Console;

import org.apache.axis2.AxisFault;

public class Client {
	
	public static void main(String[] args) throws AxisFault
	{
		ReservationStub stub = new ReservationStub();
		Console values = System.console();
		
		//Set up connection with customer and get his id
		String customer_id = "0000";
		
		System.out.println("1) Check hostels availability");
		System.out.println("2) Make a reservation");
		String choice = values.readLine("Choice : ");
		
		if(choice == "1") {
			System.out.println("You have chosen : Check hostels availability");
		}
		else if(choice == "2") {
			System.out.println("You have chosen : Make a reservation");
			MakeReservation(stub, values, customer_id);
		}
		else {
			System.out.println("No choice selected");
		}
	}
	public static void MakeReservation(ReservationStub stub, Console values, String customer_id)
	{
		ReservationStub.MakeReservation informations = new ReservationStub.MakeReservation();
		
		//makeReservation(String customer_id, String room_id, String start_date, String end_date)
		String room_id = values.readLine("Id of the room : ");
		String start = values.readLine("Start date (dd-mm-yyyy) : ");
		String end = values.readLine("End date (dd-mm-yyyy) : ");
		
		informations.customer_id(customer_id);
		informations.room_id(room_id);
		informations.start_date(start);
		informations.end_date(end);
		
		ReservationStub.MakeReservationResponse response = stub.makeReservation(informations);
		
		String result = response.toString();
		System.out.println(result);	
	}
	public static void listHotel(ReservationStub stub, Console values, String customer_id)
	{
		ReservationStub.ListHotel informations = new ReservationStub.ListHotel();
		//listHotel(String max_price, String nb_place,String location,String start_date, String end_date)
		
		String max_price = values.readLine("Max room price : ");
		String room_count = values.readLine("Number of rooms : ");
		String location = values.readLine("Asked city : ");
		String start = values.readLine("Start date (dd-mm-yyyy) : ");
		String end = values.readLine("End date (dd-mm-yyyy) : ");
		
		informations.customer_id(customer_id);
		informations.max_price(max_price);
		informations.nb_place(room_count);
		informations.location(location);
		informations.start_date(start);
		informations.end_date(end);
		
		ReservationStub.ListHotelResponse response = stub.listHotel(informations);
		
		String result = response.toString();
		System.out.println(result);	
	}
}
