package com.jinhui.common.service;

import com.github.pagehelper.PageInfo;
import com.jinhui.common.controller.vo.SysUserSearchVo;
import com.jinhui.common.entity.SysUserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
@Component
public interface SysUserService {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 根据手机号，查询系统用户
	 */
	SysUserEntity queryByMobile(String mobile);
	
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	PageInfo<SysUserEntity> queryList(String userId,String username,Integer pageNum,Integer pageSize);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void delete(Long userId);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);

	/**
	 * 查询拥有经济人角色的后台用户列表
	 * @return
	 */
	List<SysUserEntity> queryAgentList(String sysUserId);

	/**
	 * 查询拥有经济人角色的后台用户列表
	 * @return
	 */
	PageInfo<SysUserEntity> queryAgentListBySelective(String userName,Integer pageNum,Integer pageSize);

	/**
	 * 修改密码
	 * @param map
	 * @return
	 */
	int updatePassword(Map<String, Object> map);
}
