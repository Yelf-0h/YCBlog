package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yecheng.domain.entity.LoginUser;
import com.yecheng.domain.entity.User;
import com.yecheng.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Yelf
 * @create 2022-10-13-16:57
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
//        判断是否查到用户 如果没查到抛出异常
        if (Objects.isNull(user)){
            throw new RuntimeException("账号或密码错误！！");
        }
//        查到之后返回用户信息
//        TODO 查询权限信息的封装
        return new LoginUser(user);
    }
}
