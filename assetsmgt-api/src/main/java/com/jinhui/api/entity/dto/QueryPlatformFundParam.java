package com.jinhui.api.entity.dto;

/**
 * Created by Administrator on 2018/4/24 0024.
 */
public class QueryPlatformFundParam {

    private Integer pageNum;

    private Integer pageSize;

    private String transDateStart;

    private String transDateEnd;

    private String transState;

    private String bussType;

    private String userId;

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

    public String getTransDateStart() {
        return transDateStart;
    }

    public void setTransDateStart(String transDateStart) {
        this.transDateStart = transDateStart;
    }

    public String getTransDateEnd() {
        return transDateEnd;
    }

    public void setTransDateEnd(String transDateEnd) {
        this.transDateEnd = transDateEnd;
    }

    public String getTransState() {
        return transState;
    }

    public void setTransState(String transState) {
        this.transState = transState;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }
}
