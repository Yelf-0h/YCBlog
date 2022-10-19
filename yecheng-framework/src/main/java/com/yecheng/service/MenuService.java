package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminMenuListDto;
import com.yecheng.domain.entity.Menu;
import com.yecheng.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过用户id选择烫发
     *
     * @param id id
     * @return {@link List}<{@link String}>
     */
    List<String> selectPermsByUserId(Long id);

    /**
     * 通过用户id选择路由器菜单树
     *
     * @param userId 用户id
     * @return {@link List}<{@link MenuVo}>
     */
    List<MenuVo> selectRouterMenuTreeByUserId(Long userId);

    /**
     * 后台菜单列表
     *
     * @param adminMenuListDto 管理菜单列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult menuList(AdminMenuListDto adminMenuListDto);

    /**
     * 插入菜单
     *
     * @param menu 菜单
     * @return {@link ResponseResult}
     */
    ResponseResult insertMenu(Menu menu);

    /**
     * 通过id获取菜单细节
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult getMenuDetailById(Long id);

    /**
     * 更新菜单
     *
     * @param menu 菜单
     * @return {@link ResponseResult}
     */
    ResponseResult updateMenu(Menu menu);

    /**
     * 删除菜单通过id
     *
     * @param menuId 菜单id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteMenuById(Long menuId);

    /**
     * 树选择菜单
     *
     * @return {@link ResponseResult}
     */
    ResponseResult treeSelectMenu();

    /**
     * 角色菜单树选择通过id
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult roleMenuTreeSelectById(Long id);
}
