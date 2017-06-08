package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.DepartmentGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
public interface IDepartmentService {
    ServerResponse<PageInfo> getDepartmentList(int pageNum, int pageSize);
    ServerResponse<String> addDepartmentItem(DepartmentGroup departmentGroup);
    ServerResponse<String> updateDepartmentItem(DepartmentGroup departmentGroup);
    ServerResponse<String> deleteDepartmentItem(int departmentId);
}
