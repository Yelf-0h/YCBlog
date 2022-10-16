package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
public interface RoleService extends IService<Role> {

    /**
     * 选择关键角色用户id
     *
     * @param id id
     * @return {@link List}<{@link String}>
     */
    List<String> selectRoleKeyByUserId(Long id);
}
