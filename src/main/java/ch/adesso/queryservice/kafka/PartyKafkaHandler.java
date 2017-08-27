package ch.adesso.queryservice.kafka;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.airhacks.porcupine.execution.boundary.Dedicated;

import ch.adesso.partyservice.party.event.CoreEvent;
import ch.adesso.partyservice.party.event.EventEnvelope;
import ch.adesso.utils.kafka.KafkaConsumerType;
import ch.adesso.utils.kafka.TopicName;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PartyKafkaHandler {

	@Inject
	@KafkaConsumerType(TopicName.PARTY)
	KafkaConsumer<String, Object> partyConsumer;

	@Dedicated("party")
	@Inject
	ExecutorService kafka;

	@Inject
	Event<CoreEvent> partyEventChannel;

	@PostConstruct
	public void onInit() {
		runAsync(this::handlePartyEvents, kafka);
	}

	@PreDestroy
	public void close() {
		partyConsumer.close();
	}

	public void handlePartyEvents() {
		while (true) {
			ConsumerRecords<String, Object> records = partyConsumer.poll(100);
			for (ConsumerRecord<String, Object> record : records) {
				Object obj = record.value();
				CoreEvent event = ((EventEnvelope) obj).getEvent();
				partyEventChannel.fire(event);
			}
		}
	}
}
