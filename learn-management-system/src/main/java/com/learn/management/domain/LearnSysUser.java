package com.learn.management.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.learn.management.system.rpc.dto.LearnSysUserDTO;

import com.learn.management.system.rpc.vo.LearnSysUserVO;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author AutoGenerator
 * 
 */
public class LearnSysUser implements Serializable {

    // 主键id
    private Long id;

    // 头像地址
    private String headImg;

    // 真实名称
    private String realName;

    // 年龄
    private Integer age;

    // 性别 1.男 2.女
    private Integer sex;

    // 删除标识 0.未删除 1.已删除
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg){
        this.headImg = headImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName){
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex){
        this.sex = sex;
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

	public LearnSysUser(){}

	public LearnSysUser(LearnSysUserDTO learnSysUserDTO){
		BeanUtils.copyProperties(learnSysUserDTO,this);
}

    public LearnSysUserVO toConvertBeanVO(){
        LearnSysUserVO voBean=new LearnSysUserVO();
        BeanUtils.copyProperties(this,voBean);
      return voBean;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}