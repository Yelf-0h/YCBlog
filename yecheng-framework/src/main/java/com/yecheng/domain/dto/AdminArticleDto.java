package com.yecheng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yelf
 * @create 2022-10-18-16:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminArticleDto {
    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;
}
