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

public class JobStateExample {
	
	public static void main(String[] args) {
		new JobStateExample().run();
	}
	
	public void run(){
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = null;
		
		try {
			sched = sf.getScheduler();
			
			//开启时间
			Date startTime = DateBuilder.evenMinuteDate(new Date());
			
			//#任务1  每10秒钟运行一次,运行5次
			JobDetail job1 = JobBuilder.newJob(ColorJob.class)
					.withIdentity("job1", "group1")
					.build();
			SimpleTrigger trigger1 = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder
							.simpleSchedule()
							.withIntervalInSeconds(10)
							.withRepeatCount(4))
					.build();
			job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
			job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
			
			sched.scheduleJob(job1, trigger1);
			
			//#任务2 每10秒运行一次，一共5次
			JobDetail job2 = JobBuilder.newJob(ColorJob.class)
					.withIdentity("job2", "group1")
					.build();
			SimpleTrigger trigger2 = TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group1")
					.startAt(startTime)
					.withSchedule(SimpleScheduleBuilder
							.simpleSchedule()
							.withIntervalInSeconds(10)
							.withRepeatCount(4))
					.build();
			job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
			job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
			
			sched.scheduleJob(job2, trigger2);
			
			sched.start();
			
			Thread.sleep(60L * 1000L);
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if(sched != null){
				try {
					sched.shutdown(true);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
		}
		
		
	}
}
