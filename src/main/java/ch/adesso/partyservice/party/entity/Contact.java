package ch.adesso.partyservice.party.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.avro.reflect.AvroIgnore;
import org.apache.avro.reflect.Nullable;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MetaValue;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class Contact {

	@Id
	private String contactId;

	@Any(metaColumn = @Column(name = "address_type"), fetch = FetchType.EAGER)
	@AnyMetaDef(idType = "string", metaType = "string", metaValues = {
			@MetaValue(value = "P", targetEntity = PostalAddress.class),
			@MetaValue(value = "E", targetEntity = ElectronicAddress.class) })
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@JoinColumn(name = "address_id")
	private Address address;

	@Nullable
	private ContactTypeEnum contactType;

	@JsonIgnore
	@AvroIgnore
	@Any(metaColumn = @Column(name = "party_type"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "string", metaType = "string", metaValues = {
			@MetaValue(value = "P", targetEntity = Person.class),
			@MetaValue(value = "O", targetEntity = Organization.class) })
	@JoinColumn(name = "party_id")
	private Party party;

}
