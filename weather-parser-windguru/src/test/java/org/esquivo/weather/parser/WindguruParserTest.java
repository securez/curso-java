package org.esquivo.weather.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.esquivo.weather.entities.Forecast;
import org.junit.Assert;
import org.junit.Test;


public class WindguruParserTest {
    public static final String LOCAL_URL = "/windguru_spot.html";
    public static final String REMOTE_URL = "http://www.windguru.cz/${lang}/print.php?typ=spot&sn=${spot}&go=1&vs=1&wj=kmh&tj=c&odh=0&doh=24&fhours=180";

    @Test
    public void parseRemote() throws IOException {
        // GIVEN a parser
        URL url = new URL(REMOTE_URL.replace("${lang}", "int").replace("${spot}", "153771"));
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        InputStream in = new BufferedInputStream(conn.getInputStream());
        Parser<List<Forecast>> parser = new WindguruParser();
        
        // WHEN parsing data
        List<Forecast> data = parser.parse(in); 
        
        // THEN data must be ok
        System.out.print("Data : " + data);
        Assert.assertEquals(1, data.size());
        // Location can't be extracted from JSON, review if id_spot is candidate to Location.
        // Assert.assertNotNull("Location can't be null", data.get(0).getLocation());
        Assert.assertNotNull("Provider can't be null", data.get(0).getProvider());
        Assert.assertNotNull("Time can't be null", data.get(0).getTime());
        Assert.assertTrue("WeatherData can't be empty", data.get(0).getWeatherData().size() > 0);
        Assert.assertEquals(61, data.get(0).getWeatherData().size());
    }
    
    @Test
    public void parseLocal() {
        // GIVEN a parser
        Parser<List<Forecast>> parser = new WindguruParser();
        
        // WHEN parsing data
        List<Forecast> data = parser.parse(this.getClass().getResourceAsStream(LOCAL_URL)); 
        
        // THEN data must be ok
        System.out.print("Data : " + data);
        Assert.assertEquals(1, data.size());
        // Location can't be extracted from JSON, review if id_spot is candidate to Location.
        // Assert.assertNotNull("Location can't be null", data.get(0).getLocation());
        Assert.assertNotNull("Provider can't be null", data.get(0).getProvider());
        Assert.assertNotNull("Time can't be null", data.get(0).getTime());
        Assert.assertTrue("WeatherData can't be empty", data.get(0).getWeatherData().size() > 0);
        Assert.assertEquals(61, data.get(0).getWeatherData().size());
    }
   
}
