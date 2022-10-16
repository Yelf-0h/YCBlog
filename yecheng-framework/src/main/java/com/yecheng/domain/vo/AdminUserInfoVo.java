package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-16-17:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserInfoVo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;
}
