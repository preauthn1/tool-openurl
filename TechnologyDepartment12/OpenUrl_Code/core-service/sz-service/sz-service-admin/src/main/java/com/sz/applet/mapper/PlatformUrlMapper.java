package com.sz.applet.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sz.applet.pojo.dto.SearchPlatDTO;
import com.sz.applet.pojo.po.PlatformUrl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 跳转链接 Mapper 接口
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@Mapper
public interface PlatformUrlMapper extends BaseMapper<PlatformUrl> {

    Page<PlatformUrl> pageList(Page<PlatformUrl> objectPage, @Param("search") SearchPlatDTO searchPlatDTO);
}

