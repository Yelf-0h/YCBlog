package com.yecheng.domain.dto;

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
public class AdminCategoryStatusDto {

    private Long id;
    private String status;

}
