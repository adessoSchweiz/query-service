package ch.adesso.teleport.query.controller;

import java.util.List;

import javax.ejb.Stateless;

import avro.shaded.com.google.common.collect.Lists;
import ch.adesso.teleport.query.person.entity.Person;
import ch.adesso.teleport.query.route.entity.Route;

@Stateless
public class QueryService {

	public Person[] getPersons() {
		List<Person> persons = Lists.newArrayList();

		return persons.toArray(new Person[persons.size()]);

	}

	public Route[] getRouteRequests() {
		List<Route> routeRequests = Lists.newArrayList();

		return routeRequests.toArray(new Route[routeRequests.size()]);

	}

}
