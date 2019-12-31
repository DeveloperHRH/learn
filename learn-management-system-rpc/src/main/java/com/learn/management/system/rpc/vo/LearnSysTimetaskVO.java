package com.learn.management.system.rpc.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author HRH
 * 
 */
public class LearnSysTimetaskVO implements Serializable {

    // 主键id
    @ApiModelProperty(value = "主键id", name = "id")
    private Long id;

    // cron表达式
    @ApiModelProperty(value = "cron表达式", name = "cronExpression")
    private String cronExpression;

    // 是否生效 0/未生效,1/生效
    @ApiModelProperty(value = "是否生效 0/未生效,1/生效", name = "isEffect")
    private Integer isEffect;

    // 是否运行0停止,1运行
    @ApiModelProperty(value = "是否运行0停止,1运行", name = "isStart")
    private Integer isStart;

    // 任务描述
    @ApiModelProperty(value = "任务描述", name = "taskDescribe")
    private String taskDescribe;

    // 任务code
    @ApiModelProperty(value = "任务code", name = "taskCode")
    private String taskCode;

    // 任务类名
    @ApiModelProperty(value = "任务类名", name = "className")
    private String className;

    // 创建者,管理操作,用户存user_id
    @ApiModelProperty(value = "创建者,管理操作,用户存user_id", name = "createBy")
    private String createBy;

    // 创建时间
    @ApiModelProperty(value = "创建时间", name = "createTime")
    private Long createTime;

    // 修改者,管理操作,存字符串,用户操作存 user_id
    @ApiModelProperty(value = "修改者,管理操作,存字符串,用户操作存 user_id", name = "modifyBy")
    private String modifyBy;

    // 修改时间
    @ApiModelProperty(value = "修改时间", name = "modifyTime")
    private Long modifyTime;

    // 逻辑删除标识,1删除,0非删除
    @ApiModelProperty(value = "逻辑删除标识,1删除,0非删除", name = "deleted")
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression){
        this.cronExpression = cronExpression;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect){
        this.isEffect = isEffect;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart){
        this.isStart = isStart;
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe){
        this.taskDescribe = taskDescribe;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode){
        this.taskCode = taskCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className){
        this.className = className;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime){
        this.createTime = createTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy){
        this.modifyBy = modifyBy;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime){
        this.modifyTime = modifyTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted){
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}