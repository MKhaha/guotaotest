package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.CategoryAsset;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface ICategoryAssetService {
    ServerResponse<PageInfo> getCategoryList(int pageNum, int pageSize);
    ServerResponse<String> addCategoryItem(CategoryAsset categoryAsset);
    ServerResponse<String> updateCategoryItem(CategoryAsset categoryAsset);
    ServerResponse<String> deleteCategoryItem(int categoryId);

}
