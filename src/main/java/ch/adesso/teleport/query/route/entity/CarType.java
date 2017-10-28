package ch.adesso.teleport.query.route.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CarType {
	ECONOMIC, STANDARD, PREMIUM;

	@JsonValue
	public int toValue() {
		return ordinal();
	}
}
