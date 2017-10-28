package ch.adesso.teleport.query.person.boundary;

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
import ch.adesso.teleport.query.person.controller.PersonEventEnvelopeAvroDeserializer;
import ch.adesso.teleport.query.person.entity.PersonStatus;
import ch.adesso.teleport.query.person.event.PersonChangedEvent;
import ch.adesso.teleport.query.person.event.PersonContactChangedEvent;
import ch.adesso.teleport.query.person.event.PersonCreatedEvent;
import ch.adesso.teleport.query.person.event.PersonEvent;
import ch.adesso.teleport.query.person.event.PersonEventEnvelope;
import ch.adesso.teleport.query.person.event.PersonStatusChangedEvent;

@Startup
@Singleton
public class PersonEventHandler {

	private static final Logger LOG = Logger.getLogger(PersonEventHandler.class.getName());

	@Inject
	private PersonService personService;

	@Inject
	Event<PersonEvent> personEvents;

	@Inject
	@Dedicated
	private ExecutorService personPool;

	private KafkaConsumerRunner<PersonEventEnvelope> personKafkaConsumer;

	@PostConstruct
	public void onInit() {
		Properties props = KafkaConfiguration.consumerDefaultProperties();
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PersonEventEnvelopeAvroDeserializer.class);
		KafkaConsumerRunner<PersonEventEnvelope> personKafkaConsumer = new KafkaConsumerRunner<>(props, e -> {
			LOG.log(Level.INFO, "EVENT: " + e);
			personEvents.fire(e.getEvent());
		}, "person-events-topic");
		runAsync(personKafkaConsumer, personPool);
	}

	@PreDestroy
	public void close() {
		personKafkaConsumer.shutdown();
	}

	public void on(@Observes PersonCreatedEvent event) {
		personService.createPerson(event.getAggregateId());
	}

	public void on(@Observes PersonChangedEvent event) {
		personService.updatePersonData(event.getAggregateId(), event.getSequence(), event.getFirstname(),
				event.getLastname(), event.getBirthday());
	}

	public void on(@Observes PersonContactChangedEvent event) {
		personService.updateContacts(event.getAggregateId(), event.getSequence(), event.getEmail(), event.getMobil());
	}

	public void on(@Observes PersonStatusChangedEvent event) {
		personService.updateStatus(event.getAggregateId(), event.getSequence(),
				PersonStatus.valueOf(event.getStatus()));
	}
}
