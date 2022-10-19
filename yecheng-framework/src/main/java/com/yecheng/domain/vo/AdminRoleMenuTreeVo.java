package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-19-13:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleMenuTreeVo {
    private List<AdminMenuTreeVo> menus;
    private List<Long> checkedKeys;
}
