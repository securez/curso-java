package org.roisu.quartz.jobs;

import javax.inject.Inject;

import org.esquivo.weather.service.CrawlWeatherDataService;
import org.roisu.quartz.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Scheduled("0 */5 * * * ?")
public class WeatherJob implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherJob.class);

    @Inject
    CrawlWeatherDataService service;

    @Override
    public void run() {
        LOG.info("Begining task execution!!");
        service.execute();
        LOG.info("Task executed!!");
    }

}
