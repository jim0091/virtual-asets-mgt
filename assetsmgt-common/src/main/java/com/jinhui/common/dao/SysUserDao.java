package com.jinhui.common.dao;

import com.jinhui.common.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
@Component
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
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
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);

	List<SysUserEntity> querySearchList(@Param("userId") String userid,@Param("username") String username);

	/**
	 * 查询拥有经济人角色的后台用户列表
	 * @return
	 */
	List<SysUserEntity> queryAgentList(@Param("sysUserId") String sysUserId);

	/**
	 * 查询拥有经济人角色的后台用户列表
	 * @return
	 */
	List<SysUserEntity> queryAgentListBySelective(@Param("userName") String userName);

	SysUserEntity querySysUserById(Long sysUserId);


}
