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

import ch.adesso.routeservice.route.entity.RouteRequest;
import ch.adesso.utils.kafka.KafkaConsumerType;
import ch.adesso.utils.kafka.TopicName;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class RouteKafkaHandler {

	@Inject
	@KafkaConsumerType(TopicName.ROUTE)
	KafkaConsumer<String, Object> routeConsumer;

	@Dedicated("route")
	@Inject
	ExecutorService kafka;

	@Inject
	Event<RouteRequest> routeEventChannel;

	@PostConstruct
	public void onInit() {
		runAsync(this::handleRouteEvents, kafka);
	}

	@PreDestroy
	public void close() {
		routeConsumer.close();
	}

	public void handleRouteEvents() {
		while (true) {
			ConsumerRecords<String, Object> records = routeConsumer.poll(100);
			for (ConsumerRecord<String, Object> record : records) {
				Object obj = record.value();
				routeEventChannel.fire((RouteRequest) obj);
			}
		}
	}
}
