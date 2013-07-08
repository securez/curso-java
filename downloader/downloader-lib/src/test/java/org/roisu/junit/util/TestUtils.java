package org.roisu.junit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Assert;

public class TestUtils {
	private TestUtils() {
		
	}

	/**
	 * Verifies that a utility class is well defined.
	 * 
	 * @param clazz utility class to verify.
	 */
	public static void assertUtilityClassWellDefined(final Class<?> clazz)
	        throws NoSuchMethodException, InvocationTargetException,
	        InstantiationException, IllegalAccessException {
	    Assert.assertTrue("Class must be final", Modifier.isFinal(clazz.getModifiers()));
	    Assert.assertEquals("There must be only one constructor", 1, clazz.getDeclaredConstructors().length);
	    final Constructor<?> constructor = clazz.getDeclaredConstructor();
	    if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
	        Assert.fail("Constructor is not private");
	    }
	    constructor.setAccessible(true);
	    constructor.newInstance();
	    constructor.setAccessible(false);
	    for (final Method method : clazz.getMethods()) {
	        if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
	            Assert.fail("There exists a non-static method:" + method);
	        }
	    }
	}
}
