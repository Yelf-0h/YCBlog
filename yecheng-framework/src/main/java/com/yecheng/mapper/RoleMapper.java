package com.yecheng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yecheng.domain.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 选择关键角色用户id
     *
     * @param id id
     * @return {@link List}<{@link String}>
     */
    List<String> selectRoleKeyByUserId(@Param("id") Long id);
}
