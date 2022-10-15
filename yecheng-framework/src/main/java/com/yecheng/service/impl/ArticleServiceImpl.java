package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.entity.Category;
import com.yecheng.mapper.ArticleMapper;
import com.yecheng.domain.entity.Article;
import com.yecheng.service.ArticleService;
import com.yecheng.service.CategoryService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.RedisCache;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.ArticleDetailVo;
import com.yecheng.domain.vo.ArticleListVo;
import com.yecheng.domain.vo.HotArticleVo;
import com.yecheng.domain.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2022-10-12 11:31:55
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

        //获取redis中文章浏览量,设置到articles中
        articles.stream()
                .map(article -> {
                    Integer value = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX, article.getId().toString());
                    article.setViewCount(value.longValue());
                    return article;
                }).collect(Collectors.toList());


        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        //如果有categoryId 就要 查询时和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是发布状态
        queryWrapper.eq(Article::getStatus,SystemConstants.STATUS_NORMAL);
        //对isTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        //查询categoryName 以下是普通方法，会注释掉使用第二种lambda表达式
        //for (Article record : page.getRecords()) {
        //    Category category = categoryService.getById(record.getCategoryId());
        //    record.setCategoryName(category.getName());
        //}
        // 查询categoryName 方法2
        List<Article> articles = page.getRecords();
        articles.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());

        //获取redis中文章浏览量,设置到articles中
        articles.stream()
                .map(article -> {
                    Integer value = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX, article.getId().toString());
                    article.setViewCount(value.longValue());
                    return article;
                }).collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
//        根据id查询文章
        Article article = getById(id);
        //        获取redis中文章浏览量

        Integer value = redisCache.getCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX, id.toString());
        if (Objects.nonNull(value)&&value>0){
            article = article.setViewCount(value.longValue());
        }

//        转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
//        根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (Objects.nonNull(category)){
            articleDetailVo.setCategoryName(category.getName());
        }

//        封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
//        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX);
//        Integer integer = cacheMap.get(id);
//        if (Objects.isNull(integer)){
//            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
//        }
//        cacheMap.replace(id,integer,integer+1);
//        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX,cacheMap);

        redisCache.incrementCacheMapValue(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX,id.toString(),1);
        return ResponseResult.okResult();
    }


}

