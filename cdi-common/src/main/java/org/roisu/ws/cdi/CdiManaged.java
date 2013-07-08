package org.roisu.ws.cdi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sun.xml.ws.api.server.InstanceResolverAnnotation;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE)  
@Documented  
@InstanceResolverAnnotation(CdiInstanceResolver.class) 
public @interface CdiManaged {

}
