package com.jinhui.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinhui.common.dao.AccountDao;
import com.jinhui.common.dao.ChangeAgentLogDao;
import com.jinhui.common.dao.SysUserDao;
import com.jinhui.common.entity.Account;
import com.jinhui.common.entity.ChangeAgentLog;
import com.jinhui.common.entity.SysUserEntity;
import com.jinhui.common.entity.User;
import com.jinhui.common.dao.UserDao;
import com.jinhui.common.service.SysUserService;
import com.jinhui.common.service.UserService;
import com.jinhui.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @autor wsc
 * @create 2018-04-11 14:45
 **/
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ChangeAgentLogDao changeAgentLogDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public User queryUserByUserId(String userId) {
        return userDao.queryUserById(userId);
    }

    @Override
    public PageInfo<User> queryListBySelective(String userName, String userId, String sysUserId, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<User> list = userDao.selectSective(userName,userId,sysUserId);

        return new PageInfo<>(list);
    }

    @Override
    public int changeAgent(String userId, String sysUserId) {
        User user = userDao.queryUserById(userId);

        SysUserEntity sysUserEntity = sysUserDao.querySysUserById(Long.parseLong(sysUserId));
        //记录绑定经纪人日志
        ChangeAgentLog changeAgentLog = new ChangeAgentLog();
        changeAgentLog.setUserId(user.getUserId());
        changeAgentLog.setUserName(user.getUserName());
        changeAgentLog.setSysUserId(String.valueOf(sysUserEntity.getUserId()));
        changeAgentLog.setSysUserName(sysUserEntity.getUsername());
        changeAgentLog.setType("0");
        changeAgentLog.setOperateId(String.valueOf(RedisUtils.getLocalUser().getUserId()));
        changeAgentLogDao.insertSelective(changeAgentLog);

        return userDao.updateAgent(userId,sysUserId);
    }

    @Override
    public int unBindAgent(String userId) {
        return userDao.unBindAgent(userId);
    }

    @Override
    public PageInfo<Account> queryUserListBySelective(String userName, String userId, String sysUserId,Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<Account> list = accountDao.queryListBySelective(userName,userId,sysUserId);

        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Account> queryListBySysUserId(String userName, String userId, String sysUserName, String sysUserId, Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(pageNum, pageSize);

        List<Account> list = accountDao.queryListBySysUserId(userName,userId,sysUserName,sysUserId);

        return new PageInfo<>(list);
    }

    @Override
    public int addChangeAgentLog(ChangeAgentLog changeAgentLog) {
        return changeAgentLogDao.insertSelective(changeAgentLog);
    }
}
