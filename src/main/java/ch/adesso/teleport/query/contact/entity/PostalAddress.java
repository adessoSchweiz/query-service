package ch.adesso.teleport.query.contact.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table
public class PostalAddress extends Address {

	private String street;
	private String houseNo;
	private String city;
	private String postalcode;
	private String country;
}