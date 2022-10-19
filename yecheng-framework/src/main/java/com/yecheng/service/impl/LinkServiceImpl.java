package com.yecheng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yecheng.constants.SystemConstants;
import com.yecheng.domain.dto.AdminInsertLinkDto;
import com.yecheng.domain.dto.AdminLinkPageDto;
import com.yecheng.domain.vo.AdminLinkVo;
import com.yecheng.domain.vo.PageVo;
import com.yecheng.enums.AppHttpCodeEnum;
import com.yecheng.exception.SystemException;
import com.yecheng.utils.BeanCopyUtils;
import com.yecheng.domain.ResponseResult;
import com.yecheng.domain.vo.LinkVo;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.mapper.LinkMapper;
import com.yecheng.domain.entity.Link;
import com.yecheng.service.LinkService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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

    @Override
    public ResponseResult adminLinkList(Integer pageNum, Integer pageSize, AdminLinkPageDto linkPageDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkPageDto.getName()),Link::getName,linkPageDto.getName());
        queryWrapper.eq(StringUtils.hasText(linkPageDto.getStatus()),Link::getStatus,linkPageDto.getStatus());
        Page<Link> linkPage = new Page<>(pageNum,pageSize);
        page(linkPage,queryWrapper);
        if (Objects.isNull(linkPage.getRecords())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        List<Link> links = linkPage.getRecords();
        List<AdminLinkVo> adminLinkVos = BeanCopyUtils.copyBeanList(links, AdminLinkVo.class);
        return ResponseResult.okResult(new PageVo(adminLinkVos,linkPage.getTotal()));
    }

    @Override
    public ResponseResult adminInsertLink(AdminInsertLinkDto insertLinkDto) {
        if (Objects.isNull(insertLinkDto.getName())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Link link = BeanCopyUtils.copyBean(insertLinkDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult adminGetLinkById(Long id) {
        Link link = getById(id);
        if (Objects.isNull(link)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        AdminLinkVo adminLinkVo = BeanCopyUtils.copyBean(link, AdminLinkVo.class);
        return ResponseResult.okResult(adminLinkVo);
    }

    @Override
    public ResponseResult adminUpdateLink(AdminInsertLinkDto linkDto) {
        if (Objects.isNull(linkDto.getId())){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        boolean b = removeById(id);
        if (!b){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }
}
