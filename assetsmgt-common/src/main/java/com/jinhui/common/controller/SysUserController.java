package com.jinhui.common.controller;


import com.github.pagehelper.PageInfo;
import com.jinhui.common.annotation.JsonParam;
import com.jinhui.common.annotation.SysLog;
import com.jinhui.common.annotation.TokenIgnore;
import com.jinhui.common.controller.vo.*;
import com.jinhui.common.entity.SysMenuEntity;
import com.jinhui.common.entity.SysUserEntity;
import com.jinhui.common.service.SysMenuService;
import com.jinhui.common.service.SysUserRoleService;
import com.jinhui.common.service.SysUserService;
import com.jinhui.common.utils.*;
import com.jinhui.common.validator.ValidatorUtils;
import com.jinhui.common.validator.group.AddGroup;
import com.jinhui.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 登录
	 */
	@TokenIgnore
	@ApiOperation(value="登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "loginVo", value = "登录vo", required = true, dataType = "LoginVo"),
	})
	@PostMapping(value = "/login")
	@ResponseBody
	public ResultResp login(@RequestBody LoginVo loginVo) throws Exception {
		//用户信息
		SysUserEntity user = sysUserService.queryByMobile(loginVo.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(loginVo.getPassword(), user.getSalt()).toHex())) {
			return ResultResp.fail("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return ResultResp.fail("账号已被锁定,请联系管理员");
		}

		//生成token
		String token = JwtToken.createToken(String.valueOf(user.getUserId()));
		//将用户数据保存在redis，同时保存在threadlocal, 一小时有效期
		RedisUtils.setLocalUser(token,user,RedisUtils.ONE_HOUR_EXPIRE);

		//获取当前用户的权限列表
		List<SysMenuEntity> list = sysMenuService.queryUserList();
		return ResultResp.successData(list,token);
	}

	@ApiOperation(value="退出")
	@PostMapping(value = "/exit")
	@ResponseBody
	public ResultResp exit(HttpServletRequest req){
		String token = req.getHeader("token");
		logger.info(" token: " + token);
		RedisUtils.delete(token);
		return ResultResp.success();
	}

	/**
	 * 所有用户列表
	 */
	@ApiOperation(value="用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysUserSearchVo", value = "用户列表条件vo", required = true, dataType = "SysUserSearchVo"),
	})
	@PostMapping("/list")
	@ResponseBody
	public ResultResp list(@RequestBody SysUserSearchVo sysUserSearchVo){
		//查询列表数据
		PageInfo<SysUserEntity> userList = sysUserService.queryList(sysUserSearchVo.getUserId(),sysUserSearchVo.getUsername(),sysUserSearchVo.getPageNum(),sysUserSearchVo.getPageSize());

		return ResultResp.successData(userList,"");
	}
	
	/**
	 * 用户信息
	 */
	@ApiOperation(value="用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String"),
	})
	@PostMapping("/info")
	public ResultResp info(@JsonParam(value="userId") String userId){
		SysUserEntity user = sysUserService.queryObject(Long.valueOf(userId));
		
		return ResultResp.successData(user,"");
	}
	
	/**
	 * 保存用户
	 */
	@ApiOperation(value="保存用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysUserAddVo", value = "用户", required = true, dataType = "SysUserAddVo"),
	})
	@SysLog("保存用户")
	@PostMapping("/save")
	public ResultResp save(@RequestBody SysUserAddVo sysUserAddVo, HttpServletRequest req){
		ResultResp resp = ValidatorUtils.validateEntity(sysUserAddVo, AddGroup.class);
		if(resp.isSuccess()){
			SysUserEntity sysUserEntity = new SysUserEntity();
			BeanUtils.copyProperties(sysUserAddVo,sysUserEntity);
			sysUserEntity.setCreateUserId(RedisUtils.getRedisUser(req).getUserId());
			sysUserEntity.setRoleId(sysUserAddVo.getRoleId());
			sysUserService.save(sysUserEntity);

			return ResultResp.success();
		}else{
			return resp;
		}

	}
	
	/**
	 * 修改用户
	 */
	@ApiOperation(value="修改用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysUserUpdateVo", value = "用户", required = true, dataType = "SysUserUpdateVo"),
	})
	@SysLog("修改用户")
	@PostMapping("/update")
	@ResponseBody
	public ResultResp update(@RequestBody SysUserUpdateVo sysUserUpdateVo,HttpServletRequest req){
		ResultResp resp = ValidatorUtils.validateEntity(sysUserUpdateVo, AddGroup.class);
		if(resp.isSuccess()) {
			SysUserEntity sysUserEntity = new SysUserEntity();
			BeanUtils.copyProperties(sysUserUpdateVo, sysUserEntity);
			sysUserEntity.setCreateUserId(RedisUtils.getRedisUser(req).getUserId());

			sysUserEntity.setUserId(Long.valueOf(sysUserUpdateVo.getUserId()));
			sysUserService.update(sysUserEntity);

			return ResultResp.success();
		}else{
			return resp;
		}
	}
	
	/**
	 * 删除用户
	 */
	@ApiOperation(value="删除用户")
	@SysLog("删除用户")
	@PostMapping("/delete")
	@ResponseBody
	public ResultResp delete(@JsonParam(value = "userId") String userId){

		sysUserService.delete(Long.valueOf(userId));
		
		return ResultResp.success();
	}

	@ApiOperation(value="修改密码")
	@SysLog("修改密码")
	@PostMapping("/updatePassword")
	@ResponseBody
	public ResultResp updatePassword(@RequestBody UpdatePassVo updatePassVo, HttpServletRequest req){

		Map map = new HashMap();
		map.put("userId", RedisUtils.getRedisUser(req).getUserId());
		map.put("password",new Sha256Hash(updatePassVo.getPassword(), RedisUtils.getRedisUser(req).getSalt()).toHex());
		map.put("newPassword",new Sha256Hash(updatePassVo.getNewPassword(), RedisUtils.getRedisUser(req).getSalt()).toHex());

		if(!updatePassVo.getNewPassword().equals(updatePassVo.getConfirmPassword())){
			return ResultResp.fail("两次输入的新密码不一致!");
		}
		int count = sysUserService.updatePassword(map);
		if(count == 0){
			return ResultResp.fail("旧密码错误!");
		}

		return ResultResp.success();
	}
}
