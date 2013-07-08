package org.esquivo.weather.ws;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.esquivo.weather.entities.WeatherData;
import org.esquivo.weather.service.WeatherDao;
import org.roisu.ws.cdi.CdiManaged;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebService
//@SOAPBinding(style = Style.DOCUMENT)
//@CdiManaged
//@RequestScoped
public class WeatherDataWS implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherDataWS.class);

    @Inject
    private WeatherDao service;
   
    
//    @WebMethod
    public List<WeatherData> getWeatherData() {
        return service.getWeatherData();
    }
    
//    @WebMethod
    public Long save(WeatherData data) {
        LOG.debug("save : " + data);
        service.persist(data);
        return data.getId();
    }
}
