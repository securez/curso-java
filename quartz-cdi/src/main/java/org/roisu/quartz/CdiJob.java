package org.roisu.quartz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Inject;

import org.jboss.weld.context.bound.BoundRequestContext;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CdiJob implements org.quartz.Job {
    private static final Logger LOG = LoggerFactory.getLogger(CdiJob.class);

    public final static String JOB_CLASS_NAME = "CDI_JOB_CLASS_NAME";
    
    @Inject
    BoundRequestContext context;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO : Add exception handling
        JobDataMap jobData = context.getJobDetail().getJobDataMap();
        String className = jobData.getString(JOB_CLASS_NAME);
        Class<? extends Runnable> jobClass;
        
        LOG.debug("Creating Job for {}", className);
        
        try {
            jobClass = Class.forName(className).asSubclass(Runnable.class);
        } catch (ClassNotFoundException e) {
            throw new JobExecutionException(e);
        }
        
        BeanManager bm = QuartzExtension.getBeanManager();
        Set<Bean<?>> jobBeans = bm.getBeans(jobClass);
        Bean<Runnable> jobBean = (Bean<Runnable>) bm.resolve(jobBeans);
        CreationalContext<Runnable> c = bm.createCreationalContext(jobBean);
        Runnable job = (Runnable) bm.getReference(jobBean, Runnable.class, c);
        
        CreationalContext creationalContext = bm.createCreationalContext(null);
        AnnotatedType annotatedType = bm.createAnnotatedType(this.getClass());
        InjectionTarget injectionTarget = bm.createInjectionTarget(annotatedType);
        injectionTarget.inject(this, creationalContext);
                
        Map<String, Object> requestDataStore = Collections
                .synchronizedMap(new HashMap<String, Object>());
        this.context.associate(requestDataStore);
        this.context.activate();
        
        try {
            job.run();
        } finally {
            jobBean.destroy(job, c);
            
            this.context.invalidate();
            this.context.deactivate();
                        
            injectionTarget.dispose(this);
            creationalContext.release();
            c.release();
        }
    }

}