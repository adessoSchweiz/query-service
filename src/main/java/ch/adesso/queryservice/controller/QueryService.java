package ch.adesso.queryservice.controller;

import java.util.List;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import avro.shaded.com.google.common.collect.Lists;
import ch.adesso.partyservice.party.entity.Person;
import ch.adesso.partyservice.party.event.CoreEvent;
import ch.adesso.routeservice.route.entity.RouteRequest;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class QueryService {

	@PersistenceContext
	EntityManager entityManager;

	public Person[] getPersons() {
		List<Person> persons = Lists.newArrayList();
		entityManager.createNamedQuery("findAllPersons", Person.class).getResultList().forEach(p -> {
			// fetch data to prevent LazyInitialization Issues
			p.getPartyRoles().forEach(pp -> pp.getId());
			p.getContacts().forEach(c -> c.getAddress().getAddressId());
			persons.add(p);
		});

		return persons.toArray(new Person[persons.size()]);

	}

	public RouteRequest[] getRouteRequests() {
		List<RouteRequest> routeRequests = Lists.newArrayList();
		entityManager.createNamedQuery("findAllRouteOrders", RouteRequest.class).getResultList()
				.forEach(p -> routeRequests.add(p));

		return routeRequests.toArray(new RouteRequest[routeRequests.size()]);

	}

	public void update(@Observes RouteRequest route) {
		System.out.println("Got Event: " + route);
		entityManager.merge(route);
	}

	public void update(@Observes CoreEvent event) {
		System.out.println("Got Event: " + event);
		Person person = entityManager.find(Person.class, event.getAggregateId());
		if (person == null) {
			person = new Person(event.getAggregateId());
		}
		person.applyEvent(event);
		entityManager.merge(person);
	}
}
