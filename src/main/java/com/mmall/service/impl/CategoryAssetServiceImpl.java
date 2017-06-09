package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryAssetMapper;
import com.mmall.pojo.CategoryAsset;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.ICategoryAssetService;
import com.mmall.vo.CategoryVo;
import com.mmall.vo.DepartmentVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Service("iCategory")
public class CategoryAssetServiceImpl implements ICategoryAssetService {

    private Logger logger = LoggerFactory.getLogger(CategoryAssetServiceImpl.class);
    @Autowired
    private CategoryAssetMapper categoryAssetMapper;

    @Override
    public ServerResponse<List<CategoryVo>> getCategoryList() {
        List<CategoryVo> categoryVoList = new ArrayList<>();
        List<CategoryAsset> categoryAssetList = categoryAssetMapper.selectAll();
        if(CollectionUtils.isEmpty(categoryAssetList)) {
            logger.info("未找到资产分类信息");
        } else {
            for (CategoryAsset categoryAssetItem : categoryAssetList) {
                CategoryVo categoryVoItem = new CategoryVo();

                categoryVoItem.setId(categoryAssetItem.getId());
                categoryVoItem.setCategoryName(categoryAssetItem.getCategoryName());
                categoryVoList.add(categoryVoItem);

            }
        }
        return ServerResponse.createBySuccess(categoryVoList);
    }

    @Override
    public ServerResponse<String> addCategoryItem(String categoryName) {
        System.out.println(categoryName);
        if(org.apache.commons.lang3.StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("字符串无意义");
        }
        if(categoryAssetMapper.checkCategoryName(categoryName) != 0) {
            return ServerResponse.createByErrorMessage("资产分类已存在");
        }

        CategoryAsset categoryAsset = new CategoryAsset();
        categoryAsset.setCategoryName(categoryName);
        int resultCount = categoryAssetMapper.insertSelective(categoryAsset);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加资产分类信息失败");
        }
        return ServerResponse.createBySuccessMessage("添加资产分类信息成功");
    }

    @Override
    public ServerResponse<String> updateCategoryItem(CategoryVo categoryVo) {
        System.out.println(categoryVo.getCategoryName());

        if(org.apache.commons.lang3.StringUtils.isBlank(categoryVo.getCategoryName())) {
            return ServerResponse.createByErrorMessage("字符串无意义");
        }
        if(categoryAssetMapper.checkCategoryName(categoryVo.getCategoryName()) != 0) {
            return ServerResponse.createByErrorMessage("资产分类已存在");
        }

        CategoryAsset categoryAsset = categoryAssetMapper.selectByPrimaryKey(categoryVo.getId());
        if(categoryVo.getCategoryName().equals(categoryAsset.getCategoryName())) {
            return ServerResponse.createByErrorMessage("未更改资产分类名称");
        }
        categoryAsset.setCategoryName(categoryVo.getCategoryName());
        int updateCount = categoryAssetMapper.updateByPrimaryKeySelective(categoryAsset);
        if(updateCount == 0) {
            return ServerResponse.createByErrorMessage("更新资产分类信息失败");
        }
        return ServerResponse.createBySuccessMessage("更新资产分类信息成功");
    }

    @Override
    public ServerResponse<String> deleteCategoryItem(int categoryId) {
        int deleteCount = categoryAssetMapper.deleteByPrimaryKey(categoryId);
        if(deleteCount == 0) {
            return ServerResponse.createByErrorMessage("删除资产分类信息失败");
        } else {
            return ServerResponse.createBySuccessMessage("删除资产分类信息成功");
        }
    }
}
