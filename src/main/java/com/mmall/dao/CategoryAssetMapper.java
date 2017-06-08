package com.mmall.dao;

import com.mmall.pojo.CategoryAsset;

import java.util.List;

public interface CategoryAssetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CategoryAsset record);

    int insertSelective(CategoryAsset record);

    CategoryAsset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CategoryAsset record);

    int updateByPrimaryKey(CategoryAsset record);

    List<CategoryAsset> selectAll();

    int checkCategoryName(String categoryName);
}