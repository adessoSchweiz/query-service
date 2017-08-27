package ch.adesso.utils.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class KafkaConsumerProvider {
	private static final String PARTY_TOPIC = "party-events-v1";
	private static final String ROUTE_TOPIC = "route-request-topic";

	private KafkaConsumer<String, Object> partyConsumer;
	private KafkaConsumer<String, Object> routeConsumer;

	@PostConstruct
	public void init() {
		this.partyConsumer = createConsumer(PARTY_TOPIC);
		this.routeConsumer = createConsumer(ROUTE_TOPIC);
	}

	@Produces
	@KafkaConsumerType(TopicName.PARTY)
	public KafkaConsumer<String, Object> getPartyConsumer() {
		return partyConsumer;
	}

	@Produces
	@KafkaConsumerType(TopicName.ROUTE)
	public KafkaConsumer<String, Object> getRouteConsumer() {
		return routeConsumer;
	}

	public KafkaConsumer<String, Object> createConsumer(String topic) {
		Properties props = loadProperties();

		props.put("group.id", props.getProperty("group.id") + "-" + UUID.randomUUID().toString());
		props.put("key.deserializer", StringDeserializer.class);
		if (PARTY_TOPIC.equals(topic)) {
			props.put("value.deserializer", PartyEventDeserializer.class);
		} else {
			props.put("value.deserializer", RouteEventDeserializer.class);
		}
		KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(props);

		consumer.subscribe(Arrays.asList(topic));
		return consumer;
	}

	public Properties loadProperties() {
		Properties properties = new Properties();
		final InputStream stream = KafkaConsumerProvider.class.getResourceAsStream("/kafka-consumer.properties");
		if (stream == null) {
			throw new RuntimeException("No kafka producer properties found !!!");
		}
		try {
			properties.load(stream);
		} catch (final IOException e) {
			throw new RuntimeException("Configuration could not be loaded!");
		}

		return updateProperties(properties);
	}

	protected Properties updateProperties(Properties properties) {
		return properties;
	}
}
