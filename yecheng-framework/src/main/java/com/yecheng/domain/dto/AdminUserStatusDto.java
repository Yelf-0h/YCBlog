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
public class AdminUserStatusDto {
    private String status;
    private Long userId;
}
