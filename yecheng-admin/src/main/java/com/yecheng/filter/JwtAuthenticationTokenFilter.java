package com.yecheng.filter;

import com.alibaba.fastjson.JSON;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.entity.LoginUser;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.utils.JwtUtil;
import com.yecheng.utils.RedisCache;
import com.yecheng.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Yelf
 * @create 2022-10-13-17:47
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        获取请求头中的token
        String token = request.getHeader(SystemConstants.HEADER_TOKEN);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request,response);
            return;
        }
//        解析获取到userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
//        从redis中获取key为userid的用户信息
        LoginUser loginUser = redisCache.getCacheObject(SystemConstants.ADMIN_LOGIN + userId);
        if (Objects.isNull(loginUser)){
            //说明登录过期  提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
//        如果存在就是登录了,存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
