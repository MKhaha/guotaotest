package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.DepartmentGroupMapper;
import com.mmall.pojo.DepartmentGroup;
import com.mmall.service.IDepartmentService;
import com.mmall.vo.DepartmentVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ServerResponse<List<DepartmentVo>> getDepartmentList() {
        List<DepartmentVo> departmentVoList = new ArrayList<>();
        List<DepartmentGroup> departmentGroupList = departmentGroupMapper.selectAll();
        if(CollectionUtils.isEmpty(departmentGroupList)) {
            logger.info("未找到集团部门信息");
        } else {
            for (DepartmentGroup departmentGroupItem : departmentGroupList) {
                DepartmentVo departmentVoItme = new DepartmentVo();

                departmentVoItme.setId(departmentGroupItem.getId());
                departmentVoItme.setDepartmentName(departmentGroupItem.getDepartmentName());
                departmentVoList.add(departmentVoItme);
            }
        }
        return ServerResponse.createBySuccess(departmentVoList);

    }

    @Override
    public ServerResponse<String> addDepartmentItem(String departmentName) {
        System.out.println(departmentName);
        if(org.apache.commons.lang3.StringUtils.isBlank(departmentName)) {
            return ServerResponse.createByErrorMessage("字符串无意义");
        }
        if(departmentGroupMapper.checkDepartmentName(departmentName) != 0) {
            return ServerResponse.createByErrorMessage("集团部门已存在");
        }
        DepartmentGroup departmentGroup = new DepartmentGroup();
        departmentGroup.setDepartmentName(departmentName);
        int resultCount = departmentGroupMapper.insertSelective(departmentGroup);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("添加集团部门信息失败");
        }
        return ServerResponse.createBySuccessMessage("添加集团部门信息成功");
    }

    @Override
    public ServerResponse<String> updateDepartmentItem(DepartmentVo departmentVo) {
        System.out.println(departmentVo.getDepartmentName());
        if(org.apache.commons.lang3.StringUtils.isBlank(departmentVo.getDepartmentName())) {
            return ServerResponse.createByErrorMessage("字符串无意义");
        }
        if(departmentGroupMapper.checkDepartmentName(departmentVo.getDepartmentName()) != 0) {
            return ServerResponse.createByErrorMessage("集团部门已存在");
        }
        DepartmentGroup departmentGroup = departmentGroupMapper.selectByPrimaryKey(departmentVo.getId());
        if(departmentVo.getDepartmentName().equals(departmentGroup.getDepartmentName())) {
            return ServerResponse.createByErrorMessage("未更改部门名称");
        }
        departmentGroup.setDepartmentName(departmentVo.getDepartmentName());
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
