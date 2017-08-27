package ch.adesso.partyservice.party.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.avro.reflect.Nullable;

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
public class Passenger extends PartyRole {

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Nullable
	private CreditCard creditCard;

	public Passenger(String id, Party party) {
		super();
		this.setId(id);
		this.setParty(party);
	}

}
