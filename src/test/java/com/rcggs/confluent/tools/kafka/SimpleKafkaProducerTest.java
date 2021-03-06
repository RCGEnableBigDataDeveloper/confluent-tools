package com.rcggs.confluent.tools.kafka;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.log4j.Logger;

import com.google.common.base.Throwables;
import com.rcggs.confluent.tools.util.HttpUtil;

public class SimpleKafkaProducerTest {

	private static final Logger logger = Logger.getLogger(SimpleKafkaProducerTest.class);

	public static void main(String[] args) {

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.56.10:9092");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "client_1");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

		final KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(props);

		try {

			// final String json =
			// IOUtils.toString(SimpleKafkaProducerTest.class.getResourceAsStream("/schema.json"),
			// StandardCharsets.UTF_8);

			final String json = "phone_chnage,1561397835381,chicago,illinois,cook,60602,41.8781,-87.6298";

			IntStream.range(0, 100).forEach(e -> {
				try {

					System.out.println(json);
					Thread.sleep(1000);
					ProducerRecord<byte[], byte[]> record = new ProducerRecord<byte[], byte[]>("events",
							"test".getBytes(), json.getBytes());
					producer.send(record).get();

				} catch (Exception e1) {
					logger.error(Throwables.getStackTraceAsString(e1));
					e1.printStackTrace();
				}

			});

		} catch (Exception e) {

			logger.error(Throwables.getStackTraceAsString(e));
			e.printStackTrace();

		} finally {
			if (producer != null)
				producer.close();
		}
	}
}
