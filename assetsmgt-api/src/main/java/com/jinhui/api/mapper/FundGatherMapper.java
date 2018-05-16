package com.jinhui.api.mapper;

import com.jinhui.api.entity.po.FundGather;
import org.apache.ibatis.annotations.Param;

public interface FundGatherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FundGather record);

    int insertSelective(FundGather record);

    FundGather selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FundGather record);

    int updateByPrimaryKey(FundGather record);

    int updateByUserId(FundGather record);

    FundGather selectByUserId(@Param("userId") String userId);
}