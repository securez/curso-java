package org.esquivo.weather.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceWindGuruDataTest extends PersistenceTestBase {
    
    private static final Logger LOG = LoggerFactory.getLogger(PersistenceWindGuruDataTest.class);    

    @Test
    public void testInsertData() throws ParseException {
        
        Forecast forecast = new Forecast(new Date(), new Location("Santiaguito", "A Coruña", "Galicia", "España", null, null), Provider.WINDGURU);
        
        
        WindGuruData data = new WindGuruData();
        forecast.addWeatherData(data);
        
        data.setStartTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("26/06/2013 18:00"));
        data.setEndTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("26/06/2013 21:00"));
        
        // Es posible que de problemas al no haber persistido todavia la localización en BD
        data.setForecast(forecast);
        data.setMinTemp(15);
        data.setMaxTemp(20);
        data.setWindSpeed(21);
        data.setWindDirection(30);
        data.setPrecipitation(22);
        data.setCloudiness(23);
        data.setVisibility(2400);
        data.setHumidity(25);
        
        em.persist(forecast);
        em.flush();
        
        LOG.debug("WeatherData : " + data);
    }


}
