package com.rcggs.confluent.tools.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.commons.io.IOUtils;

public class AvroUtil {

	public static void serialize(final GenericRecord record, final File in, final String file) throws IOException {
		serialize(record, in, new FileOutputStream(new File(file)));
	}

	public static void serialize(final GenericRecord record, final File in, final Path path) throws IOException {
		serialize(record, in, new FileOutputStream(path.toFile()));
	}

	public static void serialize(final GenericRecord record, final File in, final File file) throws IOException {
		serialize(record, in, new FileOutputStream(file));
	}

	public static void serialize(final GenericRecord record, final File in, final OutputStream out) throws IOException {
		Schema schema = new Schema.Parser().parse(in);
		serialize(record, schema, out);
	}

	public static void serialize(final GenericRecord record, final InputStream in, final OutputStream out)
			throws IOException {
		Schema schema = new Schema.Parser().parse(in);
		serialize(record, schema, out);
	}

	public static void serialize(final GenericRecord record, final Schema schema, final OutputStream out)
			throws IOException {
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
		dataFileWriter.create(schema, out);
		dataFileWriter.append(record);
		dataFileWriter.close();
	}

	public static void deserilaize(final File in, final String file) throws IOException {
		deserilaize(in, new File(file));
	}

	public static void deserilaize(final File in, final Path path) throws IOException {
		deserilaize(in, path.toFile());
	}

	public static void deserilaize(final InputStream in, final File file) throws IOException {
		final File tempFile = File.createTempFile("user", "avsc");
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
			deserilaize(tempFile, file);
		}
	}

	public static void deserilaize(final File in, final File file) throws IOException {
		DataFileReader<GenericRecord> dataFileReader = null;
		try {
			Schema schema = new Schema.Parser().parse(in);
			DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
			dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
			GenericRecord user = null;
			while (dataFileReader.hasNext())
				user = dataFileReader.next(user);
		} finally {
			if (dataFileReader != null)
				dataFileReader.close();
		}
	}
}
