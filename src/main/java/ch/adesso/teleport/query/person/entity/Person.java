package ch.adesso.teleport.query.person.entity;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import ch.adesso.teleport.query.contact.entity.Contact;
import ch.adesso.teleport.query.contact.entity.ContactTypeEnum;
import ch.adesso.teleport.query.contact.entity.ElectronicAddress;
import ch.adesso.teleport.query.contact.entity.ElectronicAddressTypeEnum;
import ch.adesso.teleport.query.party.entity.Party;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("P")
@NamedQueries({ @NamedQuery(name = "Person.findAllPersons", query = "SELECT p FROM Person p") })
public class Person extends Party {

	private String firstname;
	private String lastname;
	private String birthday;
	private PersonStatus status;

	public void addEmailAddress(String email) {
		if (email == null) {
			return;
		}

		Set<Contact> contacts = getContacts();
		Stream<ElectronicAddress> eaStream = contacts.stream().filter(c -> c.getAddress() instanceof ElectronicAddress)
				.map(c -> (ElectronicAddress) c.getAddress())
				.filter(a -> a.getElectronicType() == ElectronicAddressTypeEnum.EMAIL && a.getValue() == email);

		if (eaStream.count() == 1) {
			eaStream.findFirst().get().setValue(email);
		} else {
			ElectronicAddress ea = new ElectronicAddress();
			ea.setId(UUID.randomUUID().toString());
			ea.setElectronicType(ElectronicAddressTypeEnum.EMAIL);
			ea.setValue(email);

			Contact c = new Contact(UUID.randomUUID().toString());
			c.setContactType(ContactTypeEnum.CORRESPONDENCE);
			c.setAddress(ea);

			addContact(c);
		}
	}

	public void addMobileAddress(String mobil) {
		if (mobil == null) {
			return;
		}

		Set<Contact> contacts = getContacts();
		Stream<ElectronicAddress> eaStream = contacts.stream().filter(c -> c.getAddress() instanceof ElectronicAddress)
				.map(c -> (ElectronicAddress) c.getAddress())
				.filter(a -> a.getElectronicType() == ElectronicAddressTypeEnum.TELEFON && a.getValue() == mobil);

		if (eaStream.count() == 1) {
			eaStream.findFirst().get().setValue(mobil);
		} else {
			ElectronicAddress ea = new ElectronicAddress();
			ea.setId(UUID.randomUUID().toString());
			ea.setElectronicType(ElectronicAddressTypeEnum.TELEFON);
			ea.setValue(mobil);

			Contact c = new Contact(UUID.randomUUID().toString());
			c.setContactType(ContactTypeEnum.CORRESPONDENCE);
			c.setAddress(ea);

			addContact(c);
		}
	}

}
