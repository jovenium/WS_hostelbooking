package filterinteraction;

import java.io.BufferedReader;
import java.io.IOException;



import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
 
public class ClientCall {
 
	

	public static String reservationQuery(String customer_id, String room_id, String start_date, String end_date) {
		try{
		URL url = new URL("http://127.0.0.1:8081/WS_HotelFiltering/reservation");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(customer_id.getBytes());
		os.flush();
		os.write(room_id.getBytes());
		os.flush();
		os.write(start_date.getBytes());
		os.flush();
		os.write(end_date.getBytes());
		
		os.flush();
		os.close();
		
		int responseCode;
		responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} else {
			System.out.println("POST request not worked");
		}
		
		}catch( Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	public static String filterQuery(String max_price, String nb_place,
			String location, String start_date, String end_date) {
		System.out.println("yo");
		String url = "http://127.0.0.1:8081/WS_HotelFiltering/filter/params:"
				+ max_price +","
						+ nb_place +","
						+ location +","
						+ start_date +","
						+ end_date;
		System.out.println(url);
		
		
		
		try {
			URL url2 = new URL(url);
			HttpURLConnection con = (HttpURLConnection) url2.openConnection();
			con.setRequestMethod("GET");
			int responseCode;
			responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			
			
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				System.out.println("GET request not worked");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
		}
		System.out.println("end call");
		
		return null;
	}
	
	
 
}