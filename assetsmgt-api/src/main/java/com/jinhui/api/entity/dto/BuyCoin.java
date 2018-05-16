package com.jinhui.api.entity.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/3/26 0026.
 */
public class BuyCoin {

    @NotNull(message = "产品id不能为空")
    private String productId;

    private String productName;

    private String productType;

    /**
     * 买入价
     */
    private String buyPrice;

    /**
     * 买入量
     */
    @NotNull(message = "买入量不能为空")
    private String buyVol;

    /**
     * 交易额
     */
    private String amount;

    /**
     * 手续费
     */
    private String charge;

    /**
     * 资金密码
     */
   // @NotEmpty(message = "资金密码不能为空")
    private String passWord;

    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotEmpty(message = "用户名称不能为空")
    private String userName;

    @NotEmpty(message = "经纪人名称不能为空")
    private String operateName;

    @NotEmpty(message = "经纪人ID不能为空")
    private String operateId;


    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getBuyVol() {
        return buyVol;
    }

    public void setBuyVol(String buyVol) {
        this.buyVol = buyVol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getOperateId() {
        return operateId;
    }

    public void setOperateId(String operateId) {
        this.operateId = operateId;
    }

    @Override
    public String toString() {
        return "BuyCoin{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", buyVol='" + buyVol + '\'' +
                ", amount='" + amount + '\'' +
                ", charge='" + charge + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
