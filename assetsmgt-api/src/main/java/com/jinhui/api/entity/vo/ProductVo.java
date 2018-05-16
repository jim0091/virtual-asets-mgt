package com.jinhui.api.entity.vo;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public class ProductVo {

    private String productId;

    private String productType;

    private String productName;

    private Date createDate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
