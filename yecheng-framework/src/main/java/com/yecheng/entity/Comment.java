package com.yecheng.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2022-10-13 20:54:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("yc_comment")
public class Comment  {
    @TableId
    private Long id;

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    //配合MyMetaObjectHandler的配置文件，说明是在插入的时候对这个字段进行填充
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    //配合MyMetaObjectHandler的配置文件，说明是在插入的时候对这个字段进行填充
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //配合MyMetaObjectHandler的配置文件，说明是在插入或者更新的时候对这个字段进行填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    //配合MyMetaObjectHandler的配置文件，说明是在插入或者更新的时候对这个字段进行填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
