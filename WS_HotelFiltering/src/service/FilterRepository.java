package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FilterRepository {

	public static ResultSet findHotelRoomsbyParameters(Statement stmt, HashMap<String,String> parameters) {
		ResultSet result;
		String query = "SELECT DISTINCT h.id, h.name, h.website, h.location, h.phone_number, h.stars, r.id, r.price, r.nb_place FROM hotel h, room r, reservation re where h.id = r.hotel_id AND r.id = re.room_id";
		query = hydrateFilterQuery(query, parameters);
		//todo suppr print
		System.out.println(query);
		try {
			result = stmt.executeQuery(query);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String hydrateFilterQuery(String query,
			HashMap<String, String> hm) {
		String t = hm.get("start_date");
		String e = hm.get("end_date");
		if( !t.equals("null") && !e.equals("null") ){
			query = query + " AND "
					+ "((re.start_date < '"+ hm.get("start_date") +"' AND re.end_date < '"+ hm.get("start_date") +"' ) "
					+ "OR ("
					+ "re.start_date > '"+ hm.get("end_date") +"' AND re.end_date > '"+ hm.get("end_date") +"' ))";
		}
		if(!hm.get("location").equals("null")){
			query = query + " AND h.location LIKE '%"+ hm.get("location") +"%'";
		}
		if(!hm.get("nb_place").equals("null")){
			query = query + " AND r.nb_place = '"+ hm.get("nb_place") +"'";
		}
		if(!hm.get("max_price").equals("null")){
			query = query + " AND r.price <= '"+ hm.get("max_price") +"'";
		}
		return query;
	}

	public static boolean addReservation(Statement t, String customer_id, String room_id, String start_date, String end_date) {
		int result;
		try{
			if(canReserveThisRoom(t,room_id,start_date,end_date)){
				ResultSet hisReserve = findHisReservations(t,customer_id,start_date,end_date);
				System.out.println("la");
				ArrayList<Integer> ids_to_delete = new ArrayList<Integer>();
				while(hisReserve.next()){
						System.out.println("la1");
						ids_to_delete.add(hisReserve.getInt(1));
				}
				for(Integer i : ids_to_delete){
					deleteThisReservation(t,i);
				}
				String query = "INSERT INTO reservation(start_date, end_date, room_id, customer_id) VALUES ('"+start_date+"', '"+end_date+"', "+room_id+", "+customer_id+");";
				//todo suppr print
				System.out.println(query);
				try {
					result = t.executeUpdate(query);
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static void deleteThisReservation(Statement t, int id_re) {
		int result=0;
		try {
			result = t.executeUpdate("DELETE FROM reservation WHERE id=" + id_re);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private static ResultSet findHisReservations(Statement t, String customer_id, String start_date, String end_date) {
		ResultSet result=null;
		try {
			result = t.executeQuery("SELECT re.id from reservation re, customer c WHERE re.customer_id = c.id AND c.id='"+ customer_id +"' AND ((re.start_date <= '"+ start_date +"' AND re.end_date >= '"+ start_date +"' ) "
					+ "OR ("
					+ "re.start_date <= '"+ end_date +"' AND re.end_date >= '"+ end_date +"' ))");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static boolean canReserveThisRoom(Statement t, String room_id, String start_date, String end_date) {
		try {
			ResultSet result = t.executeQuery("SELECT 1 from reservation re, room r WHERE r.id = re.room_id AND r.id = '"+ room_id +"' AND ((re.start_date <= '"+ start_date +"' AND re.end_date >= '"+ start_date +"' ) "
					+ "OR ("
					+ "re.start_date <= '"+ end_date +"' AND re.end_date >= '"+ end_date +"' ))");
			if(result.next()){
				if(result.getInt(1)==1){
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
