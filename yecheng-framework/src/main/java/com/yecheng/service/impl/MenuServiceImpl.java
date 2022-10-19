package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminMenuListDto;
import com.yecheng.domain.entity.RoleMenu;
import com.yecheng.domain.vo.*;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.RoleMenuService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.SecurityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.MenuMapper;
import com.yecheng.domain.entity.Menu;
import com.yecheng.service.MenuService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectPermsByUserId(Long id) {
//        根据用户id查询
//        如果是管理员返回所有的权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = new ArrayList<>(menus.stream()
                    .map(Menu::getPerms).collect(Collectors.toSet()));
            return perms;
        }else {
            //        否则返回所具有的权限
            return getBaseMapper().selectPermsByUserId(id);
        }

    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper mapper = getBaseMapper();
        List<Menu> menus = null;
//        判断是否为管理员，如果是返回所有菜单
        if (SecurityUtils.isAdmin()){
            menus =  mapper.selectAllRouterMenu();
        }else {
//        否则返回所具有的菜单
            menus = mapper.selectRouterMenuTreeByUserId(userId);
        }

        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<MenuVo> menuVosTree = builderMenuTree(menuVos,0L);

        return menuVosTree;
    }

    @Override
    public ResponseResult menuList(AdminMenuListDto adminMenuListDto) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(adminMenuListDto.getMenuName())){
            queryWrapper.like(Menu::getMenuName,adminMenuListDto.getMenuName());
        }
        if (StringUtils.hasText(adminMenuListDto.getStatus())){
            queryWrapper.like(Menu::getStatus,adminMenuListDto.getStatus());
        }
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = list(queryWrapper);
        if (Objects.isNull(list)){
            throw new SystemException(AppHttpCodeEnum.IS_NULL);
        }
        List<AdminMenuVo> adminMenuVos = BeanCopyUtils.copyBeanList(list, AdminMenuVo.class);
        return ResponseResult.okResult(adminMenuVos);
    }

    @Override
    public ResponseResult insertMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuDetailById(Long id) {
        Menu menu = getById(id);
        if (Objects.isNull(menu)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        AdminMenuDetailVo adminMenuDetailVo = BeanCopyUtils.copyBean(menu, AdminMenuDetailVo.class);
        return ResponseResult.okResult(adminMenuDetailVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if (Objects.isNull(menu.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单'"+menu.getMenuName()+"'失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenuById(Long menuId) {
        if (Objects.isNull(menuId)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        int count = count(queryWrapper);
        if (count > 0){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        removeById(menuId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelectMenu() {
        List<AdminMenuTreeVo> finalTreeVos = getAdminMenuTreeVos();

        return ResponseResult.okResult(finalTreeVos);
    }

    @Override
    public ResponseResult roleMenuTreeSelectById(Long id) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> roleMenus = roleMenuService.list(queryWrapper);
        if (Objects.isNull(roleMenus)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
//        取得此角色相关的所有menuId
        List<Long> menuIds = roleMenus.stream().map(
                roleMenu -> roleMenu.getMenuId()
        ).collect(Collectors.toList());
//        调用此类中getAdminMenuTreeVos方法取得所有菜单信息
        List<AdminMenuTreeVo> finalTreeVos = getAdminMenuTreeVos();
//        将menuIds与finalTreeVos最后的树状菜单包装到AdminRoleMenuTreeVo返回
        AdminRoleMenuTreeVo adminRoleMenuTreeVo = new AdminRoleMenuTreeVo(finalTreeVos, menuIds);
        return ResponseResult.okResult(adminRoleMenuTreeVo);
    }


    private List<MenuVo> builderMenuTree(List<MenuVo> menuVos, Long parentId) {
        List<MenuVo> collect = menuVos.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menuVos, menu.getId())))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取子menu集合
     *
     * @param menuVos 菜单vos
     * @param id      id
     * @return {@link List}<{@link MenuVo}>
     */
    private List<MenuVo> getChildren(List<MenuVo> menuVos, Long id) {
        List<MenuVo> collect = menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(id))
                .collect(Collectors.toList());
        return collect;
    }

    /**
     * 方法提取出来共用，此方法能
     * 得到所有菜单树
     *
     * @return {@link List}<{@link AdminMenuTreeVo}>
     */
    @NotNull
    private List<AdminMenuTreeVo> getAdminMenuTreeVos() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        if (Objects.isNull(menus)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<AdminMenuTreeVo> menuTreeVos = menus.stream().map(menu -> new AdminMenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null)).collect(Collectors.toList());
        List<AdminMenuTreeVo> finalTreeVos = menuTreeVos.stream().filter(menuTreeVo -> menuTreeVo.getParentId().equals(0L))
                .map(menuTreeVo -> menuTreeVo.setChildren(menuTreeVos.stream()
                        .filter(menu -> menu.getParentId().equals(menuTreeVo.getId())).collect(Collectors.toList())))
                .collect(Collectors.toList());

        for (AdminMenuTreeVo a:finalTreeVos){
            List<AdminMenuTreeVo> children = a.getChildren();
            for (AdminMenuTreeVo b:children){
                b.setChildren(
                        menuTreeVos.stream()
                                .filter(menu -> menu.getParentId().equals(b.getId())).collect(Collectors.toList())
                );
            }
        }
        return finalTreeVos;
    }
}
