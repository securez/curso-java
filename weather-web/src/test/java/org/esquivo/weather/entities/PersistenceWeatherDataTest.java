package org.esquivo.weather.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceWeatherDataTest extends PersistenceTestBase {
    
    private static final Logger LOG = LoggerFactory.getLogger(PersistenceWeatherDataTest.class);    

    @Test
    public void testInsertData() throws ParseException {
        
        Location loc = new Location("Viguito", "Pontevedra", "Galicia", "España", null, null);
        
        Forecast forecast = new Forecast(new Date(), loc, Provider.METEO_GALICIA);
        
        WeatherData data = new WeatherData();
        forecast.addWeatherData(data);
        data.setForecast(forecast);
        
        
        data.setTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("26/06/2013 18:00"));
        
        data.setMinTemp(20);
        data.setMaxTemp(25);
        data.setWindSpeed(11);
        data.setWindDirection(20);
        data.setPrecipitation(12);
        data.setCloudiness(13);
        data.setVisibility(1400);
        data.setHumidity(15);
        
        em.persist(forecast);
        em.flush();
        
        closeEntityManager();
        
        LOG.debug("WeatherData : " + data);
        
        createEntityManager();
        
        WeatherData data2 = em.find(WeatherData.class, data.getId());
        Assert.assertEquals(data.getTime(), data2.getTime());
        Assert.assertEquals(data, data2);
    }


}
