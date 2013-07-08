package org.esquivo.downloader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.junit.Test;
import org.roisu.junit.util.TestUtils;

public class UtilsTest {
	
	@Test
	public void UtilsWellDefined() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		// GIVEN : A Utils class
		
		// WHEN :
		
		// THEN : Must be well defined
		TestUtils.assertUtilityClassWellDefined(Utils.class);
	}
	
	@Test
	public void WriteToTempFile() throws IOException {
		byte str[] = "Hola mundo cruel!!!".getBytes();
		
		// GIVEN : A InputStream
		InputStream in = new ByteArrayInputStream(str);
		
		// WHEN : Write it to a temp file
		final File file = Utils.writeToTempFile(in);
		
		// THEN : File must be created
		Assert.assertTrue(file.exists());
		Assert.assertEquals(str.length, file.length());
	}

}
