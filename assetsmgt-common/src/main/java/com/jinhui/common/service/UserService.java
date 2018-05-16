package com.jinhui.common.service;

import com.github.pagehelper.PageInfo;
import com.jinhui.common.entity.Account;
import com.jinhui.common.entity.ChangeAgentLog;
import com.jinhui.common.entity.User;

import java.util.Map;

/**
 * 后台系统投资用户
 *
 * @autor wsc
 * @create 2018-04-11 14:44
 **/
public interface UserService {

    User queryUserByUserId(String userId);

    /**
     * 投资用户列表
     * @param userName
     * @param userId
     * @param sysUserId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> queryListBySelective(String userName, String userId, String sysUserId, Integer pageNum, Integer pageSize);

    /**
     * 绑定，更换经济人
      * @param userId
     * @param sysUserId
     * @return
     */
    int changeAgent(String userId,String sysUserId);

    /**
     * 解绑经纪人
     * @param userId
     * @return
     */
    int unBindAgent(String userId);

    /**
     * 用户资金列表
     * @param userName
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Account> queryUserListBySelective(String userName, String userId, String sysUserId,Integer pageNum, Integer pageSize);

    /**
     *
     * @param userName
     * @param userId
     * @param sysUserName
     * @param sysUserId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Account> queryListBySysUserId(String userName, String userId,String sysUserName, String sysUserId, Integer pageNum, Integer pageSize);


    int addChangeAgentLog(ChangeAgentLog changeAgentLog);

}
