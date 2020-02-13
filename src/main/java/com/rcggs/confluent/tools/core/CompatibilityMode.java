package com.rcggs.confluent.tools.core;

public final class CompatibilityMode {

	/**
	 * Delete fields Add optional fields Last version Consumers
	 **/
	public static final String BACKWARD = "BACKWARD";
	/**
	 * Delete fields Add optional fields All previous versions Consumers
	 **/
	public static final String BACKWARD_TRANSITIVE = "BACKWARD_TRANSITIVE";

	/**
	 * Add fields Delete optional fields Last version
	 **/
	public static final String FORWARD = "FORWARD";

	/**
	 * Add fields /**Delete optional fields All previous versions
	 **/
	public static final String FORWARD_TRANSITIVE = "FORWARD_TRANSITIVE";

	/**
	 * Add optional fields Delete optional fields Last version
	 **/
	public static final String FULL = "FULL";

	/**
	 * Add optional fields Delete optional fields All previous versions Any
	 * order
	 **/
	public static final String FULL_TRANSITIVE = "FULL_TRANSITIVE";

	/** All changes are accepted **/
	public static final String NONE = "NONE";

}
