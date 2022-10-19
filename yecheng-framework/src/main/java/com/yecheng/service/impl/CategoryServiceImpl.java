package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.dto.AdminCategoryPageDto;
import com.yecheng.domain.dto.AdminCategoryStatusDto;
import com.yecheng.domain.entity.Article;
import com.yecheng.domain.vo.AdminCategoryListVo;
import com.yecheng.domain.vo.PageVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.ArticleService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.CategoryMapper;
import com.yecheng.domain.entity.Category;
import com.yecheng.service.CategoryService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-10-13 01:05:18
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //先查询出文章表状态为已发布的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并去重
        Set<Long> categoryIds = articleList.stream().map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getPid,SystemConstants.CATEGORY_PID);
        queryWrapper.eq(Category::getStatus,0);
        List<Category> list = list(queryWrapper);
        if (list.isEmpty()){
            throw new SystemException(AppHttpCodeEnum.IS_NULL);
        }
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult categoryList(Integer pageNum, Integer pageSize, AdminCategoryPageDto categoryPageDto) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryPageDto.getName()),Category::getName,categoryPageDto.getName());
        queryWrapper.like(StringUtils.hasText(categoryPageDto.getStatus()),Category::getStatus,categoryPageDto.getStatus());
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        page(categoryPage,queryWrapper);
        if (Objects.isNull(categoryPage.getRecords())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<Category> categories = categoryPage.getRecords();
        List<AdminCategoryListVo> adminCategoryListVos = BeanCopyUtils.copyBeanList(categories, AdminCategoryListVo.class);
        return ResponseResult.okResult(new PageVo(adminCategoryListVos,categoryPage.getTotal()));
    }

    @Override
    public ResponseResult insertCategory(AdminCategoryPageDto categoryDto) {
        if (!StringUtils.hasText(categoryDto.getName())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(Long id) {
        Category category = getById(id);
        if (Objects.isNull(category)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        AdminCategoryListVo adminCategoryListVo = BeanCopyUtils.copyBean(category, AdminCategoryListVo.class);
        return ResponseResult.okResult(adminCategoryListVo);
    }

    @Override
    public ResponseResult updateCategory(AdminCategoryPageDto categoryDto) {
        if (Objects.isNull(categoryDto.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategoryById(Long id) {
        boolean b = removeById(id);
        if (!b){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatusCategory(AdminCategoryStatusDto statusDto) {
        if (Objects.isNull(statusDto.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = BeanCopyUtils.copyBean(statusDto, Category.class);
        updateById(category);
        return ResponseResult.okResult();
    }

}
