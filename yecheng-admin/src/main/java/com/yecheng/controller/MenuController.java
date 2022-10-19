package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminMenuListDto;
import com.yecheng.domain.entity.Menu;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Yelf
 * @create 2022-10-18-21:17
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult menuList(AdminMenuListDto adminMenuListDto){
        return menuService.menuList(adminMenuListDto);
    }

    @PostMapping
    public ResponseResult insertMenu(@RequestBody Menu menu){
        if (Objects.isNull(menu)){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        return menuService.insertMenu(menu);
    }
    @GetMapping("/{id}")
    public ResponseResult getMenuDetailById(@PathVariable("id") Long id){
        return menuService.getMenuDetailById(id);
    }

    @PutMapping
    private ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @DeleteMapping("{menuId}")
    public ResponseResult deleteMenuById(@PathVariable("menuId") Long menuId){
        return menuService.deleteMenuById(menuId);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelectMenu(){
        return menuService.treeSelectMenu();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelectById(@PathVariable("id") Long id){
        return menuService.roleMenuTreeSelectById(id);
    }
}
