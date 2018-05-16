package com.jinhui.common.dao;

import com.jinhui.common.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AccountDao {

    /**
     * 用户资金账号列表
     * @param userName
     * @param userId
     * @return
     */
    List<Account> queryListBySelective(@Param("userName") String userName, @Param("userId") String userId, @Param("sysUserId") String sysUserId);


    /**
     * 根据经纪人ID查所属用户列表
     * @param userName  用户姓名
     * @param userId   用户ID
     * @param sysUserName  经纪人姓名
     * @param sysUserId   经纪人ID
     * @return
     */
    List<Account> queryListBySysUserId(@Param("userName") String userName, @Param("userId") String userId,
                                       @Param("sysUserName") String sysUserName, @Param("sysUserId") String sysUserId);
}