package ch.adesso.teleport.query.passenger.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ch.adesso.teleport.query.party.entity.Party;
import ch.adesso.teleport.query.party.entity.PartyRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public class Passenger extends PartyRole {

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private CreditCard creditCard;

	private PassengerStatus status;

	public Passenger(String id, Party party) {
		super(id, party);
	}

}
