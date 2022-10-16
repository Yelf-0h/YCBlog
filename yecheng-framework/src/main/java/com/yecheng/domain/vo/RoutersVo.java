package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-16-22:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
