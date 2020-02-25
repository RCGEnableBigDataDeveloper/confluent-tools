package com.rcggs.confluent.tools.topic;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import com.rcggs.confluent.tools.core.Context;
import com.rcggs.confluent.tools.util.HttpUtil;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

public class TopicClient {

	private String url;
	private String zookeeper;
	private Properties configuration;
	private String contentType;

	public static class Builder {

		private String url;
		private Properties configuration;
		private String contentType;
		private String zookeeper;

		public Builder withUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder withConfiguration(final Properties configuration) {
			this.configuration = configuration;
			return this;
		}

		public Builder withZookeeper(final String zookeeper) {
			this.zookeeper = zookeeper;
			return this;
		}

		public Builder withContentType(final String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder withScheme(String scheme) {
			return this;
		}

		public TopicClient build() {
			TopicClient topicClient = new TopicClient();
			topicClient.url = this.url;
			topicClient.configuration = this.configuration;
			topicClient.contentType = this.contentType;
			topicClient.zookeeper = this.zookeeper;
			return topicClient;
		}
	}

	private TopicClient() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(String zookeeper) {
		this.zookeeper = zookeeper;
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

	public void createTopic(final String topicName, final int partitions, final int replicationFactor) {
		createTopic(topicName, partitions, replicationFactor, new Properties(), false);
	}

	public void createTopic(final String topicName, final int partitions, final int replicationFactor,
			final Properties topicConfig, final boolean isSecure) {
		ZkUtils zkClient = getZkClient();
		AdminUtils.createTopic(zkClient, topicName, partitions, replicationFactor, topicConfig, null);
		zkClient.close();
	}

	public String get(final String topicName) {
		return HttpUtil
				.get(String.format("%s://%s/topics/%s", Context.get("confluent.rest.topic.scheme"), url, topicName));
	}

	public String list() {
		return HttpUtil.get(String.format("%s://%s/topics/", Context.get("confluent.rest.topic.scheme"), url));
	}

	public void delete(final String topicName) {
		ZkUtils zkClient = getZkClient();
		AdminUtils.deleteTopic(zkClient, topicName);
		zkClient.close();
	}

	private ZkUtils getZkClient() {
		int sessionTimeoutMs = 10 * 1000;
		int connectionTimeoutMs = 8 * 1000;
		ZkClient zkClient = new ZkClient(zookeeper, sessionTimeoutMs, connectionTimeoutMs, ZKStringSerializer$.MODULE$);
		boolean isSecureKafkaCluster = false;
		return new ZkUtils(zkClient, new ZkConnection(zookeeper), isSecureKafkaCluster);
	}
}
