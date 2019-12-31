package com.learn.management.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DemoTask
 *
 * @author HRH
 * @date 2019/12/30
 **/

public class DemoTask implements Job {

    private Logger logger = LoggerFactory.getLogger(DemoTask.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("------------------------------------------------------DemoTask(start)...------------------------------------------------------");
        logger.info("-------------------------------------------------------DemoTask(end)...-------------------------------------------------------");

    }
}
