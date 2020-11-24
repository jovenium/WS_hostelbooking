package ws.filtering;
 

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import service.DatabaseService;
import service.FilterRepository;
 
/**
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class FilterResource extends ServerResource {  	

	/**
	 * @route /filter/params
	 * @return 
	 */
	@Get
    public Representation filter() {         
		//recuperation des parametres
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("start_date", (String) getRequestAttributes().get("start_date"));  
        hm.put("end_date", (String) getRequestAttributes().get("end_date"));  
        hm.put("location", (String) getRequestAttributes().get("location"));  
        hm.put("nb_place", (String) getRequestAttributes().get("nb_place")); 
        hm.put("max_price", (String) getRequestAttributes().get("max_price")); 
        System.out.println((String) getRequestAttributes().get("max_price"));
 
        //connexion a la DB
        Statement t = DatabaseService.getStatement();
        if(t == null){
        	return new StringRepresentation("erreur connexion",  
		            MediaType.TEXT_PLAIN);
        }
        //requete pour recuperer les chambres disponibles
        ResultSet resultquery = FilterRepository.findHotelRoomsbyParameters(t, hm);
        //formatage de la reponse
        String response = reformateResultQuery(resultquery);
        
        //envoie de la reponse
        return new StringRepresentation(response,  
            MediaType.TEXT_PLAIN);
        
    }

	/**
	 * Reformate le retour fait par la bdd, formatage avec des ";" et END a la fin d'une chambre
	 * @param rs
	 * @return
	 */
	private String reformateResultQuery(ResultSet rs) {
		String response = "";
		System.out.println("resp : " + rs);
		try {
			if(rs != null && rs.next()) { // Checks for any results and moves cursor to first row,
			    do { 
			    	for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
						response += rs.getString(i) +";";
					}
					response += "END;";
			    } while (rs.next());
			}else{
				response = "False";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	} 
}  