package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.DepartmentGroupMapper;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.IDepartmentService;
import com.mmall.vo.DepartmentVo;
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
@RequestMapping("/manage/department")
public class DepartmentManageController {

    @Autowired
    private IDepartmentService iDepartment;

    @RequestMapping(value = "getDepartment.do")
    @ResponseBody
    public ServerResponse<List<DepartmentVo>> getDepartment() {
        return iDepartment.getDepartmentList();
    }

    @RequestMapping(value = "addDepartment.do")
    @ResponseBody
    public ServerResponse<String> addDepartment(String departmentName) {
        return iDepartment.addDepartmentItem(departmentName);
    }

    @RequestMapping(value = "updateDepartment.do")
    @ResponseBody
    public ServerResponse<String> updateDepartment(DepartmentVo departmentVo) {
        return iDepartment.updateDepartmentItem(departmentVo);
    }

    @RequestMapping(value = "deleteDepartment.do")
    @ResponseBody
    public ServerResponse<String> deleteDepartment(int departmentId) {
        return iDepartment.deleteDepartmentItem(departmentId);
    }

}
