package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.vo.MenuVo;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.MenuMapper;
import com.yecheng.domain.entity.Menu;
import com.yecheng.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

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
}
