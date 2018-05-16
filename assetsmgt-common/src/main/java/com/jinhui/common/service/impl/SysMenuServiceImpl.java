package com.jinhui.common.service.impl;

import com.jinhui.common.dao.SysMenuDao;
import com.jinhui.common.entity.SysMenuEntity;
import com.jinhui.common.service.SysMenuService;
import com.jinhui.common.service.SysUserService;
import com.jinhui.common.utils.Constant;
import com.jinhui.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;


	@Override
	public List<SysMenuEntity> queryAllList() {
		return sysMenuDao.queryAllList();
	}

	@Override
	public List<SysMenuEntity> queryUserList() {
		return sysMenuDao.queryUserList(RedisUtils.getLocalUser().getUserId());
	}

	@Override
	public List<SysMenuEntity> queryRoleMenuList(Long roleId) {
		return sysMenuDao.queryRoleMenuList(roleId);
	}
}
