package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.dao.DepartmentGroupMapper;
import com.mmall.pojo.AssetInfo;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.IDepartmentService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Service("iDepartment")
class DepartmentServiceImpl implements IDepartmentService {

    private Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    private DepartmentGroupMapper departmentGroupMapper;

    @Override
    public ServerResponse<PageInfo> getDepartmentList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DepartmentGroup> departmentGroupList = departmentGroupMapper.selectAll();
        if(CollectionUtils.isEmpty(departmentGroupList)) {
            logger.info("未找到集团部门信息");
        }
        PageInfo pageResult =new PageInfo(departmentGroupList);
        pageResult.setList(departmentGroupList);
        return ServerResponse.createBySuccess(pageResult);
    }

    @Override
    public ServerResponse<String> addDepartmentItem(DepartmentGroup departmentGroup) {
        if(departmentGroupMapper.checkDepartmentName(departmentGroup.getDepartmentName()) != 0) {
            return ServerResponse.createByErrorMessage("集团部门已存在");
        }
        int resultCount = departmentGroupMapper.insertSelective(departmentGroup);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加集团部门信息失败");
        }
        return ServerResponse.createBySuccessMessage("添加集团部门信息成功");
    }

    @Override
    public ServerResponse<String> updateDepartmentItem(DepartmentGroup departmentGroup) {
        if(departmentGroupMapper.checkDepartmentName(departmentGroup.getDepartmentName()) != 0) {
            return ServerResponse.createByErrorMessage("集团部门已存在");
        }
        int updateCount = departmentGroupMapper.updateByPrimaryKeySelective(departmentGroup);
        if(updateCount == 0) {
            return ServerResponse.createByErrorMessage("更新集团部门信息失败");
        }
        return ServerResponse.createBySuccessMessage("更新集团部门信息成功");
    }

    @Override
    public ServerResponse<String> deleteDepartmentItem(int departmentId) {
        int deleteCount = departmentGroupMapper.deleteByPrimaryKey(departmentId);
        if(deleteCount == 0) {
            return ServerResponse.createByErrorMessage("删除集团部门信息失败");
        } else {
            return ServerResponse.createBySuccessMessage("删除集团部门信息成功");
        }
    }
}
