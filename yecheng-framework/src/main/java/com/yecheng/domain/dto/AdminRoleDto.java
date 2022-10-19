package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-19-0:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRoleDto {
    private String roleName;
    private String status;
//    为了不多写一个与查询list共用，加一个changeStatus接口需要的roleId
    private Long roleId;
}
