package org.esquivo.downloader;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

public class HCDownloaderTest {
	private static TestServer server;

	@BeforeClass
	public static void startServer() throws Exception {
		server = new TestServer(8080);
		server.getServerJetty().start();
	}

	@AfterClass
	public static void stopServer() throws Exception {
		server.getServerJetty().stop();
	}

	@Test(expected = SocketTimeoutException.class)
	public void TimeoutOptionsMustThrowException() throws IOException {
		// GIVEN : A download class with read timeout
		Map<String, Object> options = new HashMap<String, Object>();
		options.put(HCDownloader.CONNECTION_TIMEOUT, 500);
		options.put(HCDownloader.READ_TIMEOUT, 500);
		HCDownloader down = new HCDownloader(options);

		// WHEN : Download form valid URL
		try {
			down.download(new URL("http://localhost:8080/?sleep=1000"));
		} finally {
			down.dispose();
		}

		// THEN : Fails
		fail("Exception not throw");
	}

	@Test
	public void NullHttpEntityGetsNullFile() throws MalformedURLException,
			IOException {
		// GIVEN : A download class that will get a null entity
		HCDownloader down = new HCDownloader() {
			@Override
		    public File download(URL url, DownloaderCallback callback) throws IOException {
				HttpClient original = this.httpclient;
				this.httpclient = Mockito.mock(HttpClient.class);
				HttpResponse nullResponse = Mockito.mock(HttpResponse.class);
				
				Mockito.when(this.httpclient.execute(Mockito.any(HttpGet.class))).thenReturn(nullResponse);

				File file =  super.download(url, callback);
				
				this.httpclient = original;
				return file;
			}
		};

		// WHEN : Download form valid URL
		File file = down.download(new URL("http://localhost:8080/"));
		down.dispose();

		// THEN : Fails
		Assert.assertNull(file);
	}
}
