package com.rcggs.confluent.tools.kafka;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleKafkaConsumerTest {

	Properties config;

	void consume() {

		Properties props = getConsumerConfig();

		KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(
				props);
		Executors.newFixedThreadPool(10).execute(new Runnable() {
			public void run() {
				consumer.subscribe(Collections.singletonList("test1"));
				while (true) {
					ConsumerRecords<String, String> records = consumer.poll(100);
					for (ConsumerRecord<String, String> record : records) {
						System.err.println(" " + record.key() + " : " + record.value());
					}
				}
			}
		});
	}

	Properties getConsumerConfig() {

		Properties props = new Properties();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.10:9092");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "client");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("group.id", "group222");
		props.put("auto.offset.reset", "earliest");

		KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(props);
		return props;

	}

	public static void main(String[] args) {
		SimpleKafkaConsumerTest test = new SimpleKafkaConsumerTest();
		test.consume();
	}
}
