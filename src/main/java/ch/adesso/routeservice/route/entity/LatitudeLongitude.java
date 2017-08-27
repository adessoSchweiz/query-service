package ch.adesso.routeservice.route.entity;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@Embeddable
public class LatitudeLongitude {
	private Double latitude;
	private Double longitude;
}
