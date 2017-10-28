package ch.adesso.teleport.query.party.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import avro.shaded.com.google.common.collect.Sets;
import ch.adesso.teleport.query.contact.entity.Contact;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = { "contacts", "partyRoles" })
@EqualsAndHashCode(exclude = { "contacts", "partyRoles" })
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Party {

	@Id
	private String id;

	private long version = 0;

	@OneToMany(mappedBy = "party", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<PartyRole> partyRoles;

	@OneToMany(mappedBy = "party", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Contact> contacts;

	public Party() {
		partyRoles = Sets.newHashSet();
		contacts = Sets.newHashSet();
	}

	public <T extends PartyRole> T getPartyRole(Class<T> clazz) {
		for (PartyRole r : getPartyRoles()) {
			if (r.getClass().isAssignableFrom(clazz)) {
				return (T) r;
			}
		}
		return null;
	}

	public <T extends PartyRole> boolean hasPartyRole(Class<T> clazz) {
		return getPartyRole(clazz) != null;
	}

	public void addPartyRole(PartyRole partyRole) {
		if (partyRoles == null) {
			partyRoles = Sets.newHashSet();
		}
		partyRoles.add(partyRole);
	}

	public void deletePartyRole(PartyRole partyRole) {
		if (partyRoles != null) {
			partyRoles.remove(partyRole);
		}
	}

	public void addContact(Contact contact) {
		if (contacts == null) {
			contacts = Sets.newHashSet();
		}

		contacts.add(contact);
	}

	public void deleteContact(Contact contact) {
		if (contacts != null) {
			contacts.remove(contact);
		}
	}
}
