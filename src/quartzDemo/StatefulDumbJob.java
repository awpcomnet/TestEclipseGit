package quartzDemo;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulDumbJob implements Job{
	public static final String NUM_EXECUTIONS = "NumExecutions";
	public static final String EXECUTION_DELAY = "ExecutionDelay";
	public StatefulDumbJob(){};
	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DateTime dateTime = new DateTime(new Date());
		String timeFormat = "yyyy年MM月dd日 HH:mm:ss";
		
		System.err.println("---"+ arg0.getJobDetail().getKey() + " executing.["+dateTime.toString(timeFormat)+"]");
		
		JobDataMap map = arg0.getJobDetail().getJobDataMap();
		int executeCount = 0;
		if(map.containsKey(NUM_EXECUTIONS)){
			executeCount = map.getInt(NUM_EXECUTIONS);
		}
		executeCount ++;
		map.put(NUM_EXECUTIONS, executeCount);
		long delay = 5000L;
		if(map.containsKey(EXECUTION_DELAY)){
			delay = map.getLong(EXECUTION_DELAY);
		}
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}
		System.err.println("  -"+arg0.getJobDetail().getKey()+" complete ("+executeCount+").");
		
	}

}
