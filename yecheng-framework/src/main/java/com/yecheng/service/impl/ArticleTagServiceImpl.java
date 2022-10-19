package com.yecheng.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.domain.entity.ArticleTag;
import com.yecheng.mapper.ArticleTagMapper;
import com.yecheng.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-18 09:02:34
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
