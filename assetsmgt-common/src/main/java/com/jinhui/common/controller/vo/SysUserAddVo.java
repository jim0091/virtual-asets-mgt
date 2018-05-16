package com.jinhui.common.controller.vo;

import com.jinhui.common.validator.group.AddGroup;
import com.jinhui.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户Vo
 */
public class SysUserAddVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 邮箱
	 */
	@NotBlank(message="身份证号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String idNo;

	/**
	 * 手机号
	 */
	@NotBlank(message="手机号码不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String mobile;

	/**
	 * 角色ID列表
	 */
	private String roleId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
