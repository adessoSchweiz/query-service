package ch.adesso.queryservice.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.adesso.partyservice.party.entity.Person;
import ch.adesso.queryservice.controller.QueryService;
import ch.adesso.routeservice.route.entity.RouteRequest;

@Path("query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class QueryResource {

	@Inject
	QueryService queryService;

	@GET
	@Path("/persons")
	public Person[] getAllPersons() {
		return queryService.getPersons();
	}

	@GET
	@Path("/routes")
	public RouteRequest[] getAllRouteRequests() {
		return queryService.getRouteRequests();
	}

}
