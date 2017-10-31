package ch.adesso.teleport.query.party.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@MappedSuperclass
public class PartyRole {
	@Id
	private String id;

}
