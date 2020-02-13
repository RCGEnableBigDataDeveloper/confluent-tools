package com.rcggs.confluent.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

public class BaseTest extends TestCase {

	private String CONFIG_FILE = "/topic-def-1.json";
	
	private ObjectMapper mapper;
	private TopicDef topicDef;

	protected BaseTest() {
		mapper = new ObjectMapper();
		JsonNode node;
		topicDef = new TopicDef();
		
		try {	
			node = mapper.readTree(getClass().getResourceAsStream(CONFIG_FILE));
			topicDef.setName(node.get(0).get("topic").get("name").asText());
			topicDef.setReplicas(node.get(0).get("topic").get("replicas").asInt());
			topicDef.setPartitions(node.get(0).get("topic").get("partitions").asInt());
			topicDef.setCompatabilityMode(node.get(0).get("schema").get("compatability-mode").asText());
			topicDef.setSchema(node.get(0).get("schema").get("schema").toString());
			topicDef.setSchemaType(node.get(0).get("schema").get("schema").get("schema").toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected TopicDef getTopicDef(){
		return this.topicDef;
	}

	public static void main(String[] args) {
		new BaseTest();
	}
}
