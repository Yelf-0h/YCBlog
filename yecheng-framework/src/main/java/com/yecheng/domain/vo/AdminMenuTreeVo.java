package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-19-10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AdminMenuTreeVo {
    private Long id;
    //菜单名称
    private String label;
    private Long parentId;
    List<AdminMenuTreeVo> children;


}
