package ws.filtering;
 
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
 
public class ReservationResource extends ServerResource {  
	@Get  
	public String toString() {  
		String uid = (String) getRequestAttributes().get("uid");
		return "The items that user \"" + uid + "\" bought are: <nothing>";  
	}  
} 