package com.rcggs.confluent.tools.avro;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.Test;

import com.rcggs.confluent.tools.core.Context;

import junit.framework.TestCase;

public class AvroConsumerTest extends TestCase {

	@Test
	public void testProduceAvro() {
		Properties props = new Properties();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Context.get("confluent.broker.url"));
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"io.confluent.kafka.serializers.KafkaAvroDeserializer");
		props.put("schema.registry.url", String.format("%s://%s", Context.get("confluent.schemaregistry.scheme"),
				Context.get("confluent.schemaregistry.url")));

		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		String topic = "test";
		final Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		consumer.subscribe(Arrays.asList(topic));

		try {
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(100);
				for (ConsumerRecord<String, String> record : records) {
					System.err.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(),
							record.value());
				}
			}
		} finally {
			consumer.close();
		}
	}
}
