package com.jinhui.api.entity.vo;

/**
 * Created by Administrator on 2018/4/27 0027.
 */
public class CheckPlatformVo {

    private String accountName;

    private String walletBalance; //今日钱包资产

    private String fundFlow; //上日钱包资产+今日资产进出

    private String FundDiff; //差额

    private  int applyCount; //平台充值提现申请总数

    private int transCount;  //钱包交易记录总数

    private int countDiff;//差额


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getFundFlow() {
        return fundFlow;
    }

    public void setFundFlow(String fundFlow) {
        this.fundFlow = fundFlow;
    }

    public String getFundDiff() {
        return FundDiff;
    }

    public void setFundDiff(String fundDiff) {
        FundDiff = fundDiff;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public int getTransCount() {
        return transCount;
    }

    public void setTransCount(int transCount) {
        this.transCount = transCount;
    }

    public int getCountDiff() {
        return countDiff;
    }

    public void setCountDiff(int countDiff) {
        this.countDiff = countDiff;
    }
}
