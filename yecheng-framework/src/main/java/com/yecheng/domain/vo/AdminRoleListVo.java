package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-19-0:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleListVo {
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
//   为了与修改角色时的回显接口返回共用 添加一个remark字段
    private String remark;
}
