package ch.adesso.routeservice.route.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.avro.reflect.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table
@NamedQueries({ @NamedQuery(name = "findAllRouteOrders", query = "SELECT r FROM RouteRequest r") })
public class RouteRequest {

	@Id
	private String id;
	private String passengerId;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "latitude", column = @Column(name = "from_latitude")),
			@AttributeOverride(name = "longitude", column = @Column(name = "from_longitude")), })
	private LatitudeLongitude from;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "latitude", column = @Column(name = "to_latitude")),
			@AttributeOverride(name = "longitude", column = @Column(name = "to_longitude")), })
	private LatitudeLongitude to;

	private int noOfPersons;

	private CarType carType;

	@Nullable
	private String passengerComment;

	@Nullable
	private String estimatedTime;

	@Nullable
	private String estimatedDistance;
}