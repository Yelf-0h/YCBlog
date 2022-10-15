package com.yecheng.controller;

import com.yecheng.service.CategoryService;
import com.yecheng.domain.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yelf
 * @create 2022-10-13-1:12
 */
@RestController
@RequestMapping("/category")
@Api(tags = "文章类别",description = "文章类别的相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation(value = "文章类别列表",notes = "获取文章的所有类别的列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
