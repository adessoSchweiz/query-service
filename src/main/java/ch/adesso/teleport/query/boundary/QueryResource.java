package ch.adesso.teleport.query.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.adesso.teleport.query.controller.QueryService;
import ch.adesso.teleport.query.person.entity.Person;
import ch.adesso.teleport.query.route.entity.Route;

@Path("query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class QueryResource {

	@Inject
	QueryService queryService;

	@GET
	@Path("/persons")
	public List<Person> getAllPersons() {
		return queryService.getPersons();

	}

	@GET
	@Path("/routes")
	public List<Route> getAllRouteRequests() {
		return queryService.getRouteRequests();
	}

}
