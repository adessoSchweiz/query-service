package ch.adesso.teleport.query.contact.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ch.adesso.teleport.query.party.entity.Party;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = { "party" })
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "party" })
@Entity
@Table
public class Contact {

	@Id
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Address address;

	private ContactTypeEnum contactType;

	@ManyToOne(fetch = FetchType.LAZY)
	private Party party;

	public Contact(String id, Party party) {
		this.id = id;
		this.party = party;
	}

}
