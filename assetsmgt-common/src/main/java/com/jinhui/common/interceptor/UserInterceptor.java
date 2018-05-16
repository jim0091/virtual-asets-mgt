package com.jinhui.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.jinhui.common.annotation.TokenIgnore;
import com.jinhui.common.entity.SysUserEntity;
import com.jinhui.common.utils.RedisUtils;
import com.jinhui.common.utils.ResultResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @autor wsc
 * @create 2018-04-02 15:21
 **/
public class UserInterceptor implements HandlerInterceptor{
    private final static Logger logger = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) throws Exception {
        TokenIgnore annotation;
        if(obj instanceof HandlerMethod) {
            annotation = ((HandlerMethod) obj).getMethodAnnotation(TokenIgnore.class);
        }else{
            return true;
        }

        //如果有@AuthIgnore注解，则不验证token
        if(annotation != null){
            return true;
        }

        String token = httpServletRequest.getHeader("token");
        logger.info(" token: " + token);
        if(token != null){
            SysUserEntity user = RedisUtils.getRedisUser(httpServletRequest);
            if(user != null){
                RedisUtils.setLocalUser(token,user,RedisUtils.ONE_HOUR_EXPIRE);
                return true;
            }else{
                this.returnJson(httpServletResponse, JSON.toJSONString(ResultResp.overdue("token不存在或已过期!")));
                return false;
            }

        }else{
            this.returnJson(httpServletResponse, JSON.toJSONString(ResultResp.fail("请求无效，参数token没传!")));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {



    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        RedisUtils.removeLocalUser();
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
