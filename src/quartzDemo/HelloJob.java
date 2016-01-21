package quartzDemo;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * @Description: 实现JOB接口,接口中只有一个方法execute()
 * @author 王航
 * @date 2015年12月18日 下午2:24:22
 */
@DisallowConcurrentExecution
public class HelloJob implements Job{
	private static String FORMAT = "yyyy年MM月dd日 HH:mm:ss";
	private String property;
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DateTime dt = new DateTime(new Date());
		JobKey jobKey = context.getJobDetail().getKey();
		String description = context.getJobDetail().getDescription();
		System.out.println("Hello World!-" + (new DateTime(new Date()).toString(FORMAT)));
		System.out.println("SimpleJob says:"+jobKey+ "  description:"+description);
		for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
				System.out.println("下一次执行时间:"+(new DateTime(context.getTrigger().getNextFireTime()).toString(FORMAT)));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public HelloJob(){
	}

	@Override
	public String toString() {
		return "HelloJob [property=" + property + "]";
	}
	
}
