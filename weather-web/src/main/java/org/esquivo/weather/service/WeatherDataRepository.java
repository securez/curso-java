package org.esquivo.weather.service;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.esquivo.weather.entities.WeatherData;

@Repository
public interface WeatherDataRepository extends EntityRepository<WeatherData, Long> {

}
