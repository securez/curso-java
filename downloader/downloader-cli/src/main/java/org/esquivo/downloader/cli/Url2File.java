/**
 * 
 */
package org.esquivo.downloader.cli;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.esquivo.downloader.Downloader;
import org.esquivo.downloader.DownloaderCallback;

/**
 * @author woo
 * 
 */
public class Url2File implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	private final String url;
	private final Downloader fDown;
	private DownloaderCallback call = null;

	public Url2File(String url, Downloader fDown) {
		this.url = url;
		this.fDown = fDown;
	}
	
	public Url2File(String url, Downloader fDown, DownloaderCallback call) {
		this(url, fDown);
		this.call = call;
	}	
	

	@Override
	public void run() {
		try {
			File file = fDown.download(new URL(url), call);
			System.out.println("Path URL downloaded: " + file.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Troubles downloading URL : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
