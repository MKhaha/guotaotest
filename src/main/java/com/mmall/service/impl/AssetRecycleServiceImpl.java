package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.AssetInfoMapper;
import com.mmall.dao.AssetRecycleMapper;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetRecycleService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9.
 */
@Service("iAssetRecycle")
public class AssetRecycleServiceImpl implements IAssetRecycleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AssetRecycleMapper assetRecycleMapper;
    @Autowired
    private AssetInfoMapper assetInfoMapper;

    @Override
    public ServerResponse<PageInfo> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetInfo> assetInfoList = assetRecycleMapper.selectAll();
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("资产回收站为空");
        }
        PageInfo pageResult =new PageInfo(assetInfoList);
        pageResult.setList(assetInfoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<String> deleteItem(String assetId) {
        AssetInfo assetInfo = assetRecycleMapper.selectByPrimaryKey(assetId);
        try {
            if(assetInfo != null) {
                assetRecycleMapper.deleteByPrimaryKey(assetId);
                return ServerResponse.createBySuccessMessage("删除回收站中资产信息成功");
            } else {
                return ServerResponse.createByErrorMessage("回收站中没有对应资产信息");
            }

        } catch (Exception e) {
            return ServerResponse.createByErrorMessage("删除回收站中资产信息失败");
        }
    }

    @Override
    @Transactional()
    public ServerResponse<List<String>> deleteAssetRecycleMultiItem(List<String> assetIdList) {
        List<String> notExistList = new ArrayList<>();
        try {
            for (String assetId : assetIdList) {
                if(assetRecycleMapper.selectByPrimaryKey(assetId) != null) {
                    assetRecycleMapper.deleteByPrimaryKey(assetId);
                } else {
                    notExistList.add(assetId);
                }
            }

            if (notExistList.isEmpty()) {
                return ServerResponse.createBySuccessMessage("删除回收站中多项资产信息成功");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ServerResponse.createByError("回收站中有部分资产信息不存在", notExistList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("删除回收站中资产信息失败");
        }
    }

    @Override
    public ServerResponse<String> notExistItemInAssetTable(String assetId) {
        AssetInfo assetInfo = assetInfoMapper.selectByPrimaryKey(assetId);
        if(assetId != null) {
            return ServerResponse.createBySuccessMessage("原表中不存在这样的资产编号");
        } else {
            return ServerResponse.createByError("原表中存在这样的资产编号", assetId);
        }
    }

    private int recycleItem(AssetInfo assetInfo) {
        int resultCount = 0;
        if(assetInfoMapper.selectByPrimaryKey(assetInfo.getAssetId()) != null) {
            resultCount = assetInfoMapper.updateByPrimaryKeySelective(assetInfo);
        } else {
            resultCount = assetInfoMapper.insertSelective(assetInfo);
        }
        return resultCount;
    }

    @Override
    public ServerResponse<String> recycleAssetItem(String assetId) {
        if(assetId == null) {
            return ServerResponse.createByErrorMessage("传入数据为空");
        }
        AssetInfo assetInfo = assetRecycleMapper.selectByPrimaryKey(assetId);
        if(assetInfo == null) {
            return ServerResponse.createByErrorMessage("回收站中不存在对应资产编号");
        }

        int resultCount;
        resultCount = recycleItem(assetInfo);
        if(resultCount == 1) {
            assetRecycleMapper.deleteByPrimaryKey(assetInfo.getAssetId());
            return ServerResponse.createBySuccessMessage("回收成功");
        }
        return ServerResponse.createByErrorMessage("回收失败");

    }

    @Override
    public ServerResponse<List<String>> notExistMultiItemInAssetTable(List<String> assetIdList) {
        if(assetIdList == null) {
            return ServerResponse.createByErrorMessage("传入数据列表为空");
        }
        List<String> existList = new ArrayList<>();
        for (String assetIdItem : assetIdList) {
            if(assetInfoMapper.selectByPrimaryKey(assetIdItem) != null) {
                existList.add(assetIdItem);
            }
        }

        if(existList.isEmpty()) {
            return ServerResponse.createBySuccessMessage("原表中不存在列表中的资产编号");
        } else {
            return ServerResponse.createByError("原表中存在列表中的资产编号", existList);
        }
    }

    @Override
    @Transactional()
    public ServerResponse<String> recycleAssetMultiItem(List<String> assetIdList) {

        try {
            if(assetIdList == null) {
                return ServerResponse.createByErrorMessage("传入数据列表为空");
            }
            for (String assetIdItem : assetIdList) {
                AssetInfo assetInfo = assetRecycleMapper.selectByPrimaryKey(assetIdItem);
                if(assetInfo == null) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ServerResponse.createByErrorMessage("回收站中不存在资产编号：" + assetIdItem);
                }
                recycleItem(assetInfo);
            }

            return ServerResponse.createBySuccessMessage("回收成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ServerResponse.createByErrorMessage("回收失败");
        }
    }
}
