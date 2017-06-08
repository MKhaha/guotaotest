package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryAssetMapper;
import com.mmall.pojo.CategoryAsset;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.ICategoryAssetService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ServerResponse<PageInfo> getCategoryList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryAsset> categoryAssetList = categoryAssetMapper.selectAll();
        if(CollectionUtils.isEmpty(categoryAssetList)) {
            logger.info("未找到资产分类信息");
        }
        PageInfo pageResult =new PageInfo(categoryAssetList);
        pageResult.setList(categoryAssetList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<String> addCategoryItem(CategoryAsset categoryAsset) {
        if(categoryAssetMapper.checkCategoryName(categoryAsset.getCategoryName()) != 0) {
            return ServerResponse.createByErrorMessage("资产分类已存在");
        }

        int resultCount = categoryAssetMapper.insertSelective(categoryAsset);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加资产分类信息失败");
        }
        return ServerResponse.createBySuccessMessage("添加资产分类信息成功");
    }

    @Override
    public ServerResponse<String> updateCategoryItem(CategoryAsset categoryAsset) {
        if(categoryAssetMapper.checkCategoryName(categoryAsset.getCategoryName()) != 0) {
            return ServerResponse.createByErrorMessage("资产分类已存在");
        }
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
