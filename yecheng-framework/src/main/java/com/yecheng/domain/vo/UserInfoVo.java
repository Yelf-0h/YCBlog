package com.yecheng.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息签证官
 *
 * @author Yefl
 * @date 2022/10/13
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}