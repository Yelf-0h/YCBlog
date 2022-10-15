package com.yecheng.job;

import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.entity.Article;
import com.yecheng.service.ArticleService;
import com.yecheng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yelf
 * @create 2022-10-14-21:19
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisCache redisCache;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void UpdateViewCount(){
//        获取redis中的游览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEWCOUNT_PREFIX);
//        更新数据库中的游览量
        List<Article> articleList = cacheMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
        articleService.updateBatchById(articleList);

    }
}
