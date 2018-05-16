package com.jinhui.common.dao;

import com.jinhui.common.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
@Component
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	

    List<SysRoleEntity> querySearchList(@Param("pageNum") Integer pageNum,@Param("pageSize") Integer pageSize);

    void deleteRole(@Param("roleId") String roleId);

    int countRoleUser(@Param("roleId") String roleId);
}
