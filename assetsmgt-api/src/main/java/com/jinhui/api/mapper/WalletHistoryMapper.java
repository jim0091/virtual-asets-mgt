package com.jinhui.api.mapper;

import com.jinhui.api.entity.po.WalletHistory;

public interface WalletHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletHistory record);

    int insertSelective(WalletHistory record);

    WalletHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletHistory record);

    int updateByPrimaryKey(WalletHistory record);
}