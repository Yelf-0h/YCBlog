package com.yecheng.handler.security;

import com.alibaba.fastjson.JSON;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.domain.ResponseResult;
import com.yecheng.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yelf
 * @create 2022-10-13-19:45
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        打印下异常信息
        e.printStackTrace();
//        统一响应结果
        ResponseResult responseResult = null;
        if (e instanceof BadCredentialsException) {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),e.getMessage());
        }else if (e instanceof InsufficientAuthenticationException){
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }else {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
//        响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
    }
}
