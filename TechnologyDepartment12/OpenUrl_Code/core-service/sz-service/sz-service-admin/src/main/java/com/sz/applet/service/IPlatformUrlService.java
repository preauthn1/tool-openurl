package com.sz.applet.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sz.applet.pojo.dto.SearchPlatDTO;
import com.sz.applet.pojo.po.PlatformUrl;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 跳转链接 服务类
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
public interface IPlatformUrlService extends IService<PlatformUrl> {

    Page<PlatformUrl> pageList(SearchPlatDTO searchPlatDTO);
}
