package ws.filtering;
 
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
 
/**
 * WS_HotelFiltering sous Restlet
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class RouterApplication extends Application{
	/**
	 * creations des routes
	 */
	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		// /filter/params GET
		router.attach("/filter/params:{max_price},{nb_place},{location},{start_date},{end_date}", FilterResource.class);
		// /reservation POST
		router.attach("/reservation", ReservationResource.class);
		return router;
	}
}