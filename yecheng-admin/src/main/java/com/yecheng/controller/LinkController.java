package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminInsertLinkDto;
import com.yecheng.domain.dto.AdminLinkPageDto;
import com.yecheng.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-10-19-20:30
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult adminLinkList(Integer pageNum, Integer pageSize, AdminLinkPageDto linkPageDto){
        return linkService.adminLinkList(pageNum,pageSize,linkPageDto);
    }

    @PostMapping
    public ResponseResult adminInsertLink(@RequestBody AdminInsertLinkDto insertLinkDto){
        return linkService.adminInsertLink(insertLinkDto);
    }

    @GetMapping("{id}")
    public ResponseResult adminGetLinkById(@PathVariable("id") Long id){
        return linkService.adminGetLinkById(id);
    }

    @PutMapping
    public ResponseResult adminUpdateLink(@RequestBody AdminInsertLinkDto linkDto){
        return linkService.adminUpdateLink(linkDto);
    }


    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        return linkService.deleteLink(id);
    }
}
