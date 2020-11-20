package ws.reservation;

import java.util.regex.Pattern;

import org.restlet.representation.Representation;

import filterinteraction.ClientCall;


public class Reservation {
	public String listHotel(String max_price, String nb_place,String location,String start_date, String end_date){
		//check inputs
		System.out.println("listHOTEL");
		if(max_price==null){
			max_price="null";
		}
		if(nb_place==null){
			nb_place="null";
		}
		if(location==null){
			location = "null";
		}
		if(start_date==null){
			start_date="null";
			end_date="null";
		}
		//call filter ws
		String response = ClientCall.filterQuery(max_price, nb_place, location, start_date, end_date);
		//format response
		
		//return
		return response;
	}
	
	public String makeReservation(String customer_id, String room_id, String start_date, String end_date){
		//check inputs
		if(customer_id == null){
			return "User problem.";
		}
		if(room_id == null){
			return "Room problem.";
		}
		if( start_date == null	|| end_date == null){
			return "Date problem.";
		}
		
		String customer_id_param = "customer_id=" + customer_id;
		String room_id_param = "&room_id=" + room_id;
		String start_param = "&start_date=" + start_date;
		String end_param = "&end_date=" + end_date;
		
		//call filter WS
		String response = ClientCall.reservationQuery(customer_id_param,
													room_id_param,
													start_param,
													end_param);
		
		//reformate response
		if( response.equals("True")){
			response = "Reservation successful for the room "+room_id;
		}else{
			response = "Reservation error, the room "+ room_id +" is not available";
		}
		//resturn response
		return response;
	}
}
