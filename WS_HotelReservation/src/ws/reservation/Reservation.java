package ws.reservation;

import java.text.ParseException;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.Server;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation extends ServerResource {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Create the HTTP server and listen on port 8182  
		new Server(Protocol.HTTP, 8182, Reservation.class).start();
	}
 
	private String dateChecking(String start_date, String end_date) { 
	    Date start = null;
	    Date end = null;
	    String response = "Dates sont OK";
		try {
			start = new SimpleDateFormat("dd-MM-yyyy").parse(start_date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			end = new SimpleDateFormat("dd-MM-yyyy").parse(end_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		if(start != null && end != null)
		{
		int different = start.compareTo(end);
		if(different > 0 ) {
			response = "Erreur de saisie de dates";
		}
		}
		return response;
	}
	private String placeChecking(String nb_place) {
		String response = "Nombre de place OK";
		
		int count = Integer.parseInt(nb_place);  
		if(count <= 0) {
			response = "Erreur de saisie de place";
		}
		return response;
	}
	
	@Get
	public String reservation(String start_date, String end_date, String location, String nb_place, String max_price ){
		String testDate = dateChecking(start_date, end_date);
		String testPlace = placeChecking(nb_place);
		
		return "Date de début : "+start_date+" | Date de fin : "+ end_date+" | Ville : "+location+" | Nombre de personnes : "+nb_place+" | Prix : "+max_price+" | "+testDate+" | "+testPlace;
	}	//http://localhost:8080/WS_HotelReservation/services/Reservation/reservation?start_date=10-11-2020%20&end_date=12-11-2020&location=paris&nb_place=5&max_price=500
		
	//TODO PUSH DATAS TO FILTERING
	//TODO GET DATA FROM CLIENT
	//TODO GET RESPONSE FROM FILTERING
}
