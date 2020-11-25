package ws.authent;
 
import java.sql.Statement;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import service.DatabaseService;
import service.AuthentRepository;
 
/**
 * 
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class ConnexionResource extends ServerResource {  
	/**
	 * @route /connexion 
	 * @method POST
	 * @param entity
	 * @return
	 */
	@Post
    public Representation acceptItem(Representation entity) {   
		//recuperation des informations
        Form form = new Form(entity);  
        String name = form.getFirstValue("name");  
        String pwd = form.getFirstValue("pwd");
        
        //check des informations
        if(name == null || pwd == null){
        	return new StringRepresentation("False", MediaType.TEXT_PLAIN);
        }
        
        //connexion a la DB
        Statement t = DatabaseService.getStatement();
        //check si connexion OK
        if(t == null){
        	return new StringRepresentation("erreur connexion bd",  
		            MediaType.TEXT_PLAIN);
        }
        //interrogation de la DB pour authentifier le user
        String s_result = AuthentRepository.findIdByNameAndPWD(t, name, pwd);
        System.out.println("id user :" + s_result);
        //formatage et verification de la reponse
        String response = reformateResultQuery(s_result);
        
        //retour de la reponse a l'utilisateur
        return new StringRepresentation(response,  
	            MediaType.TEXT_PLAIN); 
    }

	/**
	 * Formatage de la reponse et gestion des erreurs
	 * @param rs
	 * @return id ou erreur de connexion
	 */
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