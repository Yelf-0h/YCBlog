package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminAddRoleDto;
import com.yecheng.domain.dto.AdminRoleDto;
import com.yecheng.domain.entity.RoleMenu;
import com.yecheng.domain.vo.AdminListAllRoleVo;
import com.yecheng.domain.vo.AdminRoleListVo;
import com.yecheng.domain.vo.PageVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.mapper.RoleMenuMapper;
import com.yecheng.service.RoleMenuService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.RoleMapper;
import com.yecheng.domain.entity.Role;
import com.yecheng.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

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

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, AdminRoleDto adminRoleDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(adminRoleDto.getRoleName()),Role::getRoleName, adminRoleDto.getRoleName());
        queryWrapper.eq(StringUtils.hasText(adminRoleDto.getStatus()),Role::getStatus, adminRoleDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> rolePage = new Page<>(pageNum,pageSize);
        page(rolePage,queryWrapper);
        if (Objects.isNull(rolePage.getRecords())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<AdminRoleListVo> adminRoleListVos = BeanCopyUtils.copyBeanList(rolePage.getRecords(), AdminRoleListVo.class);
        return ResponseResult.okResult(new PageVo(adminRoleListVos,rolePage.getTotal()));
    }

    @Override
    public ResponseResult changeStatus(AdminRoleDto adminRoleDto) {
        if (Objects.isNull(adminRoleDto.getRoleId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Role role = new Role();
        role.setId(adminRoleDto.getRoleId());
        role.setStatus(adminRoleDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult insertRole(AdminAddRoleDto adminAddRoleDto) {
        if (Objects.isNull(adminAddRoleDto)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Role role = BeanCopyUtils.copyBean(adminAddRoleDto, Role.class);
        save(role);
        List<RoleMenu> roleMenus = adminAddRoleDto.getMenuIds().stream()
                .map(menuId -> new RoleMenu(role.getId(), menuId)).collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleDetail(Long id) {
        Role role = getById(id);
        if (Objects.isNull(role)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        AdminRoleListVo adminRoleListVo = BeanCopyUtils.copyBean(role, AdminRoleListVo.class);
        return ResponseResult.okResult(adminRoleListVo);
    }

    @Override
    @Transactional
    public ResponseResult updateRole(AdminAddRoleDto addRoleDto) {
        if (Objects.isNull(addRoleDto.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Role role = BeanCopyUtils.copyBean(addRoleDto, Role.class);
        updateById(role);
        List<Long> menuIds = addRoleDto.getMenuIds();
        List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> new RoleMenu(role.getId(), menuId)
        ).collect(Collectors.toList());
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuMapper.delete(queryWrapper);
        roleMenuService.saveBatch(roleMenus);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult deleteRoleById(Long id) {
        boolean b = removeById(id);
        if (!b){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.USER_STATUS_NORMAL);
        queryWrapper.orderByAsc(Role::getRoleSort);
        List<Role> roles = list(queryWrapper);
        if (Objects.isNull(roles)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<AdminListAllRoleVo> adminListAllRoleVos = BeanCopyUtils.copyBeanList(roles, AdminListAllRoleVo.class);
        return ResponseResult.okResult(adminListAllRoleVos);
    }
}
