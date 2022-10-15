package com.yecheng.service.impl;

import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.entity.LoginUser;
import com.yecheng.domain.entity.User;
import com.yecheng.service.BlogLoginService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.JwtUtil;
import com.yecheng.utils.RedisCache;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.BlogUserLoginVo;
import com.yecheng.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Yelf
 * @create 2022-10-13-16:38
 */
@Service("blogLoginService")
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("账号或密码错误！！");
        }
//        获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
//        把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.BLOG_LOGIN +userId,loginUser,JwtUtil.JWT_TTL.intValue(), TimeUnit.MILLISECONDS);
//        把user转换成userinfovo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        把token和userinfo封装 返回
        return ResponseResult.okResult(new BlogUserLoginVo(jwt,userInfoVo));
    }

    @Override
    public ResponseResult logout() {
//        获取token 解析token中的userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        获取userid
        Long userId = loginUser.getUser().getId();
//        删除redis中的用户信息
        redisCache.deleteObject(SystemConstants.BLOG_LOGIN+userId);
        return ResponseResult.okResult();
    }
}
