package com.yecheng.controller;

import com.yecheng.annotation.SystemLog;
import com.yecheng.domain.dto.UserDto;
import com.yecheng.domain.entity.User;
import com.yecheng.service.UserService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author Yefl
 * @date 2022/10/15
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "操作用户的相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "用户信息",notes = "获取用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    @SystemLog(BusinessName = "更新用户信息")
    @ApiOperation(value = "更新用户信息",notes = "更新用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto",value = "用户Dto实体类")
    })
    public ResponseResult updateUserInfo(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.updateUserInfo(user);
    }
    @ApiOperation(value = "用户注册",notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto",value = "用户Dto实体类")
    })
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.register(user);
    }
}
