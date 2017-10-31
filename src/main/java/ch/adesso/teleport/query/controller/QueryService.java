package ch.adesso.teleport.query.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.adesso.teleport.query.passenger.entity.Passenger;
import ch.adesso.teleport.query.person.entity.Person;
import ch.adesso.teleport.query.route.entity.Route;

@Stateless
public class QueryService {

	@PersistenceContext
	EntityManager em;

	public List<Person> getPersons() {
		List<Person> persons = em.createNamedQuery("Person.findAllPersons", Person.class).getResultList();

		persons.forEach(p -> {
			Passenger passenger = (Passenger) em.createNamedQuery("Passenger.findPassengerByPartyId")
					.setParameter("partyId", p.getId()).getSingleResult();
			p.addPartyRole(passenger);
		});
		return persons;

	}

	public List<Route> getRouteRequests() {
		return em.createNamedQuery("Route.findAllRoutes", Route.class).getResultList();

	}

}
