package com.rcggs.confluent.tools.schema;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;

import junit.framework.TestCase;

public class SchemaRegistryClientTest extends TestCase {

	private static final Logger logger = Logger.getLogger(Context.class);

	private SchemaRegistryClient schemaRegistryClient;
	private String topicName;

	@Override
	protected void setUp() throws Exception {
		schemaRegistryClient = new SchemaRegistryClient.Builder()
				.withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.schemaregistry.url")).withConfiguration(new Properties())
				.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();
		topicName = "unit-test-topic-1";
		super.setUp();
	}

	@Test
	public void testSchemaGet() {
		String response = schemaRegistryClient.get(topicName);
		logger.info(response);
		assertNotNull(response);
	}

	@Test
	public void testSchemaCreate() {
		try {
			String schema = IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.json"),
					StandardCharsets.UTF_8);
			String response = schemaRegistryClient.add(topicName, schema);
			logger.info(response);
			assertNotNull(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSchemaList() {
		String response = schemaRegistryClient.list();
		logger.info(response);
		assertNotNull(response);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
