package ch.adesso.teleport.query.route.event;

import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.Nullable;

import ch.adesso.teleport.query.route.entity.LatitudeLongitude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class RouteCreatedEvent extends RouteEvent {

	private String passengerId;
	private LatitudeLongitude from;
	private LatitudeLongitude to;

	@Nullable
	@AvroDefault("1")
	private int noOfPersons;

	private String carType;

	@Nullable
	private String passengerComment;

	@Nullable
	private String estimatedTime;

	@Nullable
	private String estimatedDistance;

	private String status;

}
