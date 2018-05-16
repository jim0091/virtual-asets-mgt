package com.jinhui.common.dao;


import com.jinhui.common.entity.ChangeAgentLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChangeAgentLogDao {


    int insertSelective(ChangeAgentLog record);

}