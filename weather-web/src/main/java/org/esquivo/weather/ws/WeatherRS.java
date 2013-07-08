package org.esquivo.weather.ws;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.esquivo.weather.entities.Location;
import org.esquivo.weather.entities.WeatherData;
import org.esquivo.weather.service.WeatherDao;


@Path("/weather")
public class WeatherRS {

    @Inject
    private WeatherDao dao;
   
    @GET
    @Produces({"application/json; charset=UTF-8", "text/xml; charset=UTF-8"})
    public List<WeatherData> getData(@QueryParam("loc") Integer id) {
        if (id == null) {
            return dao.getWeatherData();
        } else {
            Location loc = dao.getLocationById(id);
            return dao.getWeatherByLocation(loc);
        }
        
    }

    

}
