package ch.adesso.partyservice.party.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.avro.reflect.AvroIgnore;
import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table
public class CreditCard {

	@Id
	@AvroIgnore
	@JsonIgnore
	private String id;

	private String cardNumber;
	private CreditCardTypeEnum cardType;
	private String nameOnCard;
	private int validToMonth;
	private int validToYear;
	private int secretNumber;

	public CreditCard(String cardNumber, CreditCardTypeEnum cardType, String nameOnCard, int validToMonth,
			int validToYear, int secretNumber) {
		super();
		this.id = UUID.randomUUID().toString();
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.nameOnCard = nameOnCard;
		this.validToMonth = validToMonth;
		this.validToYear = validToYear;
		this.secretNumber = secretNumber;
	}
}
