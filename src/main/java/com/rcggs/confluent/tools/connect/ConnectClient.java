package com.rcggs.confluent.tools.connect;

import java.util.Properties;

import com.rcggs.confluent.tools.core.Accepts;
import com.rcggs.confluent.tools.core.ContentTypes;
import com.rcggs.confluent.tools.util.HttpUtil;

public class ConnectClient {

	private String url;
	private String scheme;
	private Properties configuration;
	private String contentType;

	public static class Builder {

		private String url;
		private String scheme;
		private Properties configuration;
		private String contentType;

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

		public ConnectClient build() {
			ConnectClient connectClient = new ConnectClient();
			connectClient.url = this.url;
			connectClient.configuration = this.configuration;
			connectClient.contentType = this.contentType;
			connectClient.scheme = this.scheme;
			return connectClient;
		}
	}

	private ConnectClient() {
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

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String createConnector(final String connector) {
		return HttpUtil.post(String.format("%s://%s/connectors", scheme, url), connector, ContentTypes.JSON,
				Accepts.KAFKA_JSON);
	}
}
