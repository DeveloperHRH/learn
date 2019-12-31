package com.learn.management.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.learn.management.system.rpc.dto.LearnSysTimetaskDTO;

import com.learn.management.system.rpc.vo.LearnSysTimetaskVO;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author AutoGenerator
 * 
 */
public class LearnSysTimetask implements Serializable {

    // 主键id
    private Long id;

    // cron表达式
    private String cronExpression;

    // 是否生效 0/未生效,1/生效
    private Integer isEffect;

    // 是否运行0停止,1运行
    private Integer isStart;

    // 任务描述
    private String taskDescribe;

    // 任务code
    private String taskCode;

    // 任务类名
    private String className;

    // 创建者,管理操作,用户存user_id
    private String createBy;

    // 创建时间
    private Long createTime;

    // 修改者,管理操作,存字符串,用户操作存 user_id
    private String modifyBy;

    // 修改时间
    private Long modifyTime;

    // 逻辑删除标识,1删除,0非删除
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

    /**
    * 查询多个id
    */
    private String ids;
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

	public LearnSysTimetask(){}

	public LearnSysTimetask(LearnSysTimetaskDTO learnSysTimetaskDTO){
		BeanUtils.copyProperties(learnSysTimetaskDTO,this);
}

    public LearnSysTimetaskVO toConvertBeanVO(){
        LearnSysTimetaskVO voBean=new LearnSysTimetaskVO();
        BeanUtils.copyProperties(this,voBean);
      return voBean;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}