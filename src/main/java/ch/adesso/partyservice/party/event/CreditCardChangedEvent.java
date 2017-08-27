package ch.adesso.partyservice.party.event;

import ch.adesso.partyservice.party.entity.CreditCardTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class CreditCardChangedEvent extends PartyEvent {

	private String cardNumber;
	private CreditCardTypeEnum cardType;
	private String nameOnCard;
	private int validToMonth;
	private int validToYear;
	private int secretNumber;

	public CreditCardChangedEvent(String aggregateId, long sequence, String cardNumber, CreditCardTypeEnum cardType,
			String nameOnCard, int validToMonth, int validToYear, int secretNumber) {
		super(CreditCardChangedEvent.class, aggregateId, sequence);
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.nameOnCard = nameOnCard;
		this.validToMonth = validToMonth;
		this.validToYear = validToYear;
		this.secretNumber = secretNumber;
	}
}
