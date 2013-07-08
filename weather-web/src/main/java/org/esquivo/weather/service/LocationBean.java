package org.esquivo.weather.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.esquivo.weather.entities.Location;
import org.esquivo.weather.entities.WeatherData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class LocationBean implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(LocationBean.class);
    
    private static final long serialVersionUID = 1L;

    private Integer selected;
    private List<Location> locations;
    private List<WeatherData> weatherData;
    
    @Inject
    WeatherDao dao;
    
    @PostConstruct
    public void initList() {
        this.locations = dao.getLocations();
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        LOG.debug("Selected location : {} ", selected);
        this.selected = selected;
        this.weatherData = null;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<WeatherData> getWeatherData() {
        if(weatherData == null && selected != null) {
            Location location = dao.getLocationById(this.selected);
            this.weatherData = dao.getWeatherByLocation(location);
        }
        
        return weatherData;
    }
}
