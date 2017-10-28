package ch.adesso.teleport.query.party.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "party" })
@ToString(exclude = { "party" })
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PartyRole {

	@Id
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Party party;

	public PartyRole(String id, Party party) {
		this.id = id;
		this.party = party;
	}

}
