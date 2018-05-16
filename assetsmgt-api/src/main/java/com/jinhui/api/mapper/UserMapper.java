package com.jinhui.api.mapper;


import com.jinhui.api.entity.po.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    int insertSelective(User record);


    int updateByUserId(User record);


    User selectBySective(User user);
}