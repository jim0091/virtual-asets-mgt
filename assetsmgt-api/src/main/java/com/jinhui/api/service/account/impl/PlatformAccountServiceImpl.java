package com.jinhui.api.service.account.impl;


import com.jinhui.api.constants.MarketAcctEnum;
import com.jinhui.api.entity.po.PlatformAccount;
import com.jinhui.api.mapper.PlatformAccountMapper;
import com.jinhui.api.service.account.PlatformAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @autor wsc
 * @create 2018-03-26 17:40
 **/
@Service("platformAccountService")
public class PlatformAccountServiceImpl implements PlatformAccountService {

    @Autowired
    private PlatformAccountMapper platformAccountMapper;

    public List<PlatformAccount> queryAllAccount() {
        return platformAccountMapper.selectAllAccount();
    }

    public PlatformAccount queryAccountByType(String accountType) {
        return platformAccountMapper.selectByType(accountType);
    }

    @Override
    public int subtractPosition(MarketAcctEnum marketAcctEnum, BigDecimal amount) {
        return platformAccountMapper.subtractPosition(marketAcctEnum.getAcctCode(), amount);
    }

    @Override
    public int addPosition(MarketAcctEnum marketAcctEnum, BigDecimal amount) {
        return platformAccountMapper.addPosition(marketAcctEnum.getAcctCode(), amount);
    }

    @Override
    public int addCharge(String AcctType, BigDecimal amount) {
        return platformAccountMapper.addPosition(AcctType, amount);
    }

    @Override
    public int addPosition(String AcctType, BigDecimal amount) {
        return platformAccountMapper.addPosition(AcctType, amount);
    }
}
