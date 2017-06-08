package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.AssetInfo;
import com.mmall.service.IAssetInfoService;
import com.mmall.service.IAssetPrinter;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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


//    @SuppressWarnings("unchecked")
//    @RequestMapping(value = "exportStorageRecord.do", method = RequestMethod.GET)
//    public ServerResponse<String> exportStorageRecord(@RequestParam("searchType") String searchType,
//                                    @RequestParam("keyword") String keyword,
//                                    @RequestParam(value = "departmentResponsibility", required = false) String departmentResponsibility,
//                                    HttpServletRequest request, HttpServletResponse response) {
//        String fileName = "assetRecord.xlsx";
//        try {
//            HttpSession session = request.getSession();
//            String sessionDepartmentResponsibility = (String) session.getAttribute("departmentResponsibility");
//            if (StringUtils.isNotBlank(sessionDepartmentResponsibility)) {
//                departmentResponsibility = sessionDepartmentResponsibility;
//            }
//
//            List<AssetInfo> assetInfoList = getAssetListNoPage(searchType, keyword);
//            if(assetInfoList != null) {
//                File file = iAssetInfo.exportAssetMutilItem(assetInfoList);
//                if(file != null) {
//                    // 设置响应头
//                    response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//                    FileInputStream inputStream = new FileInputStream(file);
//                    OutputStream outputStream = response.getOutputStream();
//                    byte[] buffer = new byte[8192];
//
//                    int len;
//                    while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
//                        outputStream.write(buffer, 0, len);
//                        outputStream.flush();
//                    }
//
//                    inputStream.close();
//                    outputStream.close();
//                }
//            } else {
//                return ServerResponse.createByErrorMessage("")
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ServerResponse.createByErrorMessage("导出数据失败");
//        }
//    }

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

}
