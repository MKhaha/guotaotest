package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.AssetInfoMapper;
import com.mmall.dao.AssetRecycleMapper;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetInfoService;
import com.mmall.util.EJConvertor;
import com.mmall.util.FileUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service("iAssetInfo")
public class AssetInfoServiceImpl implements IAssetInfoService {

    private Logger logger = LoggerFactory.getLogger(AssetInfoServiceImpl.class);

    @Autowired
    private AssetInfoMapper assetInfoMapper;
    @Autowired
    private EJConvertor ejConvertor;
    @Autowired
    private AssetRecycleMapper assetRecycleMapper;

    @Override
    public List<AssetInfo> getAllAssetInfoNoPage() {
        List<AssetInfo> assetInfoList = assetInfoMapper.selectAll();
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        return assetInfoList;
    }

    @Override
    public List<AssetInfo> getAllAssetInfoByNameNoPage(String assetName) {
        List<AssetInfo> assetInfoList = assetInfoMapper.selectByAssetName(assetName);
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        return assetInfoList;
    }

    @Override
    public List<AssetInfo> getAllAssetInfoByCategoryNoPage(String assetCategory) {
        List<AssetInfo> assetInfoList = assetInfoMapper.selectByAssetCategory(assetCategory);
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        return assetInfoList;
    }

    @Override
    public ServerResponse<PageInfo> getAssetInfoSelect(AssetInfo assetInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetInfo> assetInfoList = assetInfoMapper.selectSimilarRecord(assetInfo);
        if(CollectionUtils.isEmpty(assetInfoList)) {
            logger.info("未找到资产信息");
        }
        PageInfo pageResult =new PageInfo(assetInfoList);
        pageResult.setList(assetInfoList);
        return ServerResponse.createBySuccess(pageResult);
    }


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
    public ServerResponse<String> deleteItem(String assetId) {
        AssetInfo assetInfo = assetInfoMapper.selectByPrimaryKey(assetId);
        if(assetInfo != null) {
            int deleteCount = assetInfoMapper.deleteByPrimaryKey(assetId);
            if(deleteCount > 0) {
                if(assetRecycleMapper.selectByPrimaryKey(assetId) != null) {
                    assetRecycleMapper.updateByPrimaryKeySelective(assetInfo);
                } else {
                    assetRecycleMapper.insertSelective(assetInfo);
                }
                return ServerResponse.createBySuccessMessage("删除资产信息成功");
            } else {
                return ServerResponse.createByErrorMessage("删除资产信息失败");
            }
        } else {
            return ServerResponse.createByErrorMessage("没有对应资产信息");
        }
    }

    @Override
    public  ServerResponse<String> addAssetMutliItem(MultipartFile file) {

        int total = 0;
        int available = 0;
        int insertCount = 0;
        // 从文件中读取
        try {
            File tempFile = File.createTempFile("tmp", null);
            tempFile.deleteOnExit();
            FileUtil.convertMultipartFileToFile(file, tempFile);

            System.out.println("tempFile.getAbsolutePath()" + tempFile.getAbsolutePath());
            List<AssetInfo> assetInfoList = ejConvertor.excelReader(AssetInfo.class, tempFile);
            if (assetInfoList != null) {

                AssetInfo assetSearch;
                List<AssetInfo> availableAssetInfoList = new ArrayList<>();
                for (AssetInfo assetInfo : assetInfoList) {

                    assetSearch = assetInfoMapper.selectByPrimaryKey(assetInfo.getAssetId());
                    if(assetSearch == null) {
                        availableAssetInfoList.add(assetInfo);
                    }
                }
                total = assetInfoList.size();
                available = availableAssetInfoList.size();

                if(available > 0) {
                    insertCount = assetInfoMapper.insertAssertBatch(availableAssetInfoList);
                    if(insertCount == 0) {
                        return ServerResponse.createByErrorMessage("导入数据库记录为0");
                    } else if (insertCount != available) {
                        available = insertCount;
                        logger.warn("导入数据库中记录数与应该导入的记录数不一致");
                    }
                }
                String tempString = String.format("批量添加资产信息成功, excel中读取%d条记录, 写入数据库%d条记录", total, available);
                return ServerResponse.createBySuccess(tempString);
            }
            return ServerResponse.createByErrorMessage("excel中读取的记录数为0");
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("批量添加资产信息出错");
        }
    }

    @Override
    public File exportAssetMutilItem(List<AssetInfo> assetInfoList) {
        if (assetInfoList == null)
            return null;
        return ejConvertor.excelWriter(AssetInfo.class, assetInfoList);
    }

    @Override
    public void exportAssetInfo(AssetInfo assetInfo, HttpServletRequest request, HttpServletResponse response) {
        String fileName = "assetRecord.xlsx";

        try {
            HttpSession session = request.getSession();
            List<AssetInfo> assetInfoList = assetInfoMapper.selectSimilarRecord(assetInfo);
            if(assetInfoList != null) {
                File file = exportAssetMutilItem(assetInfoList);
                if(file != null) {
                    // 设置响应头
                    response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                    FileInputStream inputStream = new FileInputStream(file);
                    OutputStream outputStream = response.getOutputStream();
                    byte[] buffer = new byte[8192];

                    int len;
                    while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
                        outputStream.write(buffer, 0, len);
                        outputStream.flush();
                    }

                    inputStream.close();
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServerResponse<String> clearInventoryAmount() {
        try {
            int assetCount = assetInfoMapper.getAssetCount();

            if(assetCount != assetInfoMapper.clearInventoryAmountAll()) {
                return ServerResponse.createByErrorMessage("清空资产盘点数量失败");
            }
            return ServerResponse.createBySuccessMessage("清空资产盘点数量成功");

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库操作出现问题");
        }
    }

    @Override
    public ServerResponse<String> assetsInventory(String assetId) {
        try {
            if(assetInfoMapper.checkAssetId(assetId) == 0) {
                return ServerResponse.createByErrorMessage("对应资产信息不存在");
            }
            AssetInfo assetInfo = assetInfoMapper.selectByPrimaryKey(assetId);
            int bookAmount = assetInfo.getBookAmount();
            int inventoryAmount = assetInfo.getInventoryAmount();
            if(bookAmount > inventoryAmount) {
                if(assetInfoMapper.updateInventoryAmountByPrimaryKey(assetId) == 0) {
                    return ServerResponse.createByErrorMessage("更新资产盘点数量失败");
                }
                return ServerResponse.createBySuccessMessage("更新资产盘点数量成功");
            } else {
                return ServerResponse.createByErrorMessage("该资产已经完成盘点");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库操作出现问题");
        }
    }

    @Override
    public ServerResponse<PageInfo> getCompleteInventory(AssetInfo assetInfo, int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<AssetInfo> assetInfoList = assetInfoMapper.selectCompleteInventory(assetInfo);
            if(CollectionUtils.isEmpty(assetInfoList)) {
                logger.info("未找到已盘点的资产信息");
            }
            PageInfo pageResult = new PageInfo(assetInfoList);
            pageResult.setList(assetInfoList);
            return ServerResponse.createBySuccess(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库操作出现问题");
        }
    }

    @Override
    public ServerResponse<PageInfo> getNotCompleteInventory(AssetInfo assetInfo, int pageNum, int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<AssetInfo> assetInfoList = assetInfoMapper.selectNotCompleteInventory(assetInfo);
            if(CollectionUtils.isEmpty(assetInfoList)) {
                logger.info("未找到未盘点的资产信息");
            }
            PageInfo pageResult = new PageInfo(assetInfoList);
            pageResult.setList(assetInfoList);
            return ServerResponse.createBySuccess(pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("数据库操作出现问题");
        }
    }

}
