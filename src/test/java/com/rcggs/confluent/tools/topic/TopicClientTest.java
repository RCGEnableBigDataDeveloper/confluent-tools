package com.rcggs.confluent.tools.topic;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.rcggs.confluent.tools.BaseTest;
import com.rcggs.confluent.tools.TopicDef;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.core.Context;

public class TopicClientTest extends BaseTest {

	private static final Logger logger = Logger.getLogger(Context.class);

	private TopicClient topicClient;

	private TopicDef topicDef;

	@Override
	protected void setUp() throws Exception {
		topicDef = getTopicDef();
		topicClient = new TopicClient.Builder().withScheme(Context.get("confluent.schemaregistry.scheme"))
				.withUrl(Context.get("confluent.rest.topic.url")).withZookeeper(Context.get("confluent.zookeeper.url"))
				.withConfiguration(new Properties()).withContentType(ContentTypes.SCHEMA_REGISTRY_JSON).build();

		super.setUp();
	}

	@Test
	public void testTopicCreate() {
		topicClient.createTopic(topicDef.getName(), topicDef.getPartitions(), topicDef.getReplicas());
	}

	@Test
	public void testTopicsList() {
		String topics = topicClient.list();
		assertNotNull(topics);
		logger.info(topics);
	}

	@Test
	public void testTopicGet() {
		String topics = topicClient.get(topicDef.getName());
		assertNotNull(topics);
		logger.info(topics);
	}

	@Test
	public void testTopicDelete() {
		//topicClient.delete(topicDef.getName());
		topicClient.delete("test-topic-10");
		topicClient.delete("test_2_14");
		topicClient.delete("input_topic_1");

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
