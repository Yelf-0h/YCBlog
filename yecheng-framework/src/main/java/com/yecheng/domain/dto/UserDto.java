package com.yecheng.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(description = "用户dto")
public class UserDto {
    private Long id;

    @ApiModelProperty(notes = "用户名")
    private String userName;
    @ApiModelProperty(notes = "用户昵称")
    private String nickName;
    @ApiModelProperty(notes = "密码")
    private String password;
    @ApiModelProperty(notes = "用户类型：0代表普通用户，1代表管理员")
    private String type;
    @ApiModelProperty(notes = "账号状态（0正常 1停用）")
    private String status;
    @ApiModelProperty(notes = "邮箱")
    private String email;
    @ApiModelProperty(notes = "手机号")
    private String phonenumber;
    @ApiModelProperty(notes = "用户性别（0男，1女，2未知）")
    private String sex;
    @ApiModelProperty(notes = "头像")
    private String avatar;
    @ApiModelProperty(notes = "创建人的用户id")
    private Long createBy;
    @ApiModelProperty(notes = "创建时间")
    private Date createTime;
    @ApiModelProperty(notes = "更新人")
    private Long updateBy;
    @ApiModelProperty(notes = "更新时间")
    private Date updateTime;
    @ApiModelProperty(notes = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

}
