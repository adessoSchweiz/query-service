package ch.adesso.teleport.query.passenger.controller;

import ch.adesso.kafka.KafkaAvroReflectDeserializer;
import ch.adesso.teleport.query.passenger.event.PassengerEventEnvelope;

public class PassengerEventEnvelopeAvroDeserializer extends KafkaAvroReflectDeserializer<PassengerEventEnvelope> {
	public PassengerEventEnvelopeAvroDeserializer() {
		super(PassengerEventEnvelope.class);
	}
}
