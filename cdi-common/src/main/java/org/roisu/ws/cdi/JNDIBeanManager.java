package org.roisu.ws.cdi;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDIBeanManager {
    private BeanManager beanManager;
 
    public static JNDIBeanManager getInstance() {
        try {
            BeanManager manager = InitialContext.doLookup("java:comp/env/BeanManager");
            JNDIBeanManager jndiManager = new JNDIBeanManager();
            jndiManager.beanManager = manager;
            return jndiManager;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
 
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(type));
        CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, type, creationalContext);
    }
}
