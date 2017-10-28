package ch.adesso.teleport.query.passenger.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class CreditCardCreatedEvent extends PassengerEvent {

	private String cardNumber;
	private String cardType;
	private String nameOnCard;
	private int validToMonth;
	private int validToYear;
	private int secretNumber;
}
