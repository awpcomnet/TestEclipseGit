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

public class MisfireExample {
	
	public static void main(String[] args) {
		new MisfireExample().run();
	}
	
	public void run(){
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = null;
		Date startTime = DateBuilder.evenMinuteDate(new Date());
		
		try {
			sched = sf.getScheduler();
			
			//#Job1 无限循环 3秒/次, 执行后延迟10秒
			JobDetail job = JobBuilder.newJob(StatefulDumbJob.class)
					.withIdentity("statefulJob1", "group1")
					.usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
					.build();
			SimpleTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(3)
							.repeatForever())
					.build();
			
			sched.scheduleJob(job, trigger);
			
			//#Job2 无限循环 3秒/次    执行后延迟10秒
			job = JobBuilder.newJob(StatefulDumbJob.class)
					.withIdentity("statefulJob2", "group1")
					.usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
					.build();
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group1")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(3)
							.repeatForever()
							.withMisfireHandlingInstructionNowWithExistingCount())
					.build();
			sched.scheduleJob(job, trigger);
			
			sched.start();
			
			Thread.sleep(600L * 1000L);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sched != null){
				try {
					sched.shutdown();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		
	}
}
