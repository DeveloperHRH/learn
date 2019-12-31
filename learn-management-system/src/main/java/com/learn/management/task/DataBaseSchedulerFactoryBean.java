package com.learn.management.task;

import com.learn.management.constant.SystemEnum;
import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;
import com.learn.management.system.rpc.service.ILearnSysTimetaskService;
import com.learn.management.system.rpc.vo.LearnSysTimetaskVO;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DataBaseSchedulerFactoryBean
 * 读取数据库 然后判断是否启动任务
 * @author HRH
 * @date 2019/12/31
 **/
@Component
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {

    @Autowired
    private ILearnSysTimetaskService sysTimetaskService;

    private Logger logger = LoggerFactory.getLogger(DataBaseSchedulerFactoryBean.class);

    /**
     * 读取数据库判断是否开始定时任务
     */
    public void afterPropertiesSet() throws Exception {
        //todo 需要配置，在项目启动时获取所有需要执行的定时任务并让他们执行
        logger.info("---------------------------------start---------------------------------------");

        super.afterPropertiesSet();
        Scheduler scheduler = this.getScheduler();
        List<String> trigerrNames = scheduler.getTriggerGroupNames();

        LearnSysTimetaskVO vo;
        LearnSysTimetaskDTO dto = new LearnSysTimetaskDTO();
        for(String trigerrName : trigerrNames){
            dto.setTaskCode(trigerrName);
            vo = sysTimetaskService.selectByDTO(dto);
            if(vo == null || !SystemEnum.TASK_START.equals(vo.getIsStart().toString())){
                scheduler.pauseTrigger(new TriggerKey(trigerrName));
            }
        }

        logger.info("----------------------------------end----------------------------------------");
    }


}
