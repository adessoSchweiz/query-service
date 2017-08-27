package ch.adesso.partyservice.party.entity;

import java.sql.Driver;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;

import org.apache.avro.reflect.AvroIgnore;
import org.apache.avro.reflect.Nullable;
import org.apache.avro.reflect.Union;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Data;
import lombok.ToString;

@JsonTypeInfo(use = Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = Passenger.class, name = "passenger"), @Type(value = Driver.class, name = "driver") })
@Data
@ToString(exclude = { "party" })
@Union({ Passenger.class, Driver.class })
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PartyRole {

	@Nullable
	@javax.persistence.Id
	private String id;

	@JsonIgnore
	@AvroIgnore
	@Any(metaColumn = @Column(name = "party_type"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "string", metaType = "string", metaValues = {
			@MetaValue(value = "P", targetEntity = Person.class),
			@MetaValue(value = "O", targetEntity = Organization.class) })
	@JoinColumn(name = "party_id")
	private Party party;
}
