package com.jinhui.common.dao;


import com.jinhui.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    User queryUserById(String userId);

    /**
     * 后台系统投资用户列表查询
     * @param userName
     * @param userId
     * @param sysUserId
     * @return
     */
    List<User> selectSective(@Param("userName") String userName, @Param("userId") String userId, @Param("sysUserId") String sysUserId);

    /**
     * 绑定，解绑，更换经纪人
     * @param userId
     * @param sysUserId
     * @return
     */
    int updateAgent(@Param("userId") String userId,@Param("sysUserId") String sysUserId);

    /**
     * 解绑经纪人
     * @param userId
     * @return
     */
    int unBindAgent(@Param("userId") String userId);
}