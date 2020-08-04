package com.rcggs.confluent.tools.kafka;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SimpleKafkaConsumerTest {

	Properties config;

	void consume() {

		Properties props = getConsumerConfig();

		KafkaConsumer<String, Long> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, Long>(props);
		Executors.newFixedThreadPool(10).execute(new Runnable() {
			public void run() {
				consumer.subscribe(Collections.singletonList("output2"));
				while (true) {
					ConsumerRecords<String, Long> records = consumer.poll(100);
					for (ConsumerRecord<String, Long> record : records) {
						System.err.println(" " + record.key() + " : " + record.value());
					}
				}
			}
		});
	}

	Properties getConsumerConfig() {

		Properties props = new Properties();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.10:9092");
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, "client");

		// props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
		// "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		// props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
		// "org.apache.kafka.common.serialization.ByteArrayDeserializer");

		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		props.put("group.id", "group222");
		props.put("auto.offset.reset", "earliest");

		// KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(props);
		return props;

	}

	public static void main(String[] args) {
		SimpleKafkaConsumerTest test = new SimpleKafkaConsumerTest();
		test.consume();
	}
}
