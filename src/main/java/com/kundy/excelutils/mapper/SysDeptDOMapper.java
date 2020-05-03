package com.kundy.excelutils.mapper;

import com.kundy.excelutils.entity.po.SysDeptDO;

/**
 * @Author: yangyl
 * @Date: 2020-05-03 23:10
 * @Description:
 */
public interface SysDeptDOMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDeptDO record);

    int insertSelective(SysDeptDO record);

    SysDeptDO selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDeptDO record);

    int updateByPrimaryKey(SysDeptDO record);
}