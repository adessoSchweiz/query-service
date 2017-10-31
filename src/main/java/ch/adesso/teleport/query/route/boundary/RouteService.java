package ch.adesso.teleport.query.route.boundary;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.adesso.teleport.query.route.entity.Route;
import ch.adesso.teleport.query.route.entity.RouteStatus;

@Stateless
public class RouteService {

	private static final Logger LOG = Logger.getLogger(RouteService.class.getName());

	@PersistenceContext
	EntityManager em;

	public void createRoute(Route route) {
		em.merge(route);
	}

	public void updateStatus(String routeId, long version, RouteStatus status) {
		Route route = em.find(Route.class, routeId);
		route.setVersion(version);
		route.setStatus(status);
		em.merge(route);
	}

}
