package com.yecheng.controller;

import com.yecheng.service.UploadService;
import com.yecheng.domain.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Yelf
 * @create 2022-10-14-16:11
 */
@RestController
@Api(tags = "文件上传",description = "文件上传相关接口")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传用户头像",notes = "上传用户头像")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img",value = "头像图片")
    })
    public ResponseResult uploadImg(MultipartFile img){
        try {
            return uploadService.uploadImg(img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }

    }
}
