package quartzDemo;

import java.util.List;
import java.util.Scanner;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


/**
 * @Description: 测试
 * @author 王航
 * @date 2015年12月18日 下午2:29:55
 */
public class SimpleExample {
	
	//创建调度器工厂
	private static SchedulerFactory sf = new StdSchedulerFactory();
	
	public static void main(String[] args) throws Exception {
		SimpleExample se = new SimpleExample();
		se.run();
	}
	
	public void run() throws SchedulerException{
		//调度器
		Scheduler sched = null;
		try {
			//获取调度器
			sched = sf.getScheduler();
			
			String str = "quartzDemo.HelloJob";
			Class HelloJob = Class.forName(str);
			
			//#任务1 每20秒执行1次
			//创建任务
			JobDetail job = JobBuilder.newJob(HelloJob)
		             .withIdentity("HelloJob", "group1")
		             .build();
			//创建CronTrigger触发器
			CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger1", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
					.build();
			
			//将任务和触发器关联调度器
			sched.scheduleJob(job, trigger);
			
			//#任务2 每一分钟过去15秒后执行一次
			job = JobBuilder.newJob(HelloJob)
					.withIdentity("HelloJob2", "group1")
					.build();
			
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger2", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 * * * ?"))
					.build();
			sched.scheduleJob(job, trigger);
			
			//#任务3 在早上8点到晚上8点之间，每隔1分钟执行一次
			job = JobBuilder.newJob(HelloJob)
					.withIdentity("HelloJob3", "group1")
					.build();
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger3", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-20 * * ?"))
					.build();
			sched.scheduleJob(job, trigger);
			
			//#任务4 下午4点到晚上11点内 每3分钟执行一次
			job = JobBuilder.newJob(HelloJob)
					.withIdentity("HelloJob4", "group1")
					.build();
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("trigger4", "group1")
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 16-23 * * ?"))
					.build();
			sched.scheduleJob(job, trigger);
			
			sched.pauseAll();
			sched.start();
			
			
			//等待5分钟
			//Thread.sleep(300L * 1000L); 
			while(true){
				Scanner input = new Scanner(System.in);
				System.out.print("input the command==>");
				String aa = input.nextLine();
				if("on".equals(aa)){
					//启动调度器
					sched.start();
				} else if("pauseAll".equals(aa)){
					//暂停
					sched.pauseAll();
				} else if("pause".equals(aa)){
					//暂停某个
					System.out.print("input jobName ==>");
					String jobName = input.nextLine();
					System.out.print("input jobGroup ==>");
					String jobGroup = input.nextLine();
					
					sched.pauseJob(JobBuilder.newJob().withIdentity(jobName, jobGroup).build().getKey());
				} else if("off".equals(aa)){
					//关闭
					sched.shutdown(true);
				} else if("resumeAll".equals(aa)){
					//恢复
					sched.resumeAll();
				} else if("resume".equals(aa)){
					System.out.print("input triggerName ==>");
					String triggerName = input.nextLine();
					System.out.print("input triggerGroup ==>");
					String triggerGroup = input.nextLine();
					Trigger tempTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup).build();
					sched.resumeTrigger(tempTrigger.getKey());
				} else if("show".equals(aa)){
					//展示
					List list = sched.getCurrentlyExecutingJobs();
					System.out.println(list.size());
					for(int i=0,len=list.size();i<len;i++){
						JobExecutionContext je = (JobExecutionContext) list.get(i);
						System.out.println(je.getJobDetail().getKey());
					}
				} else if("close".equals(aa)){
					break;
				}
			}
			
			
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//最终关闭调度器
			if((sched != null) && sched.isStarted()){
				try {
					sched.shutdown(true);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
