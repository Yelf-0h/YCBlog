package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminAddRoleDto;
import com.yecheng.domain.dto.AdminRoleDto;
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

    /**
     * 角色列表
     *
     * @param pageNum          页面num
     * @param pageSize         页面大小
     * @param adminRoleDto 管理角色列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult roleList(Integer pageNum, Integer pageSize, AdminRoleDto adminRoleDto);

    /**
     * 改变状态
     *
     * @param adminRoleDto 管理角色dto
     * @return {@link ResponseResult}
     */
    ResponseResult changeStatus(AdminRoleDto adminRoleDto);

    /**
     * 插入作用
     *
     * @param adminAddRoleDto 管理员添加角色
     * @return {@link ResponseResult}
     */
    ResponseResult insertRole(AdminAddRoleDto adminAddRoleDto);

    /**
     * 得到角色细节
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult getRoleDetail(Long id);

    /**
     * 更新作用
     *
     * @param addRoleDto 添加角色dto
     * @return {@link ResponseResult}
     */
    ResponseResult updateRole(AdminAddRoleDto addRoleDto);

    /**
     * 通过id删除角色
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteRoleById(Long id);

    /**
     * 列出所有角色
     *
     * @return {@link ResponseResult}
     */
    ResponseResult listAllRole();
}
