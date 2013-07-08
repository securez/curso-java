package org.esquivo.weather.cdi;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.esquivo.downloader.Downloader;
import org.esquivo.downloader.HCDownloader;

public class DownloaderProducer {
    private static final Map<String,Object> params = new HashMap<String,Object>();
    
    static {
        params.put(HCDownloader.CONNECTION_TIMEOUT, 5000);
        params.put(HCDownloader.READ_TIMEOUT, 10000);
    }
    
    @Produces
    public Downloader createDownloader() {
        return new HCDownloader(params);
    }
    
    public void disposeDownloader(@Disposes Downloader downloader) {
        downloader.dispose();
    }

}
