package com.learn.management.task;

import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.List;

/**
 * DataBaseSchedulerFactoryBean
 * 读取数据库 然后判断是否启动任务
 * @author HRH
 * @date 2019/12/31
 **/
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {

    private Logger logger = LoggerFactory.getLogger(DataBaseSchedulerFactoryBean.class);

    /**
     * 读取数据库判断是否开始定时任务
     */
    public void afterPropertiesSet() throws Exception {
        //todo 需要配置，在项目启动时获取所有需要执行的定时任务并让他们执行
        logger.info("---------------------------------start---------------------------------------");

        logger.info("----------------------------------end----------------------------------------");
    }


}
