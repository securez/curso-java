package org.esquivo.weather.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.esquivo.weather.entities.MeteoGaliciaData;
import org.junit.Assert;
import org.junit.Test;


public class MeteoGaliciaParserTest {
    public static final String LOCAL_URL = "/meteogalicia.html";
    public static final String REMOTE_URL = "http://www.meteogalicia.es/web/index.action";
    

    
    @Test
    public void parseRemote() throws IOException {
        // GIVEN a parser
        Parser<List<MeteoGaliciaData>> parser = new MeteoGaliciaParser();
        
        // WHEN parsing data
        URL url = new URL(REMOTE_URL);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(10000);
        InputStream in = new BufferedInputStream(conn.getInputStream());
        List<MeteoGaliciaData> data = parser.parse(in);
        
        
        // THEN data must be ok
        Assert.assertEquals(7, data.size());
    }
    
    @Test
    public void parseLocal() {
        // GIVEN a parser
        Parser<List<MeteoGaliciaData>> parser = new MeteoGaliciaParser();
        
        // WHEN parsing data
        List<MeteoGaliciaData> data = parser.parse(this.getClass().getResourceAsStream(LOCAL_URL)); 
        
        // THEN data must be ok
        Assert.assertEquals(7, data.size());
    }
   
}
