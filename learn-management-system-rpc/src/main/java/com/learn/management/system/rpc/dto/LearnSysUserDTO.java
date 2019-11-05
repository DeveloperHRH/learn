package com.learn.management.system.rpc.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;



/**
 * @author HRH
 * 
 */
public class LearnSysUserDTO  implements Serializable {

    /** 主键id*/
    @ApiModelProperty(value = "主键id", name = "id")
    private Long id;

    /** 头像地址*/
    @ApiModelProperty(value = "头像地址", name = "headImg")
    private String headImg;

    /** 真实名称*/
    @ApiModelProperty(value = "真实名称", name = "realName")
    private String realName;

    /** 年龄*/
    @ApiModelProperty(value = "年龄", name = "age")
    private Integer age;

    /** 性别 1.男 2.女*/
    @ApiModelProperty(value = "性别 1.男 2.女", name = "sex")
    private Integer sex;

    /** 删除标识 0.未删除 1.已删除*/
    @ApiModelProperty(value = "删除标识 0.未删除 1.已删除", name = "deleted")
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
    @ApiModelProperty(value = "多个查询id拼成的字符串，用“，”分隔符", name = "ids")
    private String ids;
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}