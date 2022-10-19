package com.yecheng.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.dto.TagListDto;
import com.yecheng.domain.entity.Tag;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-10-16 14:45:34
 */
public interface TagService extends IService<Tag> {

    /**
     * 页面标签列表
     *
     * @param pageNum    页面num
     * @param pageSize
     * @param tagListDto 标记列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 插入标记
     *
     * @param tagListDto 标记列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult insertTag(TagListDto tagListDto);

    /**
     * 删除标签
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult deleteTag(Long id);

    /**
     * 通过id获取标签
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    ResponseResult getTagById(Long id);

    /**
     * 更新标签
     *
     * @param tagListDto 标记列表dto
     * @return {@link ResponseResult}
     */
    ResponseResult updateTag(TagListDto tagListDto);

    /**
     * 列出所有标签
     *
     * @return {@link ResponseResult}
     */
    ResponseResult listAllTag();
}
