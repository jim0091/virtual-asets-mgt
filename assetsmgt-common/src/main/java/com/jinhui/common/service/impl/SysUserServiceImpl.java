package com.jinhui.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.common.annotation.DataFilter;
import com.jinhui.common.constants.CommonEnum;
import com.jinhui.common.dao.SysUserDao;
import com.jinhui.common.entity.SysUserEntity;
import com.jinhui.common.service.SysUserRoleService;
import com.jinhui.common.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	@Override
	public SysUserEntity queryByMobile(String mobile) {
		return sysUserDao.queryByMobile(mobile);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	@DataFilter(tableAlias = "u", user = false)
	public PageInfo<SysUserEntity> queryList(String userId,String username,Integer pageNum,Integer pageSize){
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);

		List<SysUserEntity> list = sysUserDao.querySearchList(userId,username);

		return new PageInfo<SysUserEntity>(list);
	}
	
	@Override
	@DataFilter(tableAlias = "u", user = false)
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(CommonEnum.DEFAULT_PASS, salt).toHex());
		user.setSalt(salt);
		user.setStatus(CommonEnum.USER_STATUS_NORMAL);
		sysUserDao.save(user);
		
		//保存用户与角色关系
		if(user.getRoleId()!= null && user.getRoleId() != "" ){
			List list = new ArrayList();
			list.add(user.getRoleId());
			sysUserRoleService.saveOrUpdate(user.getUserId(), list);
		}
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotEmpty(user.getPassword())){
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		sysUserDao.update(user);
		
		//保存用户与角色关系
		if(user.getRoleId()!= null && user.getRoleId() != "" ) {
			List list = new ArrayList();
			list.add(user.getRoleId());
			sysUserRoleService.saveOrUpdate(user.getUserId(), list);
		}
	}

	@Override
	@Transactional
	public void delete(Long userId) {
		sysUserDao.delete(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}

	@Override
	public List<SysUserEntity> queryAgentList(String sysUserId) {
		return sysUserDao.queryAgentList(sysUserId);
	}

	@Override
	public PageInfo<SysUserEntity> queryAgentListBySelective(String userName,Integer pageNum,Integer pageSize) {
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNum, pageSize);

		List<SysUserEntity> list = sysUserDao.queryAgentListBySelective(userName);

		return new PageInfo<>(list);
	}

	@Override
	public int updatePassword(Map<String, Object> map) {
		return sysUserDao.updatePassword(map);
	}

}
