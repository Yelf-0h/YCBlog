package com.yecheng.domain.vo;

import com.yecheng.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-19-16:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminGetUserDetailVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private AdminUserListVo user;
}
