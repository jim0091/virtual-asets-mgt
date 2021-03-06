package com.jinhui.api.entity.vo;

/**
 * Created by Administrator on 2018/4/4 0004.
 */
public class UserFundVo {

    private String userName;
    private String userId;
    private String userAccount;
    private String FundName;//资产配置
    private String totalFund;//总资产
    private String fundBalance;//可用资产
    private String investFund;//在投资产
    private String totalAmount;//总市值

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

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

    public String getFundName() {
        return FundName;
    }

    public void setFundName(String fundName) {
        FundName = fundName;
    }

    public String getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(String totalFund) {
        this.totalFund = totalFund;
    }

    public String getFundBalance() {
        return fundBalance;
    }

    public void setFundBalance(String fundBalance) {
        this.fundBalance = fundBalance;
    }

    public String getInvestFund() {
        return investFund;
    }

    public void setInvestFund(String investFund) {
        this.investFund = investFund;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
