package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 热门文章签证官
 * 最后返回时的封装的类
 *
 * @author Yefl
 * @date 2022/10/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;

    //标题
    private String title;


    //访问量
    private Long viewCount;
}