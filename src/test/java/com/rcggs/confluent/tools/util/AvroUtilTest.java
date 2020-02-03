package com.rcggs.confluent.tools.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.log4j.Logger;

import com.rcggs.confluent.tools.core.Context;

import junit.framework.TestCase;

public class AvroUtilTest extends TestCase {

	private static final Logger logger = Logger.getLogger(Context.class);

	private String path;

	@Override
	protected void setUp() throws Exception {
		path = System.getProperty("java.io.tmpdir");
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAvro() {
		try {
			
			Schema schema = new Schema.Parser().parse(getClass().getResourceAsStream("/user.avsc"));
			GenericRecord record = new GenericData.Record(schema);
			record.put("name", "test-name");
			AvroUtil.serialize(record, getClass().getResourceAsStream("/user.avsc"),
					new FileOutputStream(path + "/user.avro"));
			AvroUtil.deserilaize(getClass().getResourceAsStream("/user.avsc"), new File(path + "/user.avro"));
			assertNotNull(record);
			logger.debug(record);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
