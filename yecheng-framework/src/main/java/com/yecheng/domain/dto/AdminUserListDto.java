package com.yecheng.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yelf
 * @create 2022-10-15-15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "后台用户搜索dto")
public class AdminUserListDto {

    @ApiModelProperty(notes = "用户名")
    private String userName;
    @ApiModelProperty(notes = "账号状态（0正常 1停用）")
    private String status;
    @ApiModelProperty(notes = "手机号")
    private String phonenumber;

}
