package com.yecheng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Yelf
 * @create 2022-10-17-22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVo {
    private Long id;

    /**
     * 标签名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
