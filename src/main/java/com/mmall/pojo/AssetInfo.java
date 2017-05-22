package com.mmall.pojo;

import java.util.Date;

public class AssetInfo {
    private String assetId;

    private String assetName;

    private String assetCategory;

    private String brandSpecification;

    private String unitMeasurement;

    private Integer bookAmount;

    private Integer inventoryAmount;

    private String storageLocation;

    private String departmentResponsibility;

    private String personInCharge;

    private String statusUsage;

    private Date createTime;

    private Date updateTime;

    public AssetInfo(String assetId, String assetName, String assetCategory, String brandSpecification, String unitMeasurement, Integer bookAmount, Integer inventoryAmount, String storageLocation, String departmentResponsibility, String personInCharge, String statusUsage, Date createTime, Date updateTime) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetCategory = assetCategory;
        this.brandSpecification = brandSpecification;
        this.unitMeasurement = unitMeasurement;
        this.bookAmount = bookAmount;
        this.inventoryAmount = inventoryAmount;
        this.storageLocation = storageLocation;
        this.departmentResponsibility = departmentResponsibility;
        this.personInCharge = personInCharge;
        this.statusUsage = statusUsage;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public AssetInfo() {
        super();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory == null ? null : assetCategory.trim();
    }

    public String getBrandSpecification() {
        return brandSpecification;
    }

    public void setBrandSpecification(String brandSpecification) {
        this.brandSpecification = brandSpecification == null ? null : brandSpecification.trim();
    }

    public String getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        this.unitMeasurement = unitMeasurement == null ? null : unitMeasurement.trim();
    }

    public Integer getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(Integer bookAmount) {
        this.bookAmount = bookAmount;
    }

    public Integer getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Integer inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation == null ? null : storageLocation.trim();
    }

    public String getDepartmentResponsibility() {
        return departmentResponsibility;
    }

    public void setDepartmentResponsibility(String departmentResponsibility) {
        this.departmentResponsibility = departmentResponsibility == null ? null : departmentResponsibility.trim();
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge == null ? null : personInCharge.trim();
    }

    public String getStatusUsage() {
        return statusUsage;
    }

    public void setStatusUsage(String statusUsage) {
        this.statusUsage = statusUsage == null ? null : statusUsage.trim();
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