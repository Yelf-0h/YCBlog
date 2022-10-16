package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.UserDto;
import com.yecheng.domain.entity.LoginUser;
import com.yecheng.domain.entity.User;
import com.yecheng.domain.vo.AdminUserInfoVo;
import com.yecheng.domain.vo.MenuVo;
import com.yecheng.domain.vo.RoutersVo;
import com.yecheng.domain.vo.UserInfoVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.AdminLoginService;
import com.yecheng.service.MenuService;
import com.yecheng.service.RoleService;
import com.yecheng.service.UserService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-16-15:14
 */
@RestController
public class LoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();

    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
//        获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
//        根据id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
//        根据用户id查询角色信息
        List<String> roles = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
//        封装数据返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        return ResponseResult.okResult(new AdminUserInfoVo(perms,roles,userInfoVo));
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menuVos = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menuVos));
    }


}
