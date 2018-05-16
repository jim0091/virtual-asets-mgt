package com.jinhui.common.controller.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @autor wsc
 * @create 2018-04-10 15:57
 **/
public class SysRoleAddVo {

    /**
     * 角色名称
     */
    @NotBlank(message="角色名称不能为空")
    private String roleName;

    private List<Long> menuIdList;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
