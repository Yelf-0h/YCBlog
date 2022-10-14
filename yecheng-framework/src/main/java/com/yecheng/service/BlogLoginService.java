package com.yecheng.service;

import com.yecheng.entity.User;
import com.yecheng.utils.ResponseResult;

/**
 * 博客登录服务
 *
 * @author Yefl
 * @date 2022/10/13
 */
public interface BlogLoginService {
    /**
     * 登录
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    ResponseResult login(User user);

    /**
     * 注销
     *
     * @return {@link ResponseResult}
     */
    ResponseResult logout();
}