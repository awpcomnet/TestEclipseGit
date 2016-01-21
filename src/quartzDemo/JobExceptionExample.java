package quartzDemo;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobExceptionExample {
	
	public static void main(String[] args) {
		LOG.info("---------------1-----------------");
		LOG.info("---------------2-----------------");
		new JobExceptionExample().run();
	}
	
	
	public void run(){
		LOG.info("---------------start-----------------");
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = null;
		Date startTime = DateBuilder.evenMinuteDate(new Date());
		
		try {
			sched = sf.getScheduler();
			
			//#JOB1 无限循环  3秒/次  
			JobDetail job =JobBuilder.newJob(BadJob1.class)
					.withIdentity("badJob1", "group1")
					.build();
			SimpleTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(3)
							.repeatForever())
					.build();
			
			Date ft = sched.scheduleJob(job, trigger);
			
			//#JOB2无限循环 3秒/次
			job = JobBuilder.newJob(BadJob2.class)
					.withIdentity("badJob2", "group2")
					.build();
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group2")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(3)
							.repeatForever())
					.build();
			ft = sched.scheduleJob(job, trigger);
			
			sched.start();
			
			Thread.sleep(60L * 1000L);
			
			LOG.info("---------------end-----------------");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sched != null){
				try {
					sched.shutdown(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(JobExceptionExample.class);
}
