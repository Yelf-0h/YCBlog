package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-13-15:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminInsertLinkDto {
    /**
     * 借用AdminInsertLinkDto的dto类给update使用
     * 所以添加了id的属性
     */
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

    private String status;

}
