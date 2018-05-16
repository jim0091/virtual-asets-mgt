package com.jinhui.common.controller;


import com.github.pagehelper.PageInfo;
import com.jinhui.common.annotation.JsonParam;
import com.jinhui.common.annotation.SysLog;
import com.jinhui.common.controller.vo.SysRoleAddVo;
import com.jinhui.common.controller.vo.SysRoleSearchVo;
import com.jinhui.common.controller.vo.SysRoleUpdateVo;
import com.jinhui.common.entity.SysMenuEntity;
import com.jinhui.common.entity.SysRoleEntity;
import com.jinhui.common.service.SysMenuService;
import com.jinhui.common.service.SysRoleMenuService;
import com.jinhui.common.service.SysRoleService;
import com.jinhui.common.utils.*;
import com.jinhui.common.validator.ValidatorUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 角色列表
	 */
	@ApiOperation(value="角色列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysRoleSearchVo", value = "角色列表条件vo", required = true, dataType = "SysRoleSearchVo"),
	})
	@PostMapping("/list")
	@RequiresPermissions("sys:role:list")
	public ResultResp list(@RequestBody SysRoleSearchVo sysRoleSearchVo){

		PageInfo<SysRoleEntity> roleList = sysRoleService.queryList(sysRoleSearchVo.getPageNum(),sysRoleSearchVo.getPageSize());

		return ResultResp.successData(roleList,"");
	}
	
	/**
	 * 角色信息
	 */
	@ApiOperation(value="角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "String"),
	})
	@PostMapping("/info")
	@RequiresPermissions("sys:role:info")
	public ResultResp info(@JsonParam("roleId") String roleId){
		SysRoleEntity role = sysRoleService.queryObject(Long.parseLong(roleId));
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(Long.parseLong(roleId));
		role.setMenuIdList(menuIdList);

		return ResultResp.successData(role,"");
	}
	
	/**
	 * 保存角色
	 */
	@ApiOperation(value="保存角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysRoleAddVo", value = "角色Vo", required = true, dataType = "SysRoleAddVo"),
	})
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public ResultResp save(@RequestBody SysRoleAddVo sysRoleAddVo){
		ResultResp resp = ValidatorUtils.validateEntity(sysRoleAddVo);
		if(resp.isSuccess()){
			SysRoleEntity sre = new SysRoleEntity();
			BeanUtils.copyProperties(sysRoleAddVo,sre);
			sysRoleService.save(sre);

			return ResultResp.success();
		}else{
			return resp;
		}
	}
	
	/**
	 * 修改角色
	 */
	@ApiOperation(value="修改角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysRoleUpdateVo", value = "角色Vo", required = true, dataType = "SysRoleUpdateVo"),
	})
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public ResultResp update(@RequestBody SysRoleUpdateVo sysRoleUpdateVo){
		ResultResp resp = ValidatorUtils.validateEntity(sysRoleUpdateVo);
		if(resp.isSuccess()){
			SysRoleEntity sre = new SysRoleEntity();
			BeanUtils.copyProperties(sysRoleUpdateVo,sre);
			sre.setRoleId(Long.parseLong(sysRoleUpdateVo.getRoleId()));
		    sysRoleService.update(sre);
			return ResultResp.success();
		}else{
			return resp;
		}
	}

	@ApiOperation(value="获取所有的权限列表")
	@PostMapping("/allMenuList")
	@RequiresPermissions("sys:role:list")
	public ResultResp allMenuList(){

		List<SysMenuEntity> list = sysMenuService.queryAllList();

		return ResultResp.successData(list,"");
	}

	@ApiOperation(value="获取当前用户的权限列表")
	@PostMapping("/userMenuList")
	@RequiresPermissions("sys:role:list")
	public ResultResp userMenuList(){

		List<SysMenuEntity> list = sysMenuService.queryUserList();

		return ResultResp.successData(list,"");
	}

	@ApiOperation(value="获取某个角色的权限列表")
	@PostMapping("/roleMenuList")
	@RequiresPermissions("sys:role:list")
	public ResultResp roleMenuList(@JsonParam("roleId") String roleId){

		List<SysMenuEntity> list = sysMenuService.queryRoleMenuList(Long.parseLong(roleId));

		return ResultResp.successData(list,"");
	}

	@ApiOperation(value="删除角色")
	@SysLog("删除角色")
	@PostMapping("/deleteRole")
	public ResultResp deleteRole(@JsonParam("roleId") String roleId){
		int count = sysRoleService.countRoleUser(roleId);
		if(count != 0){
			return ResultResp.fail("该角色已分配，不能删除!");
		}else{
			sysRoleService.deleteRole(roleId);
			return ResultResp.success("删除成功!");
		}

	}

}
