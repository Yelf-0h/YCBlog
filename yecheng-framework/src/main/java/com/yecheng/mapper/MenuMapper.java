package com.yecheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yecheng.domain.entity.Menu;
import com.yecheng.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-16 17:25:38
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id选择烫发
     *
     * @param id id
     * @return
     */
    List<String> selectPermsByUserId(@Param("id") Long id);

    /**
     * 选择所有路由器菜单
     *
     * @return {@link List}<{@link MenuVo}>
     */
    List<Menu> selectAllRouterMenu();

    /**
     * 通过用户id选择路由器菜单树
     *
     * @param userId 用户id
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}
