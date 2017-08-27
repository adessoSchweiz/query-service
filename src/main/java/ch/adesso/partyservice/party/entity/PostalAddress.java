package ch.adesso.partyservice.party.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.avro.reflect.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table
public class PostalAddress extends Address {

	private String street;

	@Nullable
	private String houseNo;

	private String city;
	private String postalcode;
	private String country;

	public PostalAddress(String addressId, String street, String houseNo, String city, String postalcode,
			String country) {
		super();
		this.setAddressId(addressId);
		this.street = street;
		this.houseNo = houseNo;
		this.city = city;
		this.postalcode = postalcode;
		this.country = country;
	}

}
