package ch.adesso.teleport.query.passenger.event;

import org.apache.avro.reflect.Union;

import ch.adesso.teleport.query.CoreEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Union({ PassengerCreatedEvent.class, CreditCardCreatedEvent.class, CreditCardChangedEvent.class })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class PassengerEvent extends CoreEvent {

	public PassengerEvent(Class<?> eventType, String aggregateId, long sequence) {
		super(eventType.getSimpleName(), aggregateId, sequence);
	}
}
