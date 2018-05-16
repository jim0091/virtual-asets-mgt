package com.jinhui.common.dao;

import com.jinhui.common.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
@Mapper
@Component
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
	

	/**
	 * 获取菜单列表
	 */
	List<SysMenuEntity> queryAllList();
	
	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuEntity> queryUserList(Long userId);

	/**
	 * 查询角色的权限列表
	 * @param roleId
	 * @return
	 */
	List<SysMenuEntity> queryRoleMenuList(Long roleId);
}
