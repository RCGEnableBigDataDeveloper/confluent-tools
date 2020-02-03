package com.rcggs.confluent.tools.schema;

import org.apache.commons.io.IOUtils;

import com.rcggs.confluent.tools.core.Accepts;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;
import com.rcggs.confluent.tools.util.HttpUtil;

public class Test {

	static void addSchema(final String topicName, final String schema) {
		String scheme = Context.get("confluent.schemaregistry.scheme");
		String url = Context.get("confluent.schemaregistry.host");
		String resource = String.format("%s://%s/subjects/%s-value/versions/", scheme, url, topicName);
		HttpUtil.post(resource, schema, ContentTypes.SCHEMA_REGISTRY_JSON, Accepts.KAFKA_JSON);
	}

	static void getSchema(final String topicName) {
		String scheme = Context.get("confluent.schemaregistry.scheme");
		String url = Context.get("confluent.schemaregistry.host");
		String resource = String.format("%s://%s/subjects/%s-value/versions/1", scheme, url, topicName);
		String schemas = HttpUtil.get(resource);
		System.err.println(schemas);
	}

	public static void main(String[] args) {

		try {
			String json = IOUtils.toString(Test.class.getResourceAsStream("/produce.json"));

			// addSchema("newtopic2", json);
//			getSchema("newtopic2");

			// String body =
			// IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.avro"),
			// StandardCharsets.UTF_8);
			//
			// String res =
			// HttpUtil.post("http://192.168.56.10:9021/api/schema-registry/VbiNaaG7S0WV442_hVlrWg/subjects/my-topic-value/versions",
			// body, ContentTypes.KAFKA_AVRO,
			// Accepts.KAFKA_JSON);

			// String body =
			// IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.avro"),
			// StandardCharsets.UTF_8);
			//
			// String res =
			// HttpUtil.post("http://192.168.56.10:8082/topics/testavro3", body,
			// ContentTypes.KAFKA_AVRO,
			// Accepts.KAFKA_JSON);

			String topics =
			 HttpUtil.get("http://192.168.56.10:8082/topics/");
			
			 System.err.println(topics);
			//
			// String body1 =
			// IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.json"),
			// StandardCharsets.UTF_8);
			// HttpUtil.post("http://192.168.56.10:8081/subjects/newtest1-value/versions/",
			// body1,
			// ContentTypes.SCHEMA_REGISTRY_JSON, Accepts.KAFKA_JSON);
			//
			// String schemas =
			// HttpUtil.get("http://localhost:8081/subjects/testavro2-value/versions/1",
			// body1, null, null);
			//
			// System.err.println(schemas);
			//
			// String connector =
			// IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/connector.json"),
			// StandardCharsets.UTF_8);

			// System.err.println(connector);
			// String conectorRespone =
			// HttpUtil.post("http://192.168.56.10:8083/connectors", connector,
			// ContentTypes.JSON,
			// Accepts.KAFKA_JSON);
			// System.err.println(conectorRespone);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// http://192.168.56.10:8082/topics/testavro/partitions
	// http://192.168.56.10:8082/topics/testavro
	// http://192.168.56.10:8082/topics/
	// http://localhost:8082/topics/avrotest/versions

}
