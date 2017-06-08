package com.mmall.controller;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.DepartmentGroupMapper;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ServerResponse<PageInfo> getDepartment(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return iDepartment.getDepartmentList(pageNum, pageSize);
    }

    @RequestMapping(value = "addDepartment.do")
    @ResponseBody
    public ServerResponse<String> addDepartment(DepartmentGroup departmentGroup) {
        return iDepartment.addDepartmentItem(departmentGroup);
    }

    @RequestMapping(value = "updateDepartment.do")
    @ResponseBody
    public ServerResponse<String> updateDepartment(DepartmentGroup departmentGroup) {
        return iDepartment.updateDepartmentItem(departmentGroup);
    }

    @RequestMapping(value = "deleteDepartment.do")
    @ResponseBody
    public ServerResponse<String> deleteDepartment(int departmentId) {
        return iDepartment.deleteDepartmentItem(departmentId);
    }

}
