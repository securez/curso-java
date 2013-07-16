package org.esquivo.weather.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.esquivo.downloader.Downloader;
import org.esquivo.weather.entities.Forecast;
import org.esquivo.weather.entities.Location;
import org.esquivo.weather.entities.MeteoGaliciaData;
import org.esquivo.weather.entities.Provider;
import org.esquivo.weather.entities.WeatherData;
import org.esquivo.weather.parser.MeteoGaliciaParser;
import org.esquivo.weather.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class CrawlWeatherDataService {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlWeatherDataService.class);

    @Inject
    @ConfigProperty(name = "meteogalicia.url")
    private String url;
    
    @Inject
    private WeatherDao service;
    
    @Inject
    private Downloader downloader;
    
    public void execute() {
        try {
            LOG.info("Downloading weather data from MeteoGalicia ...");
            File file = downloader.download(new URL(url));
            LOG.info("Download finished");
            
            Parser<List<MeteoGaliciaData>> parser = new MeteoGaliciaParser();
            
            LOG.info("Parsing MeteoGalicia data ...");
            List<MeteoGaliciaData> list = parser.parse(new FileInputStream(file));
            LOG.info("Parse finished");
            
            Map<String, String> provinces = new HashMap<String, String>();
            provinces.put("A Coruña", "A Coruña");
            provinces.put("Ferrol", "A Coruña");
            provinces.put("Lugo", "Lugo");
            provinces.put("Ourense", "Ourense");
            provinces.put("Pontevedra", "Pontevedra");
            provinces.put("Santiago de Compostela", "A Coruña");
            provinces.put("Vigo", "Pontevedra");
            
            for (MeteoGaliciaData data : list) {
                LOG.info("Searching location in DB ...");
                Location loc = service.getLocationByTownProvinceRegionCountry(data.getTown(), provinces.get(data.getTown()), "Galicia", "España");
                if (loc == null) {
                    LOG.info("Location not found in DB");
                    loc = new Location(data.getTown(), provinces.get(data.getTown()), "Galicia", "España", null, null);
                    LOG.info("Added new location to DB : {}", loc);
                }
                
                // TODO : See what happen with previous data
                Forecast forecast = new Forecast(data.getDate(), loc, Provider.METEO_GALICIA);
                
                WeatherData wdata = new WeatherData();
                forecast.addWeatherData(wdata);
                wdata.setForecast(forecast);
                
                wdata.setMaxTemp(data.getMaxTemp());
                wdata.setMinTemp(data.getMinTemp());
                wdata.setStartTime(data.getDate());
                
                LOG.info("Adding new MeteoGalicia data for {} to DB", data.getTown());
                service.persist(forecast);
                LOG.info("New MeteoGalicia data for {} added to DB", data.getTown());
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        
        
    }
}
