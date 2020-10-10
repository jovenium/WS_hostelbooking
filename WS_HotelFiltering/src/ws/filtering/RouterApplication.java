package ws.filtering;
 
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
 
public class RouterApplication extends Application{
	/**
	 * Creates a root Restlet that will receive all incoming calls.
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new respective instance of resource.
		Router router = new Router(getContext());
		// Defines only two routes
		router.attach("/filter/params:{max_price},{nb_place},{location},{start_date},{end_date}", FilterResource.class);
		router.attach("/reservation", ReservationResource.class);
		return router;
	}
}