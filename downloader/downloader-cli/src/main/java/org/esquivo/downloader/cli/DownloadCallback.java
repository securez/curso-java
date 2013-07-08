/**
 * 
 */
package org.esquivo.downloader.cli;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.esquivo.downloader.DownloaderCallback;

/**
 * @author woo
 * 
 */
public class DownloadCallback implements DownloaderCallback {
	//long startTime = -1;
	private ThreadLocal <Long> startTime = new ThreadLocal<Long>() {
        @Override 
        protected Long initialValue() {
            return System.nanoTime();
        }
	};

	@Override
    public void progress(File file, long totalSize, long readCount) {
		long currTime = System.nanoTime();
		
		if(readCount != 0) {
			float speed = readCount / ((float)(currTime - startTime.get()) / 1000000000L);
			
			System.out.print("File : " + file.getAbsolutePath() + 
					"  Size : " + FileUtils.byteCountToDisplaySize(totalSize) + 
					"  Readed : " + FileUtils.byteCountToDisplaySize(readCount) + 
					"  Speed : " + FileUtils.byteCountToDisplaySize((long) speed) + 
					"  StartTime : " + startTime.get() + "/s\r");
			

		} else {
			this.startTime.get();
			System.out.println("File : " + file.getAbsolutePath() + "  Size : " + totalSize + "  Readed : " + readCount + "  StartTime : " + startTime.get());
		}
    }

}
