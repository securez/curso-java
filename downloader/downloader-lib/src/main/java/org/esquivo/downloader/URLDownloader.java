package org.esquivo.downloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The Class BufferedURLDownloader.
 */
public class URLDownloader implements Downloader {
	private int conectionTimeout = 0;
	private int readTimeout = 0;
	
	public URLDownloader() {
		
	}

	public URLDownloader(int conectionTimeout, int readTimeout) {
	    this.conectionTimeout = conectionTimeout;
	    this.readTimeout = readTimeout;
    }
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esquivo.downloader.Downloader#download(java.net.URL, int)
	 */
	@Override
	public File download(URL url) throws IOException {
		return this.download(url, null);
	}

	@Override
    public File download(URL url, DownloaderCallback callback) throws IOException {
		int connectionTimeout = this.conectionTimeout;
		int readTimeout = this.readTimeout;
		
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(connectionTimeout);	
		conn.setReadTimeout(readTimeout);
		
		final File tempFile = File.createTempFile("urldownloader-", null);
		
		InputStream in;
		
		if (callback != null) {
			long contentLength = conn.getContentLength();
			
			// TODO : Review Stream leak
			in = new DownloadCountingInputStream(conn.getInputStream(), callback, tempFile, contentLength);
			callback.progress(tempFile, contentLength, 0);
		} else {
			in = conn.getInputStream();
		}
		
		Utils.writeToFile(in, tempFile);
		
		return tempFile;
    }

    @Override
    public void dispose() {
        
    }

}