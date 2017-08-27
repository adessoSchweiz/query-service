package ch.adesso.utils.kafka;

import ch.adesso.partyservice.party.event.EventEnvelope;

public class PartyEventDeserializer extends KafkaAvroReflectDeserializer<EventEnvelope> {
	public PartyEventDeserializer() {
		super(EventEnvelope.class);
	}

}
