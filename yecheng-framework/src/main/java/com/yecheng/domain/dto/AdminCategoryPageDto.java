package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-19-17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategoryPageDto {
    /**
     * 借用AdminCategoryPageDto来update
     * 添加id字段
     * */
    private Long id;
    private String name;
    /**
     * 借用AdminCategoryPageDto来insert
     * 添加description字段
     * */
    private String description;
    private String status;
}
