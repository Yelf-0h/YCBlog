package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-17-21:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    private Long id;
    private String name;
    private String remark;
}
