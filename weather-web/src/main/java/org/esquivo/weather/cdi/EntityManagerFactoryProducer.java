package org.esquivo.weather.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EntityManagerFactoryProducer {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerFactoryProducer.class);
    protected EntityManagerFactory emf;
    
    @PostConstruct
    public void createEntityManagerFactory() {
        LOG.debug("Create emf");
        emf = Persistence.createEntityManagerFactory("weather");
    }

    @PreDestroy
    public void closeEntityManagerFactory() {
        LOG.debug("Close emf");
        emf.close();
    }
    
    public @Produces EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
