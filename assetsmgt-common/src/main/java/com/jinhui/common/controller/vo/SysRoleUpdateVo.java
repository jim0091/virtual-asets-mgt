package com.jinhui.common.controller.vo;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @autor wsc
 * @create 2018-04-10 15:57
 **/
public class SysRoleUpdateVo {

    /**
     * 角色名称
     */
    @NotBlank(message="角色Id不能为空")
    private String roleId;

    private List<Long> menuIdList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
