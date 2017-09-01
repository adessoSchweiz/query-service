package ch.adesso.partyservice.party.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.avro.reflect.Nullable;
import org.apache.avro.reflect.Union;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.MetaValue;

import avro.shaded.com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Union({ Person.class, Organization.class })
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Party extends AggregateRoot {

	@ManyToAny(metaColumn = @Column(name = "partyrole_type"), fetch = FetchType.LAZY)
	@AnyMetaDef(idType = "string", metaType = "string", metaValues = {
			@MetaValue(targetEntity = Passenger.class, value = "P"),
			@MetaValue(targetEntity = Driver.class, value = "D") })
	@JoinTable(name = "PartyRoleAssigment", joinColumns = @JoinColumn(name = "party_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@Nullable
	private List<PartyRole> partyRoles;

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.ALL })
	@JoinTable(name = "PartyContactAssigment", joinColumns = @JoinColumn(name = "party_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	@Nullable
	private List<Contact> contacts;

	public Party() {
		partyRoles = Lists.newArrayList();
		contacts = Lists.newArrayList();
	}

	@SuppressWarnings("unchecked")
	public <T extends PartyRole> T getPartyRole(Class<T> clazz) {
		for (PartyRole r : getPartyRoles()) {
			if (r.getClass().isAssignableFrom(clazz)) {
				return (T) r;
			}
		}
		return null;
	}

	public void addPartyRole(PartyRole partyRole) {
		if (partyRoles == null) {
			partyRoles = new ArrayList<>();
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
			contacts = new ArrayList<>();
		}

		contacts.add(contact);
	}

	public void deleteContact(Contact contact) {
		if (contacts != null) {
			contacts.remove(contact);
		}
	}
}
