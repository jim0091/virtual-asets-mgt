package com.jinhui.api.entity.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/3/27 0027.
 */
public class SellCoin {


    @NotNull(message = "产品id不能为空")
    private String productId;

    private String productName;

    private String productType;

    /**
     * 卖出价
     */
    private String sellPrice;

    /**
     * 卖出量
     */
    @NotNull(message = "卖出量不能为空")
    private String sellVol;

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

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getSellVol() {
        return sellVol;
    }

    public void setSellVol(String sellVol) {
        this.sellVol = sellVol;
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
        return "SellCoin{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType='" + productType + '\'' +
                ", sellPrice='" + sellPrice + '\'' +
                ", sellVol='" + sellVol + '\'' +
                ", amount='" + amount + '\'' +
                ", charge='" + charge + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
