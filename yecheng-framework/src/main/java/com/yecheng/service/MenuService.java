package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
