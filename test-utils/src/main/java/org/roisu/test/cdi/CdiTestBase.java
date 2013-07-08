package org.roisu.test.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;

import org.apache.deltaspike.cdise.api.CdiContainer;
import org.apache.deltaspike.cdise.api.CdiContainerLoader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * The Class CdiTestBase is a base class to boot CDI container for tests.
 */
public abstract class CdiTestBase {
    
    /** The cdi container. */
    protected static CdiContainer cdiContainer;
    
    /** The bean manager. */
    protected static BeanManager beanManager;
    
    /**
     * Boot container.
     *
     * @throws Exception the exception
     */
    @BeforeClass
    public static final void bootContainer() throws Exception {
        cdiContainer = CdiContainerLoader.getCdiContainer();
        cdiContainer.boot();
        beanManager = cdiContainer.getBeanManager();
        
        // Start Application contexts
        cdiContainer.getContextControl().startContext(ApplicationScoped.class);        
    }
    
    /**
     * Shutdown container.
     *
     * @throws Exception the exception
     */
    @AfterClass
    public static void shutdownContainer() throws Exception {
        if (cdiContainer != null) {
            cdiContainer.shutdown();
            cdiContainer = null;
        }
    }
    
    /**
     * Inject and start contexts.
     *
     * @throws Exception the exception
     */
    @Before
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final void injectAndStartContexts() throws Exception {
        // Start contexts
        cdiContainer.getContextControl().startContext(RequestScoped.class);
        cdiContainer.getContextControl().startContext(SessionScoped.class);
        cdiContainer.getContextControl().startContext(ConversationScoped.class);
        
        CreationalContext creationalContext = beanManager.createCreationalContext(null);
        AnnotatedType annotatedType = beanManager.createAnnotatedType(this.getClass());
        InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
        injectionTarget.inject(this, creationalContext);
    }

    /**
     * Stop contexts.
     *
     * @throws Exception the exception
     */
    @After
    public final void stopContexts() throws Exception {
        if (cdiContainer != null) {
            cdiContainer.getContextControl().stopContext(ConversationScoped.class);
            cdiContainer.getContextControl().stopContext(SessionScoped.class);
            cdiContainer.getContextControl().stopContext(RequestScoped.class);
        }
    }

}
