package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.CategoryAsset;
import com.mmall.vo.CategoryVo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface ICategoryAssetService {
    ServerResponse<List<CategoryVo>> getCategoryList();
    ServerResponse<String> addCategoryItem(String categoryName);
    ServerResponse<String> updateCategoryItem(CategoryVo categoryVo);
    ServerResponse<String> deleteCategoryItem(int categoryId);

}
