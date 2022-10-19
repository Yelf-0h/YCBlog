package com.yecheng.controller;

import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.AdminAddRoleDto;
import com.yecheng.domain.dto.AdminRoleDto;
import com.yecheng.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yelf
 * @create 2022-10-19-0:06
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, AdminRoleDto adminRoleDto){
        return roleService.roleList(pageNum,pageSize, adminRoleDto);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody AdminRoleDto adminRoleDto){
        return roleService.changeStatus(adminRoleDto);
    }

    @PostMapping
    public ResponseResult insertRole(@RequestBody AdminAddRoleDto adminAddRoleDto){
        //逻辑代码
        return roleService.insertRole(adminAddRoleDto);
    }

    @GetMapping("{id}")
    public ResponseResult getRoleDetail(@PathVariable("id") Long id){
        return roleService.getRoleDetail(id);
    }

    @PutMapping
    public ResponseResult updateRole(@RequestBody AdminAddRoleDto addRoleDto){
        return roleService.updateRole(addRoleDto);
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteRoleById(@PathVariable("id") Long id){
        return roleService.deleteRoleById(id);
    }

    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }

}
