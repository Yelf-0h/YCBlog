package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.entity.Tag;
import com.yecheng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yelf
 * @create 2022-10-16-14:47
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(){
        List<Tag> list = tagService.list();
        return ResponseResult.okResult(list);
    }
}
