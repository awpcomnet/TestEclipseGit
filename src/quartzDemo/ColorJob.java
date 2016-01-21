package quartzDemo;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job{
	public static String FAVORITE_COLOR = "color";
	public static String EXECUTION_COUNT = "count";
	private int _counter = 1;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DateTime dateTime = new DateTime(new Date());
		String format = "yyyy年MM月dd日 HH:mm:ss";
		
		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String favoriteColor = data.getString(FAVORITE_COLOR);
		int count = data.getInt(EXECUTION_COUNT);
		
		count ++;
		data.put("execution_count", count);
		System.out.println("ColorJob: " + jobKey + " executing at " + dateTime.toString(format) + "\n" + 
				" favorite color is " + favoriteColor + "\n" + 
				" execution count (from job map) is " + count + "\n" + 
				" execution count (from job member variable) is " + _counter);
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ColorJob.class);
}
