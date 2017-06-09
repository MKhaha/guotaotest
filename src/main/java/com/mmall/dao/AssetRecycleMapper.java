package com.mmall.dao;

import com.mmall.pojo.AssetInfo;

import java.util.List;

public interface AssetRecycleMapper {
    int deleteByPrimaryKey(String assetId);

    int insert(AssetInfo record);

    int insertSelective(AssetInfo record);

    AssetInfo selectByPrimaryKey(String assetId);

    int updateByPrimaryKeySelective(AssetInfo record);

    int updateByPrimaryKey(AssetInfo record);

    List<AssetInfo> selectAll();

    // 按照record中非空变量查找相近或相等的
    List<AssetInfo> selectSimilarRecord(AssetInfo record);

}