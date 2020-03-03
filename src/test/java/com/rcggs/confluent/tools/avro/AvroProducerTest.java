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
		String userSchema = "{\"name\": \"record1\",  \"type\": \"record\", \"namespace\": \"\",  \"fields\": [{ \"name\" : \"id\" , \"type\" : \"int\" },{ \"name\" : \"confidence\" , \"type\" : \"int\" },{ \"name\" : \"encode_format\" , \"type\" : \"int\" },{ \"name\" : \"epc\" , \"type\" : \"int\" },{ \"name\" : \"epcState\" , \"type\" : \"string\" },{ \"name\" : \"event\" , \"type\" : \"string\" },{ \"name\" : \"facility_id\" , \"type\" : \"string\" },{ \"name\" : \"filter_value\" , \"type\" : \"int\" },{ \"name\" : \"gtin\" , \"type\" : \"int\" },{ \"name\" : \"last_read\" , \"type\" : \"int\" },{ \"name\" : \"asnId\" , \"type\" : \"string\" },{ \"name\" : \"timestmp\" , \"type\" : \"string\" },{ \"name\" : \"lat\" , \"type\" : \"string\" },{ \"name\" : \"lng\" , \"type\" : \"string\" }]}";
		Schema.Parser parser = new Schema.Parser();
		Schema schema = parser.parse(userSchema);
		GenericRecord avroRecord = new GenericData.Record(schema);

		avroRecord.put("id", 1);
		avroRecord.put("confidence", 1);
		avroRecord.put("encode_format", 1);
		avroRecord.put("epc", 1);
		avroRecord.put("epcState", "%s");
		avroRecord.put("event", "%s");
		avroRecord.put("facility_id", "%s");
		avroRecord.put("filter_value", 10);
		avroRecord.put("gtin", 5556);
		avroRecord.put("last_read", 15487);
		avroRecord.put("asnId", "%s");
		avroRecord.put("timestmp", "%s");
		avroRecord.put("lat", "%s");
		avroRecord.put("lng", "%s");

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