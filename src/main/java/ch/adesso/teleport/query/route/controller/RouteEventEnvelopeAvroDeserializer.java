package ch.adesso.teleport.query.route.controller;

import ch.adesso.kafka.KafkaAvroReflectDeserializer;
import ch.adesso.teleport.query.route.event.RouteEventEnvelope;

public class RouteEventEnvelopeAvroDeserializer extends KafkaAvroReflectDeserializer<RouteEventEnvelope> {
	public RouteEventEnvelopeAvroDeserializer() {
		super(RouteEventEnvelope.class);
	}
}
