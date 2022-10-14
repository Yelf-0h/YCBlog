package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.entity.Link;
import com.yecheng.utils.ResponseResult;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-13 15:30:33
 */
public interface LinkService extends IService<Link> {

    /**
     * 得到所有链接
     *
     * @return {@link ResponseResult}
     */
    ResponseResult getAllLink();
}
