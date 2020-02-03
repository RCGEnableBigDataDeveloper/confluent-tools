# confluent-tools
confluent-tools is an API for intgeracting with a confluent kafka cluster. It includes classes to automate standard processes including topic creation, schema registration, and connector configuration.

This API is written in java/scala, and has the following advantages over direct shell/CLI scripting

1. Runs in any enviroment with a JVM (cross-paltform)
2. Does not require a kafa client installation
3. Easier to layer in new functionality
4. Enhanced security
5. Better tooling for colaboration and development

# Example Usage

**Create a topic with 3 partitions and a replication factor of 3**
```java
TopicClient topicClient = new TopicClient.Builder()
	.withUrl(Context.get("confluent.rest.topic.url"))
	.withZookeeper(Context.get("confluent.zookeeper.url"))
	.withConfiguration(new Properties())
	.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON)
	.build();
	
topicClient.createTopic(topicName, 3, 3);        
```

**Register a schema and assign it to a topic**
```java
SchemaRegistryClient schemaRegistryClient = new SchemaRegistryClient.Builder()
	.withScheme(Context.get("confluent.schemaregistry.scheme"))
	.withUrl(Context.get("confluent.schemaregistry.url"))
	.withConfiguration(new Properties())
	.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON)
	.build();
	
String schemaRequest = IOUtils.toString(SchemaRegistryClient.class.getResourceAsStream("/schema-definition.json"),        StandardCharsets.UTF_8);
String response = schemaRegistryClient.add(topicName, schemaRequest);
```

**Create a kafka connector**
```java
ConnectClient connectClient = new ConnectClient.Builder()
	.withScheme(Context.get("confluent.connect.scheme"))
	.withUrl(Context.get("confluent.connect.url"))
	.withConfiguration(new Properties())
	.withContentType(ContentTypes.SCHEMA_REGISTRY_JSON)
	.build();  
				
connectorRequest = IOUtils.toString(getClass().getResourceAsStream("/connector.json"), StandardCharsets.UTF_8);  
connectClient.createConnector(connectorRequest);
```
