package com.jinhui.api.entity.dto;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/17 0017.
 */
public class QueryFundParam {

    private String userId;

    private String userName;

    private Date gatherDate;

    private int pageNum;

    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public Date getGatherDate() {
        return gatherDate;
    }

    public void setGatherDate(Date gatherDate) {
        this.gatherDate = gatherDate;
    }

    @Override
    public String toString() {
        return "QueryFundParam{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", gatherDate=" + gatherDate +
                '}';
    }
}

