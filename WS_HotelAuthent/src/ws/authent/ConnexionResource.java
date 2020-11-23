package ws.authent;
 
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
import service.AuthentRepository;
 
public class ConnexionResource extends ServerResource {  
	@Post
    public Representation acceptItem(Representation entity) {    
        Form form = new Form(entity);  
        String name = form.getFirstValue("name");  
        String pwd = form.getFirstValue("pwd");
        System.out.println(name +","+pwd);
        if(name == null || pwd == null){
        	return new StringRepresentation("False", MediaType.TEXT_PLAIN);
        }
        
 
        Statement t = DatabaseService.getStatement();
        if(t == null){
        	return new StringRepresentation("erreur connexion bd",  
		            MediaType.TEXT_PLAIN);
        }
        System.out.println("findIdByNameAndPWD");
        String s_result = AuthentRepository.findIdByNameAndPWD(t, name, pwd);
        System.out.println("id user :" + s_result);
        String response = reformateResultQuery(s_result);
 
        return new StringRepresentation(response,  
	            MediaType.TEXT_PLAIN); 
    }

	private String reformateResultQuery(String rs) {
		String response = "";
		if(rs.equals("")) {
			response = "False";
		}else{
			response = rs;
		}
		return response;
	}  
} 