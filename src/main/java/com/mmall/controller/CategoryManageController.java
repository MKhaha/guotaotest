package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.CategoryAsset;
import com.mmall.service.ICategoryAssetService;
import com.mmall.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public ServerResponse<List<CategoryVo>> getCategory() {
        return iCategory.getCategoryList();
    }

    @RequestMapping(value = "addCategory.do")
    @ResponseBody
    public ServerResponse<String> addCategory(String categoryName) {
        return iCategory.addCategoryItem(categoryName);
    }

    @RequestMapping(value = "updateCategory.do")
    @ResponseBody
    public ServerResponse<String> updateCategory(CategoryVo categoryVo ) {
        return iCategory.updateCategoryItem(categoryVo);
    }

    @RequestMapping(value = "deleteCategory.do")
    @ResponseBody
    public ServerResponse<String> deleteCategory(Integer id) {
        if (id == null) {
            return ServerResponse.createByErrorMessage("参数为空");
        }
        return iCategory.deleteCategoryItem(id);
    }
}
