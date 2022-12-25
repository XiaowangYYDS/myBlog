package com.teng.job;

import com.teng.constants.SystemConstants;
import com.teng.domin.entity.Article;
import com.teng.service.ArticleService;
import com.teng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:xiaowang
 * @Data:2022/09/28/16:10 Description:
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    //每隔五分钟更新一次文章的浏览量
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void updateViewCount(){
        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.ARTICLE_VIEW_COUNT);
        //对数据进行处理
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //如果数据不为空 更新到数据库中
        if (articles != null && articles.size() > 0){
            articleService.updateBatchById(articles);
        }
    }
}
