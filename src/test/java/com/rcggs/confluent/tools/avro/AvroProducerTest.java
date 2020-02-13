package com.rcggs.confluent.tools.avro;

import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.junit.Test;

import com.rcggs.confluent.tools.BaseTest;
import com.rcggs.confluent.tools.TopicDef;
import com.rcggs.confluent.tools.core.Context;

public class AvroProducerTest extends BaseTest {

	private TopicDef topicDef;

	@Test
	public void testProduceAvro() {

		topicDef = getTopicDef();

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Context.get("confluent.broker.url"));
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaAvroSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				io.confluent.kafka.serializers.KafkaAvroSerializer.class);

		props.put("schema.registry.url", String.format("%s://%s", Context.get("confluent.schemaregistry.scheme"),
				Context.get("confluent.schemaregistry.url")));

		KafkaProducer<Object, Object> producer = new KafkaProducer<>(props);
		String key = "key1";
		String userSchema = "{\"type\":\"record\"," + "\"name\":\"record1\","
				+ "\"fields\":[{ \"name\" : \"Name\" , \"type\" : \"string\" },{ \"name\" : \"Age\" , \"type\" : \"int\" },{ \"name\" : \"height\" , \"type\" : \"int\", \"default\":10 }]}";
		Schema.Parser parser = new Schema.Parser();
		Schema schema = parser.parse(userSchema);
		GenericRecord avroRecord = new GenericData.Record(schema);
		avroRecord.put("Name", "value1");
		avroRecord.put("Age", 44);
		avroRecord.put("height", 44);

		ProducerRecord<Object, Object> record = new ProducerRecord<>(topicDef.getName(), key, avroRecord);

		System.err.println(avroRecord);

		try {
			producer.send(record);
		} catch (SerializationException e) {
			e.printStackTrace();
		} finally {
			producer.flush();
			producer.close();
		}
	}
}