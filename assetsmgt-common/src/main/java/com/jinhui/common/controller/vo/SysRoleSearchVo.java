package com.jinhui.common.controller.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @autor wsc
 * @create 2018-04-10 15:57
 **/
public class SysRoleSearchVo {

    private Integer pageNum;

    private Integer pageSize;


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
