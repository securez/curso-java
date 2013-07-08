package org.esquivo.weather.ws;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.esquivo.weather.entities.Location;
import org.esquivo.weather.service.WeatherDao;


@Path("/location")
public class LocationRS {

    @Inject
    private WeatherDao dao;
   
    @GET
    @Produces({"application/json; charset=UTF-8", "text/xml; charset=UTF-8"})
    public List<Location> getLocations(@QueryParam("o") Integer offset, @QueryParam("s") Integer size) {
        return dao.getLocations(offset, size);
    }

    @Path("/{id}")
    @GET
    @Produces({"application/json; charset=UTF-8", "text/xml; charset=UTF-8"})
    public Location getLocationById(@PathParam("id") Integer id) {
        return dao.getLocationById(id);
    }
    

}
