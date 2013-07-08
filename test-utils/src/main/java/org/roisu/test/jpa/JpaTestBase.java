package org.roisu.test.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

public abstract class JpaTestBase {
	protected static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;

    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }
    
    @Before
    public void createEntityManager() {
        em = emf.createEntityManager();
        // Start a new transaction
        tx = em.getTransaction();
        if(!tx.isActive()) {
        	tx.begin();
        }
    }

    @After
    public void closeEntityManager() {
    	if(tx.isActive()) {
    		if(tx.getRollbackOnly()) {
    			tx.rollback();
    		} else {
    			tx.commit();
    		}
    	}
    	em.close();
    }
}
