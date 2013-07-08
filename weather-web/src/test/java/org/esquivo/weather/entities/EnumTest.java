package org.esquivo.weather.entities;

import junit.framework.Assert;

import org.esquivo.weather.entities.WindDirection;
import org.junit.Test;

public class EnumTest {
    
    @Test
    public void getDegreesFromWindDirection() {
        String dir = "NW";
        
        WindDirection wd = WindDirection.valueOf(dir);
        
        Assert.assertEquals(315, wd.getDegrees());
    }
    
    @Test
    public void getAllDirections() {
        for(WindDirection value : WindDirection.values()) {
            System.out.println(value + " : " + value.getDegrees());
        }
    }
}
