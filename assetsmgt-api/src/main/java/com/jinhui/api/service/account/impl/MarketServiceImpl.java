package com.jinhui.api.service.account.impl;

import com.jinhui.api.entity.po.Market;
import com.jinhui.api.mapper.MarketMapper;
import com.jinhui.api.service.account.MarketServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @autor wsc
 * @create 2018-03-28 14:18
 **/
@Service("marketServcie")
public class MarketServiceImpl implements MarketServcie {

    @Autowired
    private MarketMapper marketMapper;

    @Override
    public Market selectByProductName(String productName) {
        return marketMapper.selectByProductName(productName);
    }
}
