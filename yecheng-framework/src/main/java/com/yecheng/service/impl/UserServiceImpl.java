package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.domain.dto.AdminInsertUserDto;
import com.yecheng.domain.dto.AdminUpdateUserDto;
import com.yecheng.domain.dto.AdminUserListDto;
import com.yecheng.domain.dto.AdminUserStatusDto;
import com.yecheng.domain.entity.Role;
import com.yecheng.domain.entity.UserRole;
import com.yecheng.domain.vo.AdminGetUserDetailVo;
import com.yecheng.domain.vo.AdminUserListVo;
import com.yecheng.domain.vo.PageVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.mapper.UserRoleMapper;
import com.yecheng.service.RoleService;
import com.yecheng.service.UserRoleService;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.domain.ResponseResult;
import com.yecheng.utils.SecurityUtils;
import com.yecheng.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.UserMapper;
import com.yecheng.domain.entity.User;
import com.yecheng.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-13 15:59:44
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public ResponseResult userInfo() {
//        获取当前用户id
        Long userId = SecurityUtils.getUserId();
//        根据id查询用户信息
        User user = getById(userId);
//        封装成前端所要的UserInfoVo通过ResponseResult统一响应格式返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {
//        对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
//        对数据进行重复判断
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
//        对密码进行加密处理
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
//        存入数据库
        save(user);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminUserList(Integer pageNum, Integer pageSize, AdminUserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        queryWrapper.like(StringUtils.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.eq(StringUtils.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        Page<User> userPage = new Page<>(pageNum,pageSize);
        page(userPage,queryWrapper);
        if (Objects.isNull(userPage.getRecords())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<AdminUserListVo> adminUserListVos = BeanCopyUtils.copyBeanList(userPage.getRecords(), AdminUserListVo.class);
        return ResponseResult.okResult(new PageVo(adminUserListVos,userPage.getTotal()));
    }

    @Override
    @Transactional
    public ResponseResult insertUser(AdminInsertUserDto user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行重复判断
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if(phonenumberExist(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //对密码进行加密然后存进数据库
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        User newUser = BeanCopyUtils.copyBean(user, User.class);
        save(newUser);
        //获取roleIds创造userRoles然后存进数据库
        List<Long> roleIds = user.getRoleIds();
        List<UserRole> userRoles = roleIds.stream().map(
                roleId -> new UserRole(newUser.getId(), roleId)
        ).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteUser(Long id) {
        if (SecurityUtils.getUserId().equals(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"不能删除当前操作的用户");
        }
        boolean b = removeById(id);
        if (!b){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminGetUserDetailById(Long id) {
        //获取roleIds
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoles = userRoleService.list(queryWrapper);
        List<Long> roleIds = userRoles.stream().map(
                userRole -> userRole.getRoleId()
        ).collect(Collectors.toList());

        //根据roleIds获取roles
        LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.orderByAsc(Role::getRoleSort);
        List<Role> roles = roleService.list(queryWrapper1);
        //根据id获取用户信息
        User user = getById(id);
        //准备封装成vo形式返回
        AdminGetUserDetailVo adminGetUserDetailVo = new AdminGetUserDetailVo();
        adminGetUserDetailVo.setRoleIds(roleIds);
        adminGetUserDetailVo.setRoles(roles);
        adminGetUserDetailVo.setUser(BeanCopyUtils.copyBean(user, AdminUserListVo.class));

        return ResponseResult.okResult(adminGetUserDetailVo);
    }

    @Override
    @Transactional
    public ResponseResult adminUpdateUser(AdminUpdateUserDto updateUserDto) {
        if (Objects.isNull(updateUserDto.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        //更新user
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        updateById(user);
        //获取roleIds更新user-role表
        List<Long> roleIds = updateUserDto.getRoleIds();
        List<UserRole> userRoles = roleIds.stream().map(
                roleId -> new UserRole(user.getId(), roleId)
        ).collect(Collectors.toList());
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(queryWrapper);
        userRoleService.saveBatch(userRoles);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(AdminUserStatusDto statusDto) {
        if (Objects.isNull(statusDto.getUserId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        User user = new User();
        user.setId(statusDto.getUserId());
        user.setStatus(statusDto.getStatus());
        updateById(user);
        return ResponseResult.okResult();
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        User user = getOne(queryWrapper);
        if (Objects.nonNull(user)){
            return true;
        }else {
            return false;
        }
    }
    private boolean phonenumberExist(String phonenumber) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        User user = getOne(queryWrapper);
        if (Objects.nonNull(user)){
            return true;
        }else {
            return false;
        }
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        User user = getOne(queryWrapper);
        if (Objects.nonNull(user)){
            return true;
        }else {
            return false;
        }
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user = getOne(queryWrapper);
        if (Objects.nonNull(user)){
            return true;
        }else {
            return false;
        }
    }
}
