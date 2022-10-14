package com.yecheng.constants;

/**
 * 系统常量
 *
 * @author Yefl
 * @date 2022/10/12
 */
public class SystemConstants {
    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String STATUS_NORMAL = "0";

    /**
     * 链接状态正常
     */
    public static final int LINK_STATUS_NORMAL = 0;

    /**
     * 存入redis是userinfo的前缀
     */
    public static final String BLOG_LOGIN = "bloglogin:";

    /**
     * 请求头中token的字段
     */
    public static final String HEADER_TOKEN = "token";
    /**
     * 评论rootid 根评论的rootid为-1
     */
    public static final int COMMENT_ROOTID = -1;
    /**
     * 评论类型为文章评论
     */
    public static final String ARTICLE_COMMENT = "0";
    /**
     * 评论类型为友链评论
     */
    public static final String LINK_COMMENT = "1";
}