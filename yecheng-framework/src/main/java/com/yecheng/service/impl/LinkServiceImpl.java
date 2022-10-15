package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yecheng.constants.SystemConstants;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.LinkVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.LinkMapper;
import com.yecheng.domain.entity.Link;
import com.yecheng.service.LinkService;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-10-13 15:30:33
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
//        查询所有通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
//        转换成VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
//        封装返回
        return ResponseResult.okResult(linkVos);
    }
}
