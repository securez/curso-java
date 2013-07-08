package org.esquivo.weather.entities;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceLocationTest extends PersistenceTestBase {

    private static final Logger LOG = LoggerFactory.getLogger(PersistenceLocationTest.class);    
    
    @Test(expected = ConstraintViolationException.class)
    public void createLocation() {
        Location loc = new Location(null, "Pontevedra", "Galicia", "España", null, null);
        
        em.persist(loc);
        em.flush();
        
        LOG.debug("Location : " + loc);
    }
    
}
