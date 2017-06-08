package com.mmall.dao;

import com.mmall.pojo.AssetInfo;

import java.util.List;

public interface AssetInfoMapper {
    int deleteByPrimaryKey(String assetId);

    int insert(AssetInfo record);

    int insertSelective(AssetInfo record);

    AssetInfo selectByPrimaryKey(String assetId);

    int updateByPrimaryKeySelective(AssetInfo record);

    int updateByPrimaryKey(AssetInfo record);

    List<AssetInfo> selectAll();

    // 按照record中非空变量查找相近或相等的
    List<AssetInfo> selectSimilarRecord(AssetInfo record);

    List<AssetInfo> selectByAssetName(String assetName);

    List<AssetInfo> selectByAssetCategory(String assetCategory);

    int checkAssetId(String assetId);

    // 批量增加资产信息
    int insertAssertBatch(List<AssetInfo> assetInfoList);

    // 库存盘点前清空盘点数量
    int clearInventoryAmountAll();

    int getAssetCount();

    // 按资产编号盘点资产
    int updateInventoryAmountByPrimaryKey(String assetId);

    // 完成库存盘点查询
    List<AssetInfo> selectCompleteInventory(AssetInfo assetInfo);
    // 未完成库存盘点查询
    List<AssetInfo> selectNotCompleteInventory(AssetInfo assetInfo);
}