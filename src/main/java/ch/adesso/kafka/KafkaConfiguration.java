package ch.adesso.kafka;

import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConfiguration {

	public static final String SCHEMA_REGISTRY_URL = "schema.registry.url";

	public static Properties consumerDefaultProperties() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS")); // kafka:9092
		properties.put(SCHEMA_REGISTRY_URL, System.getenv("SCHEMA_REGISTRY_URL")); // http://avro-schema-registry:8081
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EventEnvelopeAvroDeserializer.class);
		properties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group" + UUID.randomUUID());
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return properties;
	}

}
