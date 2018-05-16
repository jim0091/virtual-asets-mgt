package com.jinhui.api.mapper;

import com.jinhui.api.entity.po.WalletTx;

import java.math.BigDecimal;
import java.util.Date;

public interface WalletTxMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WalletTx record);

    int insertSelective(WalletTx record);

    WalletTx selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WalletTx record);

    int updateByPrimaryKey(WalletTx record);


    /**
     * 根据交易记录计算出某个时间的eth的钱包余额
     */

    BigDecimal selectEthBalance(Date date);

    /**
     * 查询出eth的钱包交易总数
     */
    int selectEthCount();


}