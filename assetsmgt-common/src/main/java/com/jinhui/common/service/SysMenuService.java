package com.jinhui.common.service;


import com.jinhui.common.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;


/**
 * 菜单管理
 */
public interface SysMenuService {

	/**
	 * 获取所有的菜单列表
	 * @return
	 */
	List<SysMenuEntity> queryAllList();

	/**
	 * 获取用户的权限列表
	 * @return
	 */
	List<SysMenuEntity> queryUserList();

	List<SysMenuEntity> queryRoleMenuList(Long roleId);


}
