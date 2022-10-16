package com.yecheng.service.impl;

import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.entity.LoginUser;
import com.yecheng.domain.entity.User;
import com.yecheng.service.AdminLoginService;
import com.yecheng.utils.JwtUtil;
import com.yecheng.utils.RedisCache;
import com.yecheng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Yelf
 * @create 2022-10-16-15:18
 */
@Service("adminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("账号或密码错误！！");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId.toString());
        redisCache.setCacheObject(SystemConstants.ADMIN_LOGIN+userId,loginUser,JwtUtil.JWT_TTL.intValue(), TimeUnit.MILLISECONDS);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(SystemConstants.HEADER_TOKEN,jwt);
        return ResponseResult.okResult(hashMap);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(SystemConstants.ADMIN_LOGIN+userId);
        return ResponseResult.okResult();
    }
}
