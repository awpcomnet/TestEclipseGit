package quartzDemo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BadJob1 implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			int zero = 0;
			int calculation = 1024/zero;
		} catch (Exception e) {
			LOG.info("--- Error in job!");
			JobExecutionException e2 = new JobExecutionException();
			e2.refireImmediately();
			throw e2;
		}
		
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(BadJob1.class);
}
