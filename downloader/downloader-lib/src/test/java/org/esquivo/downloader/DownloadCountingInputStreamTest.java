package org.esquivo.downloader;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class DownloadCountingInputStreamTest {

	@Test
	public void WithoutCallbackNeedWork() throws IOException {
		byte str[] = "Hola mundo cruel!!!".getBytes();
		
		// GIVEN : A DownloadCountingString without callback
		InputStream in = new ByteArrayInputStream(str);
		OutputStream out = new ByteArrayOutputStream();
		DownloadCountingInputStream dcin = new DownloadCountingInputStream(in);
		
		// WHEN : read data from input stream
		IOUtils.copy(dcin, out);
		
		// THEN : All the bytes must be counted
		assertEquals(str.length, dcin.getByteCount());
	}



}
