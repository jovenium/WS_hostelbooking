package ws.authent;
 
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
 
/**
 * 
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class RouterApplication extends Application{
	/**
	 * declaration des routes restlet
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// route connexion, POST, name, pwd
		// permet d'authent un utilisateur
		router.attach("/connexion", ConnexionResource.class);
		return router;
	}
}