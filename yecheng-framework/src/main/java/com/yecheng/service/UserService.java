package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.dto.AdminInsertUserDto;
import com.yecheng.domain.dto.AdminUpdateUserDto;
import com.yecheng.domain.dto.AdminUserListDto;
import com.yecheng.domain.dto.AdminUserStatusDto;
import com.yecheng.domain.entity.User;
import com.yecheng.domain.ResponseResult;

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

    /**
     * 管理用户列表
     *
     * @param pageNum
     * @param pageSize
     * @param userListDto 用户列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult adminUserList(Integer pageNum, Integer pageSize, AdminUserListDto userListDto);

    /**
     * 插入用户
     *
     * @param user 用户
     * @return {@link ResponseResult}
     */
    ResponseResult insertUser(AdminInsertUserDto user);

    /**
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteUser(Long id);

    /**
     * 管理员通过id获取用户详细信息
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult adminGetUserDetailById(Long id);

    /**
     * 管理更新用户
     *
     * @param updateUserDto 更新用户dto
     * @return {@link ResponseResult}
     */
    ResponseResult adminUpdateUser(AdminUpdateUserDto updateUserDto);

    /**
     * 改变状态
     *
     * @param statusDto 地位dto
     * @return {@link ResponseResult}
     */
    ResponseResult changeStatus(AdminUserStatusDto statusDto);
}
