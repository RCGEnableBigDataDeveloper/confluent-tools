package com.rcggs.confluent.tools;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TopicDef {

	private String name;
	private int partitions;
	private int replicas;
	private String compatabilityMode;
	private String schema;
	private String schemaType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPartitions() {
		return partitions;
	}

	public void setPartitions(int partitions) {
		this.partitions = partitions;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public String getCompatabilityMode() {
		return compatabilityMode;
	}

	public void setCompatabilityMode(String compatabilityMode) {
		this.compatabilityMode = compatabilityMode;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchemaType() {
		return schemaType;
	}

	public void setSchemaType(String schemaType) {
		this.schemaType = schemaType;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
