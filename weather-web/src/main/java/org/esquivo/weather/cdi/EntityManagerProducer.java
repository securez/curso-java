package org.esquivo.weather.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EntityManagerProducer {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerFactoryProducer.class);
    
    @Inject
    EntityManagerFactory emf;
    
    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        LOG.debug("Create em");
        return emf.createEntityManager();
    }
    
    public void closeEntityManager(@Disposes EntityManager em) {
        LOG.debug("Close em");
        if (em.isOpen()) {
            em.close();
        }
    }
}
