package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetInfoService;
import com.mmall.service.IAssetPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ServerResponse<String> printQRcode(String assetId) {
        return iAssetPrinter.assetPrint(assetId);
    }
}
