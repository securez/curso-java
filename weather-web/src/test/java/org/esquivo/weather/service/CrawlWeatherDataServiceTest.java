package org.esquivo.weather.service;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.roisu.test.cdi.CdiJunit4Runner;

@RunWith(CdiJunit4Runner.class)
public class CrawlWeatherDataServiceTest {

    @Inject
    CrawlWeatherDataService service;
    
    @Inject
    WeatherDao dao;
        
    @Test
    public void testCrawlWeatherDataService() {
        service.execute();
        Assert.assertTrue(dao.getWeatherData().size() > 0);
        Assert.assertTrue(dao.getLocations().size() > 0);
    }    
}
