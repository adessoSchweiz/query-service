package ch.adesso.teleport.query.route.boundary;

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
import ch.adesso.teleport.query.route.controller.RouteEventEnvelopeAvroDeserializer;
import ch.adesso.teleport.query.route.event.RouteCreatedEvent;
import ch.adesso.teleport.query.route.event.RouteEvent;
import ch.adesso.teleport.query.route.event.RouteEventEnvelope;

@Startup
@Singleton
public class RouteEventHandler {

	private static final Logger LOG = Logger.getLogger(RouteEventHandler.class.getName());

	@Inject
	Event<RouteEvent> routeEvents;

	@Inject
	@Dedicated
	private ExecutorService routePool;

	private KafkaConsumerRunner<RouteEventEnvelope> routeKafkaConsumer;

	@PostConstruct
	public void onInit() {
		Properties props = KafkaConfiguration.consumerDefaultProperties();
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, RouteEventEnvelopeAvroDeserializer.class);
		KafkaConsumerRunner<RouteEventEnvelope> routeKafkaConsumer = new KafkaConsumerRunner<>(props,
				e -> routeEvents.fire(e.getEvent()), "route-events-topic");
		runAsync(routeKafkaConsumer, routePool);
	}

	@PreDestroy
	public void close() {
		routeKafkaConsumer.shutdown();
	}

	public void on(@Observes RouteCreatedEvent event) {
		LOG.log(Level.INFO, "Got Event: " + event);
	}

}
