package org.roisu.cdi.interceptor;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor @Profile
public class ProfileInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ProfileInterceptor.class);
    
    @AroundInvoke
    public Object profileMethod(InvocationContext ctx) throws Exception {
        final String clazz = ctx.getTarget().getClass().getName();
        final String method = ctx.getMethod().getName();
        
        long start = System.currentTimeMillis();
        Object result = ctx.proceed();
        long end = System.currentTimeMillis();
        
        LOG.info("Profiled method : " + clazz + "." + method + "() executed in " +
                (((float)(end - start)) / 1000) + " s.");
        
        
        return result;
    }

}
