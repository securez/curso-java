package org.roisu.quartz;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzExtension implements Extension {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzExtension.class);
    private static BeanManager beanManager;
    private static Scheduler scheduler;

    public void scheduleJob(@Observes ProcessAnnotatedType<? extends Runnable> pat) throws SchedulerException {
        AnnotatedType<? extends Runnable> t = pat.getAnnotatedType();
        Scheduled schedule = t.getAnnotation(Scheduled.class);
        if (schedule == null) {
            // no scheduled job, ignoring this class
            return;
        }

        Class<? extends Runnable> jobClass = t.getJavaClass().asSubclass(Runnable.class);
        if (jobClass == null) {
            LOG.error("Can't schedule job " + t);
            return;
        }
        JobDetail job = JobBuilder.newJob(CdiJob.class).usingJobData(CdiJob.JOB_CLASS_NAME, jobClass.getName()).build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(schedule.value())).build();
        scheduler.scheduleJob(job, trigger);
    }

    public void initScheduler(@Observes BeforeBeanDiscovery event) throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    public void startScheduler(@Observes AfterDeploymentValidation event, BeanManager bm) {
        beanManager = bm;
        try {
            scheduler.start();
            LOG.info("Started scheduler.");
        } catch (SchedulerException se) {
            throw new RuntimeException(se);
        }
    }

    public void shutdownScheduler(@Observes BeforeShutdown event) {
        try {
            scheduler.shutdown(true);
        } catch (SchedulerException se) {
            throw new RuntimeException(se);
        }
    }

    public static BeanManager getBeanManager() {
        return beanManager;
    }
}
