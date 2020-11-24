package ws.filtering;
 
import java.sql.Statement;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import service.DatabaseService;
import service.FilterRepository;
 
/**
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class ReservationResource extends ServerResource {  
	/**
	 * recuperation des informations pour faire une reservation (et remplacer l'ancienne si besoin)
	 * @param entity
	 * @return
	 */
	@Post
    public Representation acceptItem(Representation entity) {  
		//recuperation des parametres
		Representation result = null;  
        Form form = new Form(entity);  
        String customer_id = form.getFirstValue("customer_id");  
        String room_id = form.getFirstValue("room_id");
        String start_date = form.getFirstValue("start_date");
        String end_date = form.getFirstValue("end_date");
        System.out.println(customer_id +","+room_id+","+start_date+","+end_date);
        //verification des parametres
        if( customer_id == null || room_id == null || start_date == null || end_date == null ){
        	return new StringRepresentation("Probleme de parametres.", MediaType.TEXT_PLAIN);
        }
        
        //recuperation de la connexion a la DB
        Statement t = DatabaseService.getStatement();
        if(t == null){
        	return new StringRepresentation("erreur connexion",  
		            MediaType.TEXT_PLAIN);
        }
        //requete de reservation en DB
        boolean resultbool = FilterRepository.addReservation(t, customer_id,room_id,start_date,end_date);
        //reformatage de la reponse en True/False
        String response = reformateResultQuery(resultbool);
        
        //reponse
        return new StringRepresentation(response,  
	            MediaType.TEXT_PLAIN); 
    }

	//formatage de la reponse apres interrogation en DB
	private String reformateResultQuery(boolean rs) {
		String response = "";
		if(rs) {
			response = "True";
		}else{
			response = "False";
		}
		return response;
	}  
} 