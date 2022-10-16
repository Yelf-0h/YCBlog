package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.constants.SystemConstants;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.UserService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.CommentVo;
import com.yecheng.domain.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.CommentMapper;
import com.yecheng.domain.entity.Comment;
import com.yecheng.service.CommentService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-13 20:54:27
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 评论列表
     *
     * @param commentType
     * @param articleId 文章id
     * @param pageNum   页面num
     * @param pageSize  页面大小
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
//        查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
//        对评论的类型进行判断
        queryWrapper.eq(Comment::getType,commentType);
//        查找对应articleid文章的评论  判断commentType是否为0 是则执行 否则是友链评论就不执行
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
//        根评论的rootId为-1
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOTID);
//        分页查询
        Page<Comment> pageSet = new Page(pageNum,pageSize);
        page(pageSet,queryWrapper);

//        进行vo封装
        List<CommentVo> commentVos = toCommentVoList(pageSet.getRecords());

        List<CommentVo> finalResult = commentVos.stream().map(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
            return commentVo;
        }).collect(Collectors.toList());


        return ResponseResult.okResult(new PageVo(finalResult,pageSet.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 评论列表抽离出来的 vo转换封装的静态方法
     *
     * @param list
     * @return {@link List}<{@link CommentVo}>
     */
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
//        遍历vo集合

        commentVos = commentVos.stream().map(commentVo -> {
//        通过createBy查找用户的昵称并赋值给username
            commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName());
//            设置评论头像链接
            commentVo.setUsernameAvatar(userService.getById(commentVo.getCreateBy()).getAvatar());
//        通过toCommentUserId查找用户的昵称并赋值给toCommentUserName
            if (commentVo.getToCommentUserId() != SystemConstants.COMMENT_ROOTID) {
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
//                设置子评论头像链接
                commentVo.setToCommentUserNameAvatar(userService.getById(commentVo.getToCommentUserId()).getAvatar());
            }
            return commentVo;
        }).collect(Collectors.toList());
        return commentVos;
    }

    /**
     * 获取根评论的子评论
     *
     * @param id 根评论id
     * @return {@link List}<{@link CommentVo}>
     */
    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        return toCommentVoList(list);
    }
}
