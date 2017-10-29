package ch.adesso.teleport.query.route.boundary;

import static java.util.concurrent.CompletableFuture.runAsync;

import java.util.Properties;
import java.util.concurrent.ExecutorService;

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
import ch.adesso.teleport.query.route.entity.CarTypeEnum;
import ch.adesso.teleport.query.route.entity.Route;
import ch.adesso.teleport.query.route.entity.RouteStatus;
import ch.adesso.teleport.query.route.event.RouteCreatedEvent;
import ch.adesso.teleport.query.route.event.RouteEvent;
import ch.adesso.teleport.query.route.event.RouteEventEnvelope;
import ch.adesso.teleport.query.route.event.RouteStatusChangedEvent;

@Startup
@Singleton
public class RouteEventHandler {

	@Inject
	RouteService routeService;

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
		Route route = new Route();
		route.setCarType(event.getCarType() != null ? CarTypeEnum.valueOf(event.getCarType()) : null);
		route.setPassengerId(event.getPassengerId());
		route.setPassengerComment(event.getPassengerComment());
		route.setId(event.getAggregateId());
		route.setVersion(event.getSequence());
		route.setFrom(event.getFrom());
		route.setTo(event.getTo());
		route.setNoOfPersons(event.getNoOfPersons());
		route.setEstimatedDistance(event.getEstimatedDistance());
		route.setEstimatedTime(event.getEstimatedTime());

		routeService.createRoute(route);
	}

	public void on(@Observes RouteStatusChangedEvent event) {
		routeService.updateStatus(event.getAggregateId(), event.getSequence(), RouteStatus.valueOf(event.getStatus()));
	}
}
