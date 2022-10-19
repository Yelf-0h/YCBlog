package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminInsertUserDto;
import com.yecheng.domain.dto.AdminUpdateUserDto;
import com.yecheng.domain.dto.AdminUserListDto;
import com.yecheng.domain.dto.AdminUserStatusDto;
import com.yecheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-10-19-15:07
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult adminUserList(Integer pageNum, Integer pageSize, AdminUserListDto userListDto){
        return userService.adminUserList(pageNum,pageSize,userListDto);
    }

    @PostMapping
    public ResponseResult insertUser(@RequestBody AdminInsertUserDto user){
        return userService.insertUser(user);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteUser(@PathVariable("id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("{id}")
    public ResponseResult adminGetUserDetailById(@PathVariable("id") Long id){
        return userService.adminGetUserDetailById(id);
    }
    @PutMapping
    public ResponseResult adminUpdateUser(@RequestBody AdminUpdateUserDto updateUserDto){
        return userService.adminUpdateUser(updateUserDto);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody AdminUserStatusDto statusDto){
        return userService.changeStatus(statusDto);
    }
}
