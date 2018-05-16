package com.jinhui.api.entity.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
public class BuyProduct {

    @NotNull(message = "产品id不能为空")
    private String productId;

    @NotNull(message = "产品名称不能为空")
    private String productName;

    @NotNull(message = "买入量不能为空")
    private String buyVol;


    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotEmpty(message = "用户名称不能为空")
    private String userName;

    @NotEmpty(message = "经纪人名称不能为空")
    private String operateName;

    @NotEmpty(message = "经纪人ID不能为空")
    private String operateId;

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

    public String getBuyVol() {
        return buyVol;
    }

    public void setBuyVol(String buyVol) {
        this.buyVol = buyVol;
    }



}
