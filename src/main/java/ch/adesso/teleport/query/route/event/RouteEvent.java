package ch.adesso.teleport.query.route.event;

import org.apache.avro.reflect.Union;

import ch.adesso.teleport.query.CoreEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Union({ RouteCreatedEvent.class, RouteStatusChangedEvent.class })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class RouteEvent extends CoreEvent {

	public RouteEvent(Class<?> eventType, String aggregateId, long sequence) {
		super(eventType.getSimpleName(), aggregateId, sequence);
	}
}