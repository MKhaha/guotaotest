package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.CategoryAsset;
import com.mmall.service.ICategoryAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
@RequestMapping("/manage/assetCategory")
public class CategoryManageController {

    @Autowired
    private ICategoryAssetService iCategory;

    @RequestMapping(value = "getCategory.do")
    @ResponseBody
    public ServerResponse<PageInfo> getCategory(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iCategory.getCategoryList(pageNum, pageSize);
    }

    @RequestMapping(value = "addCategory.do")
    @ResponseBody
    public ServerResponse<String> addCategory(CategoryAsset categoryAsset) {
        return iCategory.addCategoryItem(categoryAsset);
    }

    @RequestMapping(value = "updateCategory.do")
    @ResponseBody
    public ServerResponse<String> updateCategory(CategoryAsset categoryAsset) {
        return iCategory.updateCategoryItem(categoryAsset);
    }

    @RequestMapping(value = "deleteCategory.do")
    @ResponseBody
    public ServerResponse<String> deleteCategory(int categoryId) {
        return iCategory.deleteCategoryItem(categoryId);
    }
}
