package com.jinhui.common.controller;


import com.github.pagehelper.PageInfo;
import com.jinhui.common.annotation.JsonParam;
import com.jinhui.common.controller.vo.AgentMgtSearchVo;
import com.jinhui.common.controller.vo.ChangeAgentVo;
import com.jinhui.common.controller.vo.UserInvestMgtSearchVo;
import com.jinhui.common.controller.vo.UserSearchVo;
import com.jinhui.common.entity.Account;
import com.jinhui.common.entity.SysUserEntity;
import com.jinhui.common.entity.User;
import com.jinhui.common.service.SysUserService;
import com.jinhui.common.service.UserService;
import com.jinhui.common.utils.RedisUtils;
import com.jinhui.common.utils.ResultResp;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @autor wsc
 * @create 2018-03-22 15:47
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value="投资用户信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userSearchVo", value = "投资用户信息列表vo", required = true, dataType = "UserSearchVo"),
    })
    @PostMapping(value="/list")
    @ResponseBody
    public ResultResp list(@RequestBody UserSearchVo userSearchVo,HttpServletRequest req) {
        String sysUserId = RedisUtils.getRedisUser(req).getUserId() + "";
        String sysRoleId = RedisUtils.getRedisUser(req).getRoleId() + "";
        if(!"2".equals(sysRoleId)){   //超管 财务 运营
            if(!"".equals(userSearchVo.getSysUserId()) && userSearchVo.getSysUserId() != null ){
                sysUserId = userSearchVo.getSysUserId();
            }else{
                sysUserId = null;
            }
        }else{
            if(!"".equals(userSearchVo.getSysUserId()) && userSearchVo.getSysUserId() != null ){
                sysUserId = userSearchVo.getSysUserId();
            }
        }

        PageInfo<User> list = userService.queryListBySelective(userSearchVo.getUserName(),
                                                userSearchVo.getUserId(),sysUserId,
                                                userSearchVo.getPageNum(),userSearchVo.getPageSize());

        return ResultResp.successData(list,"");
    }

    @ApiOperation(value="经纪人列表")
    @PostMapping(value="/agentList")
    @ResponseBody
    public ResultResp agentList(HttpServletRequest req){
        String sysUserId = RedisUtils.getRedisUser(req).getUserId() + "";
        String sysRoleId = RedisUtils.getRedisUser(req).getRoleId() + "";

        List<SysUserEntity> list;
        //经纪人只返回自己
        if(sysRoleId.equals(String.valueOf(2))){
              list = sysUserService.queryAgentList(sysUserId);
        }else{
            list = sysUserService.queryAgentList("");
        }

        return ResultResp.successData(list,"");
    }

    @ApiOperation(value="绑定，更换经纪人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changeAgentVo", value = "绑定，更换经纪人vo", required = true, dataType = "ChangeAgentVo"),
    })
    @PostMapping(value="/changeAgent")
    @ResponseBody
    public ResultResp changeAgent(@RequestBody ChangeAgentVo changeAgentVo){

        userService.changeAgent(changeAgentVo.getUserId(),changeAgentVo.getSysUserId());

        return ResultResp.successData(null,"");
    }

   /* @ApiOperation(value="解绑经纪人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "userId"),
    })
    @PostMapping(value="/unBindAgent")
    @ResponseBody
    public ResultResp unBindAgent(@JsonParam("userId") String userId){


        userService.unBindAgent(userId);

        return ResultResp.successData(null,"");
    }*/

    @ApiOperation(value="用户资金账户列表")
    @PostMapping(value="/accountList")
    @ResponseBody
    public ResultResp accountList(@RequestBody UserSearchVo userSearchVo,HttpServletRequest req){
        String sysUserId = RedisUtils.getRedisUser(req).getUserId() + "";
        String sysRoleId = RedisUtils.getRedisUser(req).getRoleId() + "";
        if(!"2".equals(sysRoleId)){   //超管 财务 运营
            sysUserId = null;
        }

        PageInfo<Account> list = userService.queryUserListBySelective(userSearchVo.getUserName(),
                userSearchVo.getUserId(), sysUserId,userSearchVo.getPageNum(),userSearchVo.getPageSize());

        return ResultResp.successData(list,"");
    }

    @ApiOperation(value="经济人管理列表")
    @PostMapping(value="/agentMgtList")
    @ResponseBody
    public ResultResp agentMgtList(@RequestBody AgentMgtSearchVo agentMgtSearchVo){

        PageInfo<SysUserEntity> list = sysUserService.queryAgentListBySelective(agentMgtSearchVo.getUserName(),
                agentMgtSearchVo.getPageNum(),agentMgtSearchVo.getPageSize());

        return ResultResp.successData(list,"");
    }

    @ApiOperation(value="用户投资管理列表")
    @PostMapping(value="/userInvestMgtList")
    @ResponseBody
    public ResultResp userInvestMgtList(@RequestBody UserInvestMgtSearchVo userInvestMgtSearchVo,HttpServletRequest req){
        PageInfo<Account> list = userService.queryListBySysUserId(userInvestMgtSearchVo.getUserName(),userInvestMgtSearchVo.getUserId(),
                userInvestMgtSearchVo.getSysUserName(),userInvestMgtSearchVo.getSysUserId(),
                userInvestMgtSearchVo.getPageNum(),userInvestMgtSearchVo.getPageSize());

        return ResultResp.successData(list,"");
    }

}
