package ch.adesso.teleport.query.organization.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ch.adesso.teleport.query.party.entity.Party;

@Entity
@DiscriminatorValue("O")
public class Organization extends Party {

}