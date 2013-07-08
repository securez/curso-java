package org.esquivo.weather.entities;

import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.roisu.test.jpa.JpaTestBase;

public class PersistenceTestBase extends JpaTestBase {

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("weather");
    }

}
