package ch.adesso.teleport.query.route.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table
@NamedQueries({ @NamedQuery(name = "findAllRoutes", query = "SELECT r FROM Route r") })
public class Route {

	@Id
	private String id;
	private long version = 0;

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
	private String passengerComment;
	private String estimatedTime;
	private String estimatedDistance;

	private RouteStatus status;

}
