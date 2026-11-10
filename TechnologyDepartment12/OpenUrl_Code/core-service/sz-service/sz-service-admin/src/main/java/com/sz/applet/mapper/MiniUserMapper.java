package com.sz.applet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sz.applet.pojo.po.MiniUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 小程序用户表 Mapper 接口
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Mapper
public interface MiniUserMapper extends BaseMapper<MiniUser> {

}