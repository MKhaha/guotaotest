package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface IAssetInfoService {
    List<AssetInfo> getAllAssetInfoNoPage();
    List<AssetInfo> getAllAssetInfoByNameNoPage(String assetName);
    List<AssetInfo> getAllAssetInfoByCategoryNoPage(String assetCategory);

    ServerResponse<PageInfo> getAssetInfoSelect(AssetInfo assetInfo, int pageNum, int pageSize);
    ServerResponse<PageInfo> getAllAssetInfo(int pageNum, int pageSize);
    ServerResponse<AssetInfo> getAllAssetInfoById(String assetId);
    ServerResponse<PageInfo> getAllAssetInfoByName(String assetName, int pageNum, int pageSize);
    ServerResponse<PageInfo> getAllAssetInfoByCategory(String assetCategory, int pageNum, int pageSize);
    ServerResponse<String> addAssetItem(AssetInfo assetInfo);
    ServerResponse<String> updateItem(AssetInfo assetInfo);
    ServerResponse<String> deleteItem(String assetId);
    ServerResponse<String> clearInventoryAmount();
    ServerResponse<String> assetsInventory(String assetId);
    ServerResponse<PageInfo> getCompleteInventory(AssetInfo assetInfo, int pageNum, int pageSize);
    ServerResponse<PageInfo> getNotCompleteInventory(AssetInfo assetInfo, int pageNum, int pageSize);

    void exportAssetInfo(AssetInfo assetInfo, HttpServletRequest request, HttpServletResponse response);

    ServerResponse<String> addAssetMutliItem(MultipartFile file);
    File exportAssetMutilItem(List<AssetInfo> assetInfoList);
}
