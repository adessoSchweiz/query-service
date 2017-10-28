package ch.adesso.teleport.query.route.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RouteService {

	@PersistenceContext
	EntityManager em;

	public void createRoute() {

	}

	public void updateStatus() {

	}
}
