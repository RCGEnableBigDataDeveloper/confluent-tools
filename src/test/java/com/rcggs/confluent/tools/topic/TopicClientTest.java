package com.rcggs.confluent.tools.topic;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;

import junit.framework.TestCase;

public class TopicClientTest extends TestCase {

	private static final Logger logger = Logger.getLogger(Context.class);

	private TopicClient topicClient;
	private String topicName;

	@Override
	protected void setUp() throws Exception {
		topicClient = new TopicClient.Builder().withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.rest.topic.url")).withZookeeper(Context.get("confluent.zookeeper.url"))
				.withConfiguration(new Properties()).withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();

		topicName = "unit-test-topic";
		super.setUp();
	}

	@Test
	public void testTopicCreate() {
		topicClient.createTopic(topicName, 3, 1);
	}

	@Test
	public void testTopicsList() {
		String topics = topicClient.list();
		assertNotNull(topics);
		logger.debug(topics);
	}

	@Test
	public void testTopicGet() {
		String topics = topicClient.get(topicName);
		assertNotNull(topics);
		logger.debug(topics);
	}

	@Test
	public void testTopicDelte() {
		topicClient.delete(topicName);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
