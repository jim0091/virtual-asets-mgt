package com.jinhui.api.constants;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public enum  CoinInfo {
    btc("BTC", "cp001"),
    eth("ETH", "cp002"),
    ltc("LTC", "cp003"),
    usdt("USDT", "cp004"),
    eos("EOS","cp005");

    private String name;

    private String code;


    CoinInfo(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
