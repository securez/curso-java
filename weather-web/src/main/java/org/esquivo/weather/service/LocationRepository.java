package org.esquivo.weather.service;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.esquivo.weather.entities.Location;

@Repository
public interface LocationRepository extends EntityRepository<Location, Integer> {

}
