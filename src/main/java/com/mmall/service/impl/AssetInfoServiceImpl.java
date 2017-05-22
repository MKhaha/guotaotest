package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.AssetInfoMapper;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service("iAssetInfo")
public class AssetInfoServiceImpl implements IAssetInfoService {

    private Logger logger = LoggerFactory.getLogger(AssetInfoServiceImpl.class);

    @Autowired
    private AssetInfoMapper assetInfoMapper;

    @Override
    public ServerResponse<PageInfo> getAllAssetInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetInfo> assetInfoList = assetInfoMapper.selectAll();
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        PageInfo pageResult =new PageInfo(assetInfoList);
        pageResult.setList(assetInfoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<AssetInfo> getAllAssetInfoById(String assetId) {
        AssetInfo assetInfo = assetInfoMapper.selectByPrimaryKey(assetId);
        if(assetInfo == null) {
            logger.info("未找到资产信息");
        }
        return ServerResponse.createBySuccess(assetInfo);
    }

    @Override
    public ServerResponse<PageInfo> getAllAssetInfoByName(String assetName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetInfo> assetInfoList = assetInfoMapper.selectByAssetName(assetName);
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        PageInfo pageResult = new PageInfo(assetInfoList);
        pageResult.setList(assetInfoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<PageInfo> getAllAssetInfoByCategory(String assetCategory, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetInfo> assetInfoList = assetInfoMapper.selectByAssetCategory(assetCategory);
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        PageInfo pageResult = new PageInfo(assetInfoList);
        pageResult.setList(assetInfoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<String> addAssetItem(AssetInfo assetInfo) {
        int resultCount = assetInfoMapper.checkAssetId(assetInfo.getAssetId());
        if(resultCount > 0) {
            return ServerResponse.createByErrorMessage("资产编码已存在");
        }
        resultCount = assetInfoMapper.insert(assetInfo);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("增加资产信息失败");
        }
        return ServerResponse.createBySuccessMessage("增加资产信息成功");
    }

    @Override
    public ServerResponse<String> updateItem(AssetInfo assetInfo) {
        int updateCount = assetInfoMapper.updateByPrimaryKeySelective(assetInfo);
        if(updateCount > 0) {
            return ServerResponse.createBySuccessMessage("更新资产信息成功");
        }
        return ServerResponse.createByErrorMessage("更新资产信息失败");
    }

    @Override
    public ServerResponse<String> deleteItem(String assetid) {
        int resultCount = assetInfoMapper.checkAssetId(assetid);
        if(resultCount > 0) {
            int deleteCount = assetInfoMapper.deleteByPrimaryKey(assetid);
            if(deleteCount > 0) {
                return ServerResponse.createBySuccessMessage("删除资产信息成功");
            } else {
                return ServerResponse.createByErrorMessage("删除资产信息失败");
            }
        } else {
            return ServerResponse.createByErrorMessage("没有对应资产信息");
        }
    }


}
