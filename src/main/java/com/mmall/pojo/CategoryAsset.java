package com.mmall.pojo;

import java.util.Date;

public class CategoryAsset {
    private Integer id;

    private String categoryName;

    private String detail;

    private Date createTime;

    private Date updateTime;

    public CategoryAsset(Integer id, String categoryName, String detail, Date createTime, Date updateTime) {
        this.id = id;
        this.categoryName = categoryName;
        this.detail = detail;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public CategoryAsset() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
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