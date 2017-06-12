package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetInfoService;
import com.mmall.service.IAssetPrinter;
import com.mmall.service.IAssetRecycleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/asset/")
public class AssetController {

    @Autowired
    private IAssetInfoService iAssetInfo;

    @Autowired
    private IAssetPrinter iAssetPrinter;
    @Autowired
    private IAssetRecycleService iAssetRecycle;

    private Logger logger = LoggerFactory.getLogger(AssetController.class);


    @RequestMapping(value = "test.do")
    @ResponseBody
    public ServerResponse<String> login(){
        return ServerResponse.createByErrorMessage("hello ~~~");
    }

    private static final String SEARCH_BY_ASSET_ID = "searchByAssetId";
    private static final String SEARCH_BY_ASSET_NAME = "searchByAssetName";
    private static final String SEARCH_BY_ASSET_CATEGORY = "searchByAssetCategory";
    private static final String SEARCH_ALL = "searchAll";

    @RequestMapping(value = "getAssetAllList.do")
    @ResponseBody
    public ServerResponse<PageInfo> getAllAsset(@RequestParam("searchType") String searchType,
                                                @RequestParam("keyWord") String keyWord,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iAssetInfo.getAllAssetInfo(pageNum, pageSize);
    }

    @RequestMapping(value = "getAssetById.do")
    @ResponseBody
    public ServerResponse<AssetInfo> getAssetByAssetId(String assetId) {
        return iAssetInfo.getAllAssetInfoById(assetId);
    }

    @RequestMapping(value = "getAssetList.do")
    @ResponseBody
    public ServerResponse<PageInfo> getAssetList(@RequestParam("searchType") String searchType,
                                                 @RequestParam("keyWord") String keyWord,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("searchType : " + searchType);
        logger.info("keyWord : " + keyWord);
        switch (searchType) {
            case SEARCH_ALL:
                return iAssetInfo.getAllAssetInfo(pageNum, pageSize);
            case SEARCH_BY_ASSET_NAME:
                return iAssetInfo.getAllAssetInfoByName(keyWord, pageNum, pageSize);
            case SEARCH_BY_ASSET_CATEGORY:
                return iAssetInfo.getAllAssetInfoByCategory(keyWord, pageNum, pageSize);
            default:
                return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
    }

    @RequestMapping(value = "getAssetSelect.do")
    @ResponseBody
    public ServerResponse<PageInfo> getAssetSelect(AssetInfo assetInfo,
                                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iAssetInfo.getAssetInfoSelect(assetInfo, pageNum, pageSize);
    }

    @RequestMapping(value = "addAssetItem.do")
    @ResponseBody
    public ServerResponse<String> addAssetItem(AssetInfo assetInfo) {
        return iAssetInfo.addAssetItem(assetInfo);
    }

    @RequestMapping(value = "updateAssetInfo.do")
    @ResponseBody
    public ServerResponse<String> updateAsseInfo(AssetInfo assetInfo) {
        return iAssetInfo.updateItem(assetInfo);
    }

    @RequestMapping(value = "deleteItem.do")
    @ResponseBody
    public ServerResponse<String> deleteItem(String assetId) {
        return iAssetInfo.deleteItem(assetId);
    }


    @RequestMapping(value = "printQRcode.do")
    @ResponseBody
    public ServerResponse<String> printQRcode(String assetId, String assetName) {
        return iAssetPrinter.assetPrint(assetId, assetName);
    }

    /**
     * 导入库存信息
     *
     * @param file 保存有库存信息的文件
     * @return 返回一个map，其中：key 为 result表示操作的结果，包括：success 与
     * error；key为total表示导入的总条数；key为available表示有效的条数
     */
    @RequestMapping(value = "importAssetRecord.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> importStorageRecord(@RequestParam("file") MultipartFile file) {
        return iAssetInfo.addAssetMutliItem(file);
    }

    /**
     * 导出资产信息
     *
     * @param assetInfo
     * @param request
     * @param response
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "exportStorageRecord.do", method = RequestMethod.GET)
    public void exportStorageRecord(AssetInfo assetInfo, HttpServletRequest request, HttpServletResponse response) {
        iAssetInfo.exportAssetInfo(assetInfo, request, response);
    }


    /**
     * 清空库存盘点数量
     * @return
     */
    @RequestMapping(value = "clearInventoryAmount.do")
    @ResponseBody
    public ServerResponse<String> clearInventoryAmount() {
        return iAssetInfo.clearInventoryAmount();

    }

    /**
     * 盘点库存，对相应资产编号的资产更新盘点数量
     * @param assetId
     * @return
     */
    @RequestMapping(value = "assetsInventory.do")
    @ResponseBody
    public ServerResponse<String> assetsInventory(String assetId) {
        return iAssetInfo.assetsInventory(assetId);
    }

    /**
     * 获取已经完成盘点的资产信息
     * @param assetInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getCompleteInventory.do")
    @ResponseBody
    public ServerResponse<PageInfo> getCompleteInventory(AssetInfo assetInfo,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iAssetInfo.getCompleteInventory(assetInfo, pageNum, pageSize);
    }


    /**
     * 获取未完成盘点的资产信息
     * @param assetInfo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "getNotCompleteInventory.do")
    @ResponseBody
    public ServerResponse<PageInfo> getNotCompleteInventory(AssetInfo assetInfo,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iAssetInfo.getNotCompleteInventory(assetInfo, pageNum, pageSize);
    }


    @RequestMapping(value = "getAssetRecycle.do")
    @ResponseBody
    public ServerResponse<PageInfo> getAssetRecycle(AssetInfo assetInfo,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iAssetRecycle.selectAll(assetInfo, pageNum, pageSize);
    }

    @RequestMapping(value = "deleteAssetRecycleItem.do")
    @ResponseBody
    public ServerResponse<String> deleteAssetRecycleItem(String assetId) {
        return iAssetRecycle.deleteItem(assetId);
    }

    @RequestMapping(value = "deleteAssetRecycleMultiItem.do")
    @ResponseBody
    public ServerResponse<List<String>> deleteAssetRecycleMultiItem(@RequestBody List<String> assetIdList) {
        System.out.println(assetIdList);
        return iAssetRecycle.deleteAssetRecycleMultiItem(assetIdList);
    }

    @RequestMapping(value = "notExistItemInAssetTable.do")
    @ResponseBody
    public ServerResponse<String> notExistItemInAssetTable(String assetId) {
        return iAssetRecycle.notExistItemInAssetTable(assetId);
    }

    @RequestMapping(value = "recycleAssetItem.do")
    @ResponseBody
    public ServerResponse<String> recycleAssetItem(String assetId) {
        return iAssetRecycle.recycleAssetItem(assetId);
    }

    @RequestMapping(value = "notExistMultiItemInAssetTable.do")
    @ResponseBody
    public ServerResponse<List<String>> notExistMultiItemInAssetTable(@RequestBody List<String> assetIdList) {
        for (String item : assetIdList) {
            System.out.println(item);
        }
        return iAssetRecycle.notExistMultiItemInAssetTable(assetIdList);
    }

    @RequestMapping(value = "recycleAssetMultiItem.do")
    @ResponseBody
    public ServerResponse<String> recycleAssetMultiItem(@RequestBody List<String> assetIdList) {
        return iAssetRecycle.recycleAssetMultiItem(assetIdList);
    }

}
