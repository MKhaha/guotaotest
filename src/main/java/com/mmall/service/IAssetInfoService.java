package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface IAssetInfoService {
    ServerResponse<PageInfo> getAllAssetInfo(int pageNum, int pageSize);
    ServerResponse<AssetInfo> getAllAssetInfoById(String assetId);
    ServerResponse<PageInfo> getAllAssetInfoByName(String assetName, int pageNum, int pageSize);
    ServerResponse<PageInfo> getAllAssetInfoByCategory(String assetCategory, int pageNum, int pageSize);
    ServerResponse<String> addAssetItem(AssetInfo assetInfo);
    ServerResponse<String> updateItem(AssetInfo assetInfo);
    ServerResponse<String> deleteItem(String assetid);
}
