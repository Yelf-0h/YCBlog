package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.dto.AdminInsertLinkDto;
import com.yecheng.domain.dto.AdminLinkPageDto;
import com.yecheng.domain.entity.Link;
import com.yecheng.domain.ResponseResult;

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

    /**
     * 管理链接列表
     *
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param linkPageDto 链接页面dto
     * @return {@link ResponseResult}
     */
    ResponseResult adminLinkList(Integer pageNum, Integer pageSize, AdminLinkPageDto linkPageDto);

    /**
     * 管理插入链接
     *
     * @param insertLinkDto
     * @return {@link ResponseResult}
     */
    ResponseResult adminInsertLink(AdminInsertLinkDto insertLinkDto);

    /**
     * 管理员通过id获取链接
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult adminGetLinkById(Long id);

    /**
     * 管理更新链接
     *
     * @param linkDto 链接dto
     * @return {@link ResponseResult}
     */
    ResponseResult adminUpdateLink(AdminInsertLinkDto linkDto);

    /**
     * 删除链接
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteLink(Long id);
}
