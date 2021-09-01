package edu.northeastern.base.manager;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by F Wu
 */

public interface JobListener {

    public String getName();

    public void jobToBeExecuted(JobExecutionContext context);

    public void jobExecutionVetoed(JobExecutionContext context);

    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException);

}
