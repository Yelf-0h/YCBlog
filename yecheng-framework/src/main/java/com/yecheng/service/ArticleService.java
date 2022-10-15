package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.entity.Article;
import com.yecheng.domain.ResponseResult;

/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2022-10-12 11:31:52
 */
public interface ArticleService extends IService<Article> {

    /**
     * 热门文章列表
     *
     * @return {@link ResponseResult}
     */
    ResponseResult hotArticleList();

    /**
     * 文章列表
     *
     * @param pageNum    页面num
     * @param pageSize   页面大小
     * @param categoryId 类别id
     * @return {@link ResponseResult}
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 获取文章细节
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult getArticleDetail(Long id);

    /**
     * 更新文章游览量
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult updateViewCount(Long id);
}

