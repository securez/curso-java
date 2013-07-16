package org.esquivo.weather.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import org.apache.deltaspike.jpa.impl.transaction.context.TransactionContext;
import org.esquivo.weather.entities.Location;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.roisu.test.cdi.CdiJunit4Runner;

@RunWith(CdiJunit4Runner.class)
public class LocationRepositoryTest {

    @Inject
    LocationRepository locationRepository;
    
    @Inject
    TransactionContext txContext;
    
    @Inject
    EntityManager em;
           
    @Test
    @Transactional
    public void testCrawlWeatherDataService() {
        Location loc = new Location("Vigo", "Pontevedra", "Galicia", "Spain", null, null);
        locationRepository.saveAndFlush(loc);

        Assert.assertNotNull(txContext);
        Assert.assertTrue(locationRepository.count() > 0);

        System.out.println("Active : " + txContext.isActive());
    }    
}
