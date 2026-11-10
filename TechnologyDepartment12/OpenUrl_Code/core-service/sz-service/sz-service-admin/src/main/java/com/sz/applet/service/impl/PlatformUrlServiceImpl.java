package com.sz.applet.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sz.applet.pojo.dto.SearchPlatDTO;
import com.sz.applet.pojo.po.PlatformUrl;
import com.sz.applet.mapper.PlatformUrlMapper;
import com.sz.applet.service.IPlatformUrlService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 跳转链接 服务实现类
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@Service
public class PlatformUrlServiceImpl extends ServiceImpl<PlatformUrlMapper, PlatformUrl> implements IPlatformUrlService {

    @Override
    public Page<PlatformUrl> pageList(SearchPlatDTO searchPlatDTO) {
        return baseMapper.pageList(new Page<>(searchPlatDTO.getCurrent(), searchPlatDTO.getSize()), searchPlatDTO);
    }
}
