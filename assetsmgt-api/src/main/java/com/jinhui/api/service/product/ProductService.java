package com.jinhui.api.service.product;

import com.jinhui.api.entity.dto.BuyProduct;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
public interface
ProductService {

    void buyProduct(BuyProduct buyProduct);



    //到期赎回
    void redeem();
}
