package org.esquivo.downloader;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.input.CountingInputStream;

public class DownloadCountingInputStream extends CountingInputStream {
	
	private DownloaderCallback callback;
	private File file;
	private long totalSize;

	public DownloadCountingInputStream(InputStream in) {
	    super(in);
    }
	
	public DownloadCountingInputStream(InputStream in, DownloaderCallback callback, File file, long totalSize) {
	    super(in);
	    this.callback = callback;
	    this.file = file;
	    this.totalSize = totalSize;
    }

	@Override
	protected void afterRead(int n) {
		super.afterRead(n);
		if (callback != null) {
			callback.progress(file, totalSize, this.getByteCount());
		}
	}

}
