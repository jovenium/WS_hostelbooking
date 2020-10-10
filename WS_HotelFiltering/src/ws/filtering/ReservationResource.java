package ws.filtering;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import service.DatabaseService;
import service.FilterRepository;
 
public class ReservationResource extends ServerResource {  
	@Post
    public Representation acceptItem(Representation entity) {  
		Representation result = null;  
        Form form = new Form(entity);  
        String customer_id = form.getFirstValue("customer_id");  
        String room_id = form.getFirstValue("room_id");
        String start_date = form.getFirstValue("start_date");
        String end_date = form.getFirstValue("end_date");
        System.out.println(customer_id +","+room_id+","+start_date+","+end_date);
        if( customer_id == null || room_id == null || start_date == null || end_date == null ){
        	return new StringRepresentation("Probleme de parametres.", MediaType.TEXT_PLAIN);
        }
        
 
        Statement t = DatabaseService.getStatement();
        if(t == null){
        	return new StringRepresentation("erreur connexion",  
		            MediaType.TEXT_PLAIN);
        }
        boolean resultbool = FilterRepository.addReservation(t, customer_id,room_id,start_date,end_date);
        String response = reformateResultQuery(resultbool);
 
        return new StringRepresentation(response,  
	            MediaType.TEXT_PLAIN); 
    }

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