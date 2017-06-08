package com.mmall.dao;

import com.mmall.pojo.DepartmentGroup;

import java.util.List;

public interface DepartmentGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DepartmentGroup record);

    int insertSelective(DepartmentGroup record);

    DepartmentGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DepartmentGroup record);

    int updateByPrimaryKey(DepartmentGroup record);

    List<DepartmentGroup> selectAll();

    int checkDepartmentName(String departmentName);
}