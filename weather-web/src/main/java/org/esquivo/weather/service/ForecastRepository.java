package org.esquivo.weather.service;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.esquivo.weather.entities.Forecast;

@Repository
public interface ForecastRepository extends EntityRepository<Forecast, Integer> {

}
