package ch.adesso.teleport.query.person.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.adesso.teleport.query.person.entity.Person;
import ch.adesso.teleport.query.person.entity.PersonStatus;

@Stateless
public class PersonService {

	@PersistenceContext
	private EntityManager em;

	public void createPerson(String personId) {
		Person p = new Person();
		p.setId(personId);
		p.setVersion(0);
		em.merge(p);
	}

	public void updatePersonData(String personId, long version, String firstname, String lastname, String birtday) {
		Person p = em.find(Person.class, personId);
		p.setVersion(version);
		p.setFirstname(firstname);
		p.setLastname(lastname);
		p.setBirthday(birtday);
		em.merge(p);
	}

	public void updateContacts(String personId, long version, String email, String mobil) {
		Person p = em.find(Person.class, personId);
		p.setVersion(version);
		p.addEmailAddress(email);
		p.addMobileAddress(mobil);
		em.merge(p);
	}

	public void updateStatus(String personId, long version, PersonStatus status) {
		Person p = em.find(Person.class, personId);
		p.setVersion(version);
		p.setStatus(status);
		em.merge(p);
	}
}
