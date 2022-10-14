package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.entity.Comment;
import com.yecheng.utils.ResponseResult;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-13 20:54:27
 */
public interface CommentService extends IService<Comment> {

    /**
     * 评论列表
     *
     * @param commentType
     * @param articleId 文章id
     * @param pageNum   页面num
     * @param pageSize  页面大小
     * @return {@link ResponseResult}
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     *
     * @param comment 评论
     * @return {@link ResponseResult}
     */
    ResponseResult addComment(Comment comment);
}
