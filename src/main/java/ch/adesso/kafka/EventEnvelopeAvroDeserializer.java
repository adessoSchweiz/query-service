package ch.adesso.kafka;

import ch.adesso.teleport.query.EventEnvelope;

public class EventEnvelopeAvroDeserializer extends KafkaAvroReflectDeserializer<EventEnvelope> {
	public EventEnvelopeAvroDeserializer() {
		super(EventEnvelope.class);
	}
}
