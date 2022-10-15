package com.yecheng.runner;

import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.entity.Article;
import com.yecheng.mapper.ArticleMapper;
import com.yecheng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yelf
 * @create 2022-10-14-21:12
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        //将查询博客信息  id  viewCount封装到map中
        Map<String,Integer> viewCountMap =  articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(),article -> article.getViewCount().intValue()));
        //viewCountMap存储到redis中
        redisCache.setCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX,viewCountMap);
    }
}
