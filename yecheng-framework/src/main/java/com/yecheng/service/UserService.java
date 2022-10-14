package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.entity.User;
import com.yecheng.utils.ResponseResult;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-13 15:59:44
 */
public interface UserService extends IService<User> {

    /**
     * 用户信息
     *
     * @return {@link ResponseResult}
     */
    ResponseResult userInfo();

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 注册
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    ResponseResult register(User user);
}
