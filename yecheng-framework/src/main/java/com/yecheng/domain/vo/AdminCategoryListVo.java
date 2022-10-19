package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-13-1:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryListVo {

    private Long id;
    private String description;
    private String name;
    private String status;
}
