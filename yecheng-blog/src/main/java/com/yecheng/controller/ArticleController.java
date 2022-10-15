package com.yecheng.controller;

import com.yecheng.service.ArticleService;
import com.yecheng.domain.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-10-12-17:28
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章",description = "文章的相关接口")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public String test(){
//        return articleService.list().toString();
//    }

    @GetMapping("/hotArticleList")
    @ApiOperation(value = "热门文章",notes = "获取热门文章列表")
    public ResponseResult hotArticleList(){
        //查询热门文章，封装成ResponseResult返回
        return articleService.hotArticleList();
    }

    @GetMapping("/articleList")
    @ApiOperation(value = "文章列表",notes = "获取一页文章列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小"),
            @ApiImplicitParam(name = "categoryId",value = "文章类别Id,若存在则只显示此类别的文章")
    })
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "文章详情",notes = "根据Id获取此文章的详情")
    @ApiImplicitParam(name = "id",value = "需要显示详情的文章Id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "文章游览量",notes = "更新文章游览量,使其+1")
    @ApiImplicitParam(name = "id",value = "需要更新游览量的文章Id")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }



}
