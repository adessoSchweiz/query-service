package ch.adesso.teleport.query.passenger.boundary;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.adesso.teleport.query.passenger.entity.CreditCard;
import ch.adesso.teleport.query.passenger.entity.Passenger;
import ch.adesso.teleport.query.person.entity.Person;

@Stateless
public class PassengerService {

	@PersistenceContext
	private EntityManager em;

	public void createPassenger(String personId) {
		Person p = getPerson(personId);
		if (!p.hasPartyRole(Passenger.class)) {
			Passenger passenger = new Passenger(UUID.randomUUID().toString(), p);
			p.addPartyRole(passenger);
			em.merge(p);
		}
	}

	public void addCreditCard(String personId, long version, CreditCard creditCard) {
		Person p = getPerson(personId);
		Passenger passenger = p.getPartyRole(Passenger.class);
		creditCard.setId(UUID.randomUUID().toString());
		passenger.setCreditCard(creditCard);

		p.setVersion(version);
		em.merge(p);
	}

	public void updateCreditCard(String personId, long version, CreditCard creditCard) {
		Person p = getPerson(personId);
		Passenger passenger = p.getPartyRole(Passenger.class);
		CreditCard cc = passenger.getCreditCard();

		creditCard.setId(cc.getId());
		passenger.setCreditCard(creditCard);
		p.setVersion(version);
		em.merge(p);
	}

	private Person getPerson(String personId) {
		Person p = em.find(Person.class, personId);

		while (p == null) {
			p = em.find(Person.class, personId);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				//
			}
		}

		return p;
	}
}
