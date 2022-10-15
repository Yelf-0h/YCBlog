package com.yecheng.controller;

import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.dto.AddCommentDto;
import com.yecheng.domain.entity.Comment;
import com.yecheng.service.CommentService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "评论",description = "评论相关接口")
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
    @ApiOperation(value = "文章评论列表",notes = "获取一页文章评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章的Id"),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    /**
     * 添加评论
     *
     * @param addCommentDto 评论
     * @return {@link ResponseResult}
     */
    @PostMapping
    @ApiOperation(value = "添加评论",notes = "添加一条评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addCommentDto",value = "添加评论的dto实体类"),
    })
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
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
    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页的大小")
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
