package com.yecheng.service;

import com.yecheng.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Yelf
 * @create 2022-10-14-16:15
 */
public interface UploadService {
    /**
     * 上传img
     *
     * @param img img
     * @return {@link ResponseResult}
     */
    ResponseResult uploadImg(MultipartFile img);
}
