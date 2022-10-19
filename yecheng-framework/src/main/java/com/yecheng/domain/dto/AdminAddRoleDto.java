package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2022-10-16 17:25:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAddRoleDto {

//    与更新接口共用所以多加一个id属性
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //备注
    private String remark;
    List<Long> menuIds;



}
