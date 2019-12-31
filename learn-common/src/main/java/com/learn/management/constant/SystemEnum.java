package com.learn.management.constant;

/**
 * SystemEnum 系统级功能枚举
 *
 * @author HRH
 * @date 2019/12/30
 **/
public class SystemEnum {

    //逻辑删除标识,1删除,0非删除
    public static final Integer DELETE_YES = 1;
    public static final Integer DELETE_NO = 0;


    //定时任务 是否运行"0"停止,"1"运行
    public static final String TASK_STOP = "0";
    public static final String TASK_START = "1";

    //定时任务 是否生效 "0"未生效,"1"生效
    public static final String TASK_EFFECT_NO = "0";
    public static final String TASK_EFFECT_YES = "1";

}
