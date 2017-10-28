package ch.adesso.teleport.query.person.controller;

import ch.adesso.kafka.KafkaAvroReflectDeserializer;
import ch.adesso.teleport.query.person.event.PersonEventEnvelope;

public class PersonEventEnvelopeAvroDeserializer extends KafkaAvroReflectDeserializer<PersonEventEnvelope> {
	public PersonEventEnvelopeAvroDeserializer() {
		super(PersonEventEnvelope.class);
	}
}
