package com.jinhui.api.entity.vo;

/**
 * Created by Administrator on 2018/4/26 0026.
 */
public class PlatformFundVo {


    private String userName;
    private String userId;
    private String accountName;
    private String totalFund;//总资产


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(String totalFund) {
        this.totalFund = totalFund;
    }
}
