package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */
public interface IAssetRecycleService {
    ServerResponse<PageInfo> selectAll(AssetInfo assetInfo, int pageNum, int pageSize);
    ServerResponse<String> deleteItem(String assetId);
    ServerResponse<List<String>> deleteAssetRecycleMultiItem(List<String> assetIdList);
    ServerResponse<String> notExistItemInAssetTable(String assetId);
    ServerResponse<String> recycleAssetItem(String assetId);
    ServerResponse<List<String>> notExistMultiItemInAssetTable(List<String> assetIdList);
    ServerResponse<String> recycleAssetMultiItem(List<String> assetIdList);
}
