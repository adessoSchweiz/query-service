package ch.adesso.teleport.query.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import avro.shaded.com.google.common.collect.Lists;
import ch.adesso.teleport.query.person.entity.Person;
import ch.adesso.teleport.query.route.entity.Route;

@Stateless
public class QueryService {

	@PersistenceContext
	EntityManager em;

	public Person[] getPersons() {
		List<Person> persons = em.createNamedQuery("findAllPersons", Person.class).getResultList();

		persons.forEach(p -> p.getPartyRoles());
		return persons.toArray(new Person[persons.size()]);

	}

	public Route[] getRouteRequests() {
		List<Route> routeRequests = Lists.newArrayList();

		return routeRequests.toArray(new Route[routeRequests.size()]);

	}

}
