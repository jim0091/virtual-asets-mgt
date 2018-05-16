package com.jinhui.api.entity.vo;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/4 0004.
 */
public class TransQueryParam {

    private Date transTimeStart;

    private Date transTimeEnd;

    private String transType;

    private String userName;

    private String userId;


    private Integer pageNum;

    private Integer pageSize;

    public Date getTransTimeStart() {
        return transTimeStart;
    }

    public void setTransTimeStart(Date transTimeStart) {
        this.transTimeStart = transTimeStart;
    }

    public Date getTransTimeEnd() {
        return transTimeEnd;
    }

    public void setTransTimeEnd(Date transTimeEnd) {
        this.transTimeEnd = transTimeEnd;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
