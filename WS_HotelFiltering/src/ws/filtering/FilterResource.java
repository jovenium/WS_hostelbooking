package ws.filtering;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
 
public class FilterResource extends ServerResource {  	

	
	@Get
    public Representation acceptItem(Representation entity) {  
		Representation result = null;  
        // Parse the given representation and retrieve data
        Form form = new Form(entity);  
//        String start_date = form.getFirstValue("start_date");  
//        String end_date = form.getFirstValue("end_date");  
//        String location = form.getFirstValue("location");  
//        Integer nb_place = Integer.parseInt(form.getFirstValue("nb_place")); 
//        Integer max_price = Integer.parseInt(form.getFirstValue("nb_place")); 
 
        //findHotelsAndRooms()
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	String url = "jdbc:mysql://localhost:3306/DB_HOTEL?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection co = DriverManager.getConnection(url,"root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new StringRepresentation("erreur connexion",  
		            MediaType.TEXT_PLAIN);
		}
        
        
        result = new StringRepresentation("connexion OK",  
            MediaType.TEXT_PLAIN);
      
        return result;  
    } 
}  