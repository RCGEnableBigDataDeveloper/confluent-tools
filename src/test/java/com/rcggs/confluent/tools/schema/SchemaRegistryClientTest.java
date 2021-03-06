package com.rcggs.confluent.tools.schema;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.BaseTest;
import com.rcggs.confluent.tools.TopicDef;
import com.rcggs.confluent.tools.core.CompatibilityMode;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;

public class SchemaRegistryClientTest extends BaseTest {

	private static final Logger logger = Logger.getLogger(Context.class);

	private SchemaRegistryClient schemaRegistryClient;
	
	private TopicDef topicDef;

	@Override
	protected void setUp() throws Exception {
		topicDef= getTopicDef();
		schemaRegistryClient = new SchemaRegistryClient.Builder()
				.withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.schemaregistry.url")).withConfiguration(new Properties())
				.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();
		super.setUp();
	}

	@Test
	public void testSchemaGet() {
		String response = schemaRegistryClient.get(topicDef.getName());
		logger.info(response);
		assertNotNull(response);
	}

	@Test
	public void testSchemaCreate() {
		try {
			String schema = IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema.json"),
					StandardCharsets.UTF_8);
			String response = schemaRegistryClient.add(topicDef.getName(), schema);
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

	@Test
	public void testSchemaDelete() {
		String response = schemaRegistryClient.delete(topicDef.getName() + "-value");
		logger.info(response);
		assertNotNull(response);
	}

	@Test
	public void testSchemaCompatibility() {
		String response = schemaRegistryClient.setCompatibility(topicDef.getName(), CompatibilityMode.BACKWARD);
		logger.info(response);
		assertNotNull(response);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
