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

    List<AssetInfo> selectByAssetName(String assetName);

    List<AssetInfo> selectByAssetCategory(String assetCategory);

    int checkAssetId(String assetId);
}