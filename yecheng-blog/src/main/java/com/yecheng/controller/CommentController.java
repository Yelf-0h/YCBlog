package com.yecheng.controller;

import com.yecheng.constants.SystemConstants;
import com.yecheng.entity.Comment;
import com.yecheng.service.CommentService;
import com.yecheng.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 *
 * @author Yefl
 * @date 2022/10/13
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论列表
     *
     * @param articleId 文章id
     * @param pageNum   页面num
     * @param pageSize  页面大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    /**
     * 添加评论
     *
     * @param comment 评论
     * @return {@link ResponseResult}
     */
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        //逻辑代码
        return commentService.addComment(comment);
    }

    /**
     * 链接评论列表
     *
     * @param pageNum   页面num
     * @param pageSize  页面大小
     * @return {@link ResponseResult}
     */
    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
