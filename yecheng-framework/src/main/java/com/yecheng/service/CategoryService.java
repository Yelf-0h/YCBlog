package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.entity.Category;
import com.yecheng.utils.ResponseResult;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-13 01:05:17
 */
public interface CategoryService extends IService<Category> {

    /**
     * 得到类别列表
     *
     * @return {@link ResponseResult}
     */
    ResponseResult getCategoryList();
}
