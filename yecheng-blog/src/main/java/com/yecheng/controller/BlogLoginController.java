package com.yecheng.controller;

import com.yecheng.domain.dto.UserDto;
import com.yecheng.domain.entity.User;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.BlogLoginService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客登录控制器
 *
 * @author Yefl
 * @date 2022/10/15
 */
@RestController
@Api(tags = "登录状态控制",description = "登录相关接口")
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userDto",value = "用户Dto实体类")
    })
    public ResponseResult login(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }
    @ApiOperation(value = "退出登录",notes = "退出登录")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}