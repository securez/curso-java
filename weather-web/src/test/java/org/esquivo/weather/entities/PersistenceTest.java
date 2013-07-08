package org.esquivo.weather.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class PersistenceTest {

    
    @Test
    public void createPersistenceContext() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("weather");
        EntityManager em = emf.createEntityManager();
        
        em.close();
    }
}
