package com.kundy.excelutils.mapper;

import com.kundy.excelutils.entity.po.SysUserDO;

 /**
  * @Author: yangyl
  * @Date: 2020-05-03 23:10
  * @Description: 
 */
public interface SysUserDOMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUserDO record);

    int insertSelective(SysUserDO record);

    SysUserDO selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUserDO record);

    int updateByPrimaryKey(SysUserDO record);
}