package com.jinhui.common.service;

import com.github.pagehelper.PageInfo;
import com.jinhui.common.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	PageInfo<SysRoleEntity> queryList(Integer pageNum, Integer pageSize);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

	void deleteRole(String roleId);

	int countRoleUser(String roleId);

}
