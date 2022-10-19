package com.yecheng.service.impl;

import com.yecheng.domain.entity.LoginUser;
import com.yecheng.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-18-15:55
 */
@Service("ps")
public class PermissionService {
    public boolean hasPermission(String... perms) {
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        for (String permission : permissions) {
            for (int i = 0; i < perms.length; i++) {
                if (permission.equals(perms[i])) {
                    return true;
                }
            }
        }
//        permissions.contains(perms);
        return false;
    }

    public boolean hasPermission(String permission){
        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }

}
