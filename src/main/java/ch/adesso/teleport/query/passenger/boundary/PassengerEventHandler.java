package ch.adesso.teleport.query.passenger.boundary;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import com.airhacks.porcupine.execution.boundary.Dedicated;

import ch.adesso.kafka.KafkaConfiguration;
import ch.adesso.kafka.KafkaConsumerRunner;
import ch.adesso.teleport.query.passenger.controller.PassengerEventEnvelopeAvroDeserializer;
import ch.adesso.teleport.query.passenger.entity.CreditCard;
import ch.adesso.teleport.query.passenger.entity.CreditCardTypeEnum;
import ch.adesso.teleport.query.passenger.event.CreditCardChangedEvent;
import ch.adesso.teleport.query.passenger.event.CreditCardCreatedEvent;
import ch.adesso.teleport.query.passenger.event.PassengerCreatedEvent;
import ch.adesso.teleport.query.passenger.event.PassengerEvent;
import ch.adesso.teleport.query.passenger.event.PassengerEventEnvelope;

@Startup
@Singleton
public class PassengerEventHandler {

	private static final Logger LOG = Logger.getLogger(PassengerEventHandler.class.getName());

	@Inject
	PassengerService passengerService;

	@Inject
	private Event<PassengerEvent> passengerEvents;

	@Inject
	@Dedicated
	private ExecutorService passengerPool;

	private KafkaConsumerRunner<PassengerEventEnvelope> passengerKafkaConsumer;

	@PostConstruct
	public void onInit() {
		Properties props = KafkaConfiguration.consumerDefaultProperties();
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PassengerEventEnvelopeAvroDeserializer.class);
		KafkaConsumerRunner<PassengerEventEnvelope> passengerKafkaConsumer = new KafkaConsumerRunner<>(props, e -> {
			LOG.log(Level.INFO, "PASSENGER_EVENT: " + e);
			passengerEvents.fire(e.getEvent());
		}, "passenger-events-topic");
		runAsync(passengerKafkaConsumer, passengerPool);
	}

	@PreDestroy
	public void close() {
		passengerKafkaConsumer.shutdown();
	}

	public void on(@Observes PassengerCreatedEvent event) {
		passengerService.createPassenger(event.getAggregateId());
	}

	public void on(@Observes CreditCardCreatedEvent event) {
		CreditCard cc = new CreditCard();
		cc.setCardNumber(event.getCardNumber());
		cc.setCardType(CreditCardTypeEnum.valueOf(event.getCardType()));
		cc.setNameOnCard(event.getNameOnCard());
		cc.setSecretNumber(event.getSecretNumber());
		cc.setValidToMonth(event.getValidToMonth());
		cc.setValidToYear(event.getValidToYear());
		passengerService.addCreditCard(event.getAggregateId(), event.getSequence(), cc);
	}

	public void on(@Observes CreditCardChangedEvent event) {
		CreditCard cc = new CreditCard();
		cc.setCardNumber(event.getCardNumber());
		cc.setCardType(CreditCardTypeEnum.valueOf(event.getCardType()));
		cc.setNameOnCard(event.getNameOnCard());
		cc.setSecretNumber(event.getSecretNumber());
		cc.setValidToMonth(event.getValidToMonth());
		cc.setValidToYear(event.getValidToYear());
		passengerService.updateCreditCard(event.getAggregateId(), event.getSequence(), cc);
	}
}
