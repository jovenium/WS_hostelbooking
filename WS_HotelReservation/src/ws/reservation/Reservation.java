package ws.reservation;


import filterinteraction.ClientCall;

/**
 * WS_HotelReservation sous Axis2
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class Reservation {
	/**
	 * Retourne la liste des chambres correspondants aux parametres de la requete
	 * @route /listHotel
	 * @method GET
	 * @param max_price
	 * @param nb_place
	 * @param location
	 * @param start_date
	 * @param end_date
	 * @return String concatenation des hotels et des chambres
	 */
	public String listHotel(String max_price, String nb_place,String location,String start_date, String end_date){
		//On verifie les entrees
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
		//On appelle filter WS et on recupere la reponse contenant la liste des chambres d'hotel
		String response = ClientCall.filterQuery(max_price, nb_place, location, start_date, end_date);
		
		//On envoie la reponse au client
		return response;
	}
	
	/**
	 * @route /makeReservation
	 * @method POST
	 * @param customer_id
	 * @param room_id
	 * @param start_date
	 * @param end_date
	 * @return vrai ou faux/ reservation reussi ou non
	 */
	public String makeReservation(String customer_id, String room_id, String start_date, String end_date){
		//On verifie la conformite des entrees
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
		
		//On appelle le filtre WS
		String response = ClientCall.reservationQuery(customer_id_param,
													room_id_param,
													start_param,
													end_param);
		
		//Si filter WS nous repond vrai on envoi la reponse positive au client
		if( response.equals("True")){
			response = "Reservation successful for the room "+room_id;
		//Si filter WS nous repond faux on envoi la reponse negative au client
		}else{
			response = "Reservation error, the room "+ room_id +" is not available";
		}
		//On envoie la reponse formulee
		return response;
	}
}
