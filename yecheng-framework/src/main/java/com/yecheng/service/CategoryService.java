package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.dto.AdminCategoryPageDto;
import com.yecheng.domain.dto.AdminCategoryStatusDto;
import com.yecheng.domain.entity.Category;
import com.yecheng.domain.ResponseResult;

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

    /**
     * 列出所有类别(admin后台操作)
     *
     * @return {@link ResponseResult}
     */
    ResponseResult listAllCategory();

    /**
     * 类别列表
     *
     * @param pageNum         页面num
     * @param pageSize        页面大小
     * @param categoryPageDto 类别页面dto
     * @return {@link ResponseResult}
     */
    ResponseResult categoryList(Integer pageNum, Integer pageSize, AdminCategoryPageDto categoryPageDto);

    /**
     * 插入类别
     *
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    ResponseResult insertCategory(AdminCategoryPageDto categoryDto);

    /**
     * 通过id获取类别
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult getCategoryById(Long id);

    /**
     * 更新类别
     *
     * @param categoryDto 类别dto
     * @return {@link ResponseResult}
     */
    ResponseResult updateCategory(AdminCategoryPageDto categoryDto);

    /**
     * 删除类别id
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteCategoryById(Long id);

    /**
     * 改变状态分类
     *
     * @param statusDto 地位dto
     * @return {@link ResponseResult}
     */
    ResponseResult changeStatusCategory(AdminCategoryStatusDto statusDto);
}
