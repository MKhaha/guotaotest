package com.mmall.pojo;

import java.util.Date;

public class DepartmentGroup {
    private Integer id;

    private String departmentName;

    private String detail;

    private Date createTime;

    private Date updateTime;

    public DepartmentGroup(Integer id, String departmentName, String detail, Date createTime, Date updateTime) {
        this.id = id;
        this.departmentName = departmentName;
        this.detail = detail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DepartmentGroup() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}