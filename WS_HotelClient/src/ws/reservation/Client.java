package ws.reservation;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.Scanner;

import org.apache.axis2.AxisFault;

import ws.reservation.ReservationStub.MakeReservationResponse;

public class Client {
	
	public static void main(String[] args) throws AxisFault
	{
		ReservationStub stub = new ReservationStub();
		Console values = System.console();
		
		//Set up connection with customer and get his id
		String customer_id = "0000";
		
		System.out.println("1) Check hostels availability");
		System.out.println("2) Make a reservation");
		
		Scanner choiceS = new Scanner(System.in);
		//String choice = values.readLine("Choice : ");
		int choice = Integer.parseInt(choiceS.nextLine());
		
		if(choice == 1) {
			System.out.println("You have chosen : Check hostels availability");
		}
		else if(choice == 2) {
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
		Scanner datas = new Scanner(System.in);
		
		//makeReservation(String customer_id, String room_id, String start_date, String end_date)
		System.out.println("Id of the room : ");
		String room_id = datas.nextLine();
		System.out.println("Start date (dd-mm-yyyy) : ");
		String start = datas.nextLine();
		System.out.println("End date (dd-mm-yyyy) : ");
		String end = datas.nextLine();
			
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
		Scanner datas = new Scanner(System.in);
		
		//listHotel(String max_price, String nb_place,String location,String start_date, String end_date)
		
		System.out.println("Max room price : ");
		String max_price = datas.nextLine();
		System.out.println("Number of rooms : ");
		String room_count = datas.nextLine();
		System.out.println("Asked city : ");
		String location = datas.nextLine();
		System.out.println("Start date (dd-mm-yyyy) : ");
		String start = datas.nextLine();
		System.out.println("End date (dd-mm-yyyy) : ");
		String end = datas.nextLine();
		
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
}
