package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminArticleDto;
import com.yecheng.domain.dto.InsertArticleDto;
import com.yecheng.domain.dto.UpdateAdminArticleDto;
import com.yecheng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-10-18-0:34
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody InsertArticleDto insertArticleDto){
        return articleService.addArticle(insertArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult adminArticleList(Integer pageNum, Integer pageSize, AdminArticleDto articleDto){
        return articleService.adminArticleList(pageNum,pageSize,articleDto);
    }

    @GetMapping("{id}")
    public ResponseResult selectArticleById(@PathVariable("id") Long id){
        return articleService.selectArticleById(id);
    }

    @PutMapping
    public ResponseResult adminUpdateArticle(@RequestBody UpdateAdminArticleDto adminArticleDto){
        return articleService.adminUpdateArticle(adminArticleDto);
    }


    /**
     * 管理员删除文章
     * 要有文章管理的权限才能删除
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @DeleteMapping("{id}")
    @PreAuthorize("@ps.hasPermission('content:article:delete')")
    public ResponseResult adminDeleteArticle(@PathVariable("id") Long id){
        return articleService.adminDeleteArticle(id);
    }


}
