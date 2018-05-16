package com.jinhui.common.controller.vo;

import com.jinhui.common.validator.group.AddGroup;
import com.jinhui.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户Vo
 */
public class SysUserUpdateVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message="用户Id不能为空", groups = {UpdateGroup.class})
	private String userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 身份证号
	 */
	private String idNo;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 角色ID列表
	 */
	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
