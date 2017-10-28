package ch.adesso.teleport.query.passenger.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table
public class CreditCard {

	@Id
	private String id;

	private String cardNumber;
	private CreditCardTypeEnum cardType;
	private String nameOnCard;
	private Integer validToMonth;
	private Integer validToYear;
	private Integer secretNumber;

}
