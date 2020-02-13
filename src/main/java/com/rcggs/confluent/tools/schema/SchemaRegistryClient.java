package com.rcggs.confluent.tools.schema;

import java.util.Properties;

import com.rcggs.confluent.tools.core.Accepts;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.util.HttpUtil;

public class SchemaRegistryClient {

	private String url;
	private Properties configuration;
	private String contentType;
	private String scheme;

	public static class Builder {

		private String url;
		private Properties configuration;
		private String contentType;
		private String scheme;

		public Builder withUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder withConfiguration(final Properties configuration) {
			this.configuration = configuration;
			return this;
		}

		public Builder withContentType(final String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder withScheme(String scheme) {
			this.scheme = scheme;
			return this;
		}

		public SchemaRegistryClient build() {
			SchemaRegistryClient schemaRegistryClient = new SchemaRegistryClient();
			schemaRegistryClient.url = this.url;
			schemaRegistryClient.configuration = this.configuration;
			schemaRegistryClient.contentType = this.contentType;
			schemaRegistryClient.scheme = scheme;
			return schemaRegistryClient;
		}
	}

	private SchemaRegistryClient() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getScheme() {
		return url;
	}

	public void setConfiguration(Properties configuration) {
		this.configuration = configuration;
	}

	public Properties getConfiguration() {
		return configuration;
	}

	public String getContentType() {
		return contentType;
	}

	public void setScheme(String contentType) {
		this.contentType = contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String add(final String topicName, final String schema) {
		String resource = String.format("%s://%s/subjects/%s-value/versions/", scheme, url, topicName);
		return HttpUtil.post(resource, schema, ContentTypes.SCHEMA_REGISTRY_JSON, Accepts.KAFKA_JSON);
	}

	public String delete(final String subjectName) {
		String resource = String.format("%s://%s/subjects/%s", scheme, url, subjectName);
		return HttpUtil.delete(resource, ContentTypes.SCHEMA_REGISTRY_JSON, null, null);
	}

	public String get(final String topicName) {
		String resource = String.format("%s://%s/subjects/%s-value/versions/1", scheme, url, topicName);
		return HttpUtil.get(resource);
	}

	public String list() {
		String resource = String.format("%s://%s/subjects/", scheme, url);
		return HttpUtil.get(resource);
	}

	public String setCompatibility(final String topicName, final String compatibilityMode) {
		return HttpUtil.put(String.format("%s://%s/config/%s", scheme, url, topicName),
				"application/vnd.schemaregistry.v1+json", "{\"compatibility\": \"" + compatibilityMode + "\"}");
	}
}
