package com.jinhui.common.controller.vo;

/**
 * @autor wsc
 * @create 2018-04-11 15:02
 **/
public class AgentMgtSearchVo {

    private Integer pageNum;

    private Integer pageSize;

    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
