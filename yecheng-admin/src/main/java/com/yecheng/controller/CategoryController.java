package com.yecheng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson.JSON;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminCategoryPageDto;
import com.yecheng.domain.dto.AdminCategoryStatusDto;
import com.yecheng.domain.dto.AdminInsertLinkDto;
import com.yecheng.domain.entity.Category;
import com.yecheng.domain.vo.ExcelCategoryVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.CategoryService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yelf
 * @create 2022-10-18-0:14
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            WebUtils.setDownLoadHeader("分类表.xlsx",response);
            List<Category> categories = categoryService.list();
            if (categories.isEmpty()){
                throw new SystemException(AppHttpCodeEnum.IS_NULL);
            }
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);
        } catch (Exception e) {
            // 重置response
            response.reset();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult categoryList(Integer pageNum, Integer pageSize, AdminCategoryPageDto categoryPageDto){
        return categoryService.categoryList(pageNum,pageSize,categoryPageDto);
    }

    @PostMapping
    public ResponseResult insertCategory(@RequestBody AdminCategoryPageDto categoryDto){
        return categoryService.insertCategory(categoryDto);
    }

    @GetMapping("{id}")
    public ResponseResult getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody AdminCategoryPageDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatusCategory(@RequestBody AdminCategoryStatusDto statusDto){
        return categoryService.changeStatusCategory(statusDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteCategoryById(@PathVariable("id") Long id){
        return categoryService.deleteCategoryById(id);
    }
}
