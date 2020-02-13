package com.rcggs.confluent.tools.avro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.BaseTest;
import com.rcggs.confluent.tools.core.CompatibilityMode;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;
import com.rcggs.confluent.tools.schema.SchemaRegistryClient;
import com.rcggs.confluent.tools.topic.TopicClient;

public class CompatabilityTest extends BaseTest {

	private static final Logger logger = Logger.getLogger(Context.class);

	private TopicClient topicClient;
	private SchemaRegistryClient schemaRegistryClient;

	private String topicName = "test_topic_007";

	@Override
	protected void setUp() throws Exception {

		schemaRegistryClient = new SchemaRegistryClient.Builder()
				.withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.schemaregistry.url")).withConfiguration(new Properties())
				.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();

		topicClient = new TopicClient.Builder().withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.rest.topic.url")).withZookeeper(Context.get("confluent.zookeeper.url"))
				.withConfiguration(new Properties()).withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();

		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testCompatability() {
		try {

			/** create the topic **/
			topicClient.createTopic(topicName, 3, 1);

			/** register the schema **/
			String schema = IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.json"),
					StandardCharsets.UTF_8);

			/** set the compatability mode **/
			String response = schemaRegistryClient.setCompatibility(topicName, CompatibilityMode.BACKWARD);
			logger.info(response);
			assertNotNull(response);

			response = schemaRegistryClient.add(topicName, schema);
			assertNotNull(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
