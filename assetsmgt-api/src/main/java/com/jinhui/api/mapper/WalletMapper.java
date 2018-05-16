package com.jinhui.api.mapper;

import com.jinhui.api.entity.po.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);


    /**
     * 查询eth的钱包信息
     */
    Wallet selectByEth();

}