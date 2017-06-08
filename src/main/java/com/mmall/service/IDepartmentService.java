package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.vo.DepartmentVo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface IDepartmentService {
    ServerResponse<List<DepartmentVo>> getDepartmentList();
    ServerResponse<String> addDepartmentItem(String departmentName);
    ServerResponse<String> updateDepartmentItem(DepartmentVo departmentVo);
    ServerResponse<String> deleteDepartmentItem(int departmentId);
}
