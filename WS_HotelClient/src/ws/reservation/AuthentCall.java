package ws.reservation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthentCall {

	public static String makeAuthent(){
		System.out.println("########## LOGIN ##########");
		String name = "";
		String pwd = "";
		String id = "";
		do{
			id = "";
			name = "";
			pwd = "";
			System.out.print("Name : ");
			name = Client.choiceS.nextLine();
			System.out.print("Password : ");
			pwd = Client.choiceS.nextLine();
			if(isValidInput(name,pwd)){
				//faire requete
				id = callAuthent(name,pwd);
				//System.out.println("id authent:" + id);
				if(!isValidId(id)){
					System.out.println("Wrong Name or Password, please try again.");
				}else{
					System.out.println("Hey "+name+", welcome back !\n");
				}
			}
			
		}while(!isValidId(id));
		
		return id;
	}

	private static String callAuthent(String name, String pwd) {
		try{
			URL url = new URL("http://127.0.0.1:8081/WS_HotelAuthent/connexion");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			
			String name_param="name="+name;
			String pwd_param="&pwd="+pwd;
			
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(name_param.getBytes());
			os.flush();
			os.write(pwd_param.getBytes());
			os.flush();
			os.close();
			
			int responseCode;
			responseCode = con.getResponseCode();
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
				System.out.println("PROBLEME WEB SERVICE AUTHENT");
				return "False";
			}
			
			}catch( Exception e){
				e.printStackTrace();
			}
		return "False";
	}

	private static boolean isValidId(String id) {
		if(id.length()>=1 && !id.equals("False") ){
			return true;
		}
		return false;
	}

	private static boolean isValidInput(String name, String pwd) {
		boolean result = true;
		if(name == null || name.equals("")){
			System.out.println("Wrong Name.");
			result = false;
		}
		if(pwd == null || pwd.equals("")){
			System.out.println("Wrong Password. Please try again.");
			result = false;
		}
		return result;
	}
}
