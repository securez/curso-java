package org.esquivo.weather.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.esquivo.weather.entities.Forecast;
import org.esquivo.weather.entities.Location;
import org.esquivo.weather.entities.WeatherData;
import org.roisu.cdi.interceptor.Profile;

@RequestScoped
@Profile
public class WeatherDao {
    
    @Inject
    EntityManager em;
    
    public List<WeatherData> getWeatherData() {       
        return em.createNamedQuery("WeatherData.getAll", WeatherData.class).getResultList();
    }
    
    public List<Location> getLocations() {       
        return getLocations(null, null);
    }

    public List<Location> getLocations(Integer offset, Integer size) {       
        TypedQuery<Location> q = em.createNamedQuery("Location.getAll", Location.class);
        
        if(offset != null) {
            q.setFirstResult(offset);
        }
        if(size != null) {
            q.setMaxResults(size);
        }
        
        return q.getResultList();
    }
    
    public Location getLocationByTownProvinceRegionCountry(String town, String province, String region, String country) {
        TypedQuery<Location> q = em.createNamedQuery("Location.getByTownProvinceRegionCountry", Location.class);
        q.setParameter("town", town);
        q.setParameter("province", province);
        q.setParameter("region", region);
        q.setParameter("country", country);
        
        try {
            return q.getSingleResult();
        } catch (PersistenceException e){
            return null;
        }
    }
    
    public Location getLocationById(Integer id) {
        return em.find(Location.class, id);
    }
    
    public List<WeatherData> getWeatherByLocation(Location location) {
        TypedQuery<WeatherData> q = em.createNamedQuery("WeatherData.getByLocation", WeatherData.class);
        q.setParameter("location", location);
        
        return q.getResultList();
    }
    
    public List<WeatherData> getWeatherByForecast(Forecast forecast) {
        TypedQuery<WeatherData> q = em.createNamedQuery("WeatherData.getByForecast", WeatherData.class);
        q.setParameter("forecast", forecast);
        
        return q.getResultList();
    }
    
    @Transactional
    public <T> void persist(T data) {
        em.persist(data);
    }
}
