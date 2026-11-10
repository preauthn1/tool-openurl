package com.sz.applet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sz.applet.pojo.dto.MiniLoginDTO;
import com.sz.applet.pojo.po.MiniLoginUser;
import com.sz.applet.pojo.po.MiniUser;

import com.sz.applet.pojo.vo.MiniUserVO;

/**
 * <p>
 * 小程序用户表 Service
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
public interface MiniUserService extends IService<MiniUser> {

    MiniUserVO doLogin(MiniLoginDTO dto);

    MiniLoginUser getUserByOpenId(String openId, String unionid);
}
