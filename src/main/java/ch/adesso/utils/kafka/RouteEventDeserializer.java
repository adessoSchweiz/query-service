package ch.adesso.utils.kafka;

import ch.adesso.routeservice.route.entity.RouteRequest;

public class RouteEventDeserializer extends KafkaAvroReflectDeserializer<RouteRequest> {
	public RouteEventDeserializer() {
		super(RouteRequest.class);
	}

}
