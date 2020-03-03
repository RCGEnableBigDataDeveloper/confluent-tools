package com.rcggs.confluent.tools.connect;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;

import junit.framework.TestCase;

public class ConnectClientTest extends TestCase {

	private static final Logger logger = Logger.getLogger(Context.class);

	private ConnectClient connectClient;
	private String connectorRequest;

	@Override
	protected void setUp() throws Exception {
		connectClient = new ConnectClient.Builder().withScheme(Context.get("confluent.connect.scheme"))
				.withUrl(Context.get("confluent.connect.url")).withConfiguration(new Properties())
				.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();
		connectorRequest = IOUtils.toString(getClass().getResourceAsStream("/connectors/bigquery-sink.json"), StandardCharsets.UTF_8);
		super.setUp();
	}

	@Test
	public void testConnectCreate() {
		String response = connectClient.createConnector(connectorRequest);
		logger.debug(response);
		assertNotNull(response);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
