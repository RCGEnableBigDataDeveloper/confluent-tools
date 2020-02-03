package com.rcggs.confluent.tools.core;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Context {

	private static final Logger logger = Logger.getLogger(Context.class);

	private static Properties properties;

	public static String get(final String key) {
		return properties.getProperty(key);
	}

	public static Properties getProperties() {
		return properties;
	}

	static {
		properties = new Properties();
		try {
			properties.load(Context.class.getResourceAsStream("/confluent-tools.properties"));
		} catch (IOException e) {
			throw new IllegalStateException("cannot find or load confluent_tools properties");
		}
	}
}