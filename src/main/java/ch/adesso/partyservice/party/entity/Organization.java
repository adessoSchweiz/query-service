package ch.adesso.partyservice.party.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import ch.adesso.partyservice.party.event.CoreEvent;

@Entity
@Table
public class Organization extends Party {

	@Override
	public void applyEvent(CoreEvent event) {
		// TODO Auto-generated method stub

	}

}
