package org.roisu.test.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class CdiJunit4Runner extends BlockJUnit4ClassRunner {
    private final Class<?> clazz;
    private CdiContainer cdiContainer;
    private BeanManager beanManager;

    /**
     * Runs the class passed as a parameter within the container.
     * @param klass to run
     * @throws InitializationError if anything goes wrong.
     */
    public CdiJunit4Runner(final Class<Object> klass) throws InitializationError {
        super(klass);
        this.clazz = klass;
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        beanManager = cdiContainer.getBeanManager();
        
        // Start Application contexts
        cdiContainer.getContextControl().startContext(ApplicationScoped.class);
    }


    @Override
    protected Object createTest() throws Exception {
        final Object test = getBean(clazz);
        return test;
    }
    
    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        // Start Contexts
        cdiContainer.getContextControl().startContext(RequestScoped.class);
        cdiContainer.getContextControl().startContext(SessionScoped.class);
        cdiContainer.getContextControl().startContext(ConversationScoped.class);
        
        // Runt Test
        super.runChild(method, notifier);
        
        // Stop contexts
        cdiContainer.getContextControl().stopContext(ConversationScoped.class);
        cdiContainer.getContextControl().stopContext(SessionScoped.class);
        cdiContainer.getContextControl().stopContext(RequestScoped.class);
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getBean(Class<T> type) {
        Bean<T> bean = (Bean<T>) beanManager.resolve(beanManager.getBeans(type));
        CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, type, creationalContext);
    }
}