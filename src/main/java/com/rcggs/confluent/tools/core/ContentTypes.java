package com.rcggs.confluent.tools.core;

public final class ContentTypes {

	/**
	 * Standard Context-type header key
	 */
	public static final String KEY = "Content-type";
	/**
	 * Standard Context-type application json
	 */
	public static final String JSON = "application/json";
	/**
	 * Standard Kafka Context-type application avro
	 */
	public static final String KAFKA_AVRO = "application/vnd.kafka.avro.v2+json";
	/**
	 * Standard Kafka Context-type application json
	 */
	public static final String KAFKA_JSON = "application/vnd.kafka.v1+json";
	/**
	 * Standard Schema Registry content type json
	 */
	public static final String SCHEMA_REGISTRY_JSON = "application/vnd.schemaregistry.v1+json";

}