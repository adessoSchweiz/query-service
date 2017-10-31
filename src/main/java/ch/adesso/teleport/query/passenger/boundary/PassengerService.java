package ch.adesso.teleport.query.passenger.boundary;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ch.adesso.teleport.query.passenger.entity.CreditCard;
import ch.adesso.teleport.query.passenger.entity.Passenger;
import ch.adesso.teleport.query.person.entity.Person;

@Stateless
public class PassengerService {

	@PersistenceContext
	private EntityManager em;

	public void createPassenger(String personId, long version) {
		if (em.find(Passenger.class, personId) != null) {
			throw new RuntimeException(String.format("Passenger [%s] already exists", personId));
		}

		Passenger passenger = new Passenger(personId);
		em.merge(passenger);

		updateAggregateRootVersion(personId, version);
	}

	public void addCreditCard(String personId, long version, CreditCard creditCard) {
		Passenger passenger = em.find(Passenger.class, personId);
		if (passenger == null) {
			throw new RuntimeException("Passenger not found");
		}
		creditCard.setId(UUID.randomUUID().toString());
		passenger.setCreditCard(creditCard);
		em.merge(passenger);

		updateAggregateRootVersion(personId, version);
	}

	public void updateCreditCard(String personId, long version, CreditCard creditCard) {
		Passenger passenger = em.find(Passenger.class, personId);
		if (passenger == null) {
			throw new RuntimeException("Passenger not found");
		}
		CreditCard cc = passenger.getCreditCard();
		creditCard.setId(cc.getId());
		passenger.setCreditCard(creditCard);
		em.merge(passenger);

		updateAggregateRootVersion(personId, version);
	}

	private void updateAggregateRootVersion(String personId, long version) {
		Person person = getPerson(personId);
		person.setVersion(version);
		em.merge(person);
	}

	// person is in another topic, so we wait or data
	private Person getPerson(String personId) {
		Person p = em.find(Person.class, personId);

		while (p == null) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				//
			}
			p = em.find(Person.class, personId);
		}

		return p;
	}

}
