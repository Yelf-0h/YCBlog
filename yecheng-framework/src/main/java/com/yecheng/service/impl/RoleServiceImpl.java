package com.yecheng.service.impl;

import com.yecheng.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.RoleMapper;
import com.yecheng.domain.entity.Role;
import com.yecheng.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        List<String> roles = new ArrayList<>();
//        判断是否为管理员 如果是返回合集中只需要有admin
        if (SecurityUtils.isAdmin()){
            roles.add("admin");
            return roles;
        }else {
//        否则返回具有的角色
            return getBaseMapper().selectRoleKeyByUserId(id);
        }

    }
}
