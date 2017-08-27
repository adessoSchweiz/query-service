package ch.adesso.partyservice.party.entity;

import org.codehaus.jackson.annotate.JsonValue;

public enum ContactTypeEnum {
	DOMICILE, CORRESPONDENCE;

	@JsonValue
	public int toValue() {
		return ordinal();
	}
}
