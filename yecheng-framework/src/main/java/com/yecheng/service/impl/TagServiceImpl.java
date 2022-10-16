package com.yecheng.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.TagMapper;
import com.yecheng.domain.entity.Tag;
import com.yecheng.service.TagService;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2022-10-16 14:45:34
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
