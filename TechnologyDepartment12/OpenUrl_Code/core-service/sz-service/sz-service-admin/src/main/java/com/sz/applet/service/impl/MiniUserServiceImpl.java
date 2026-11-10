package com.sz.applet.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.applet.mapper.MiniUserMapper;
import com.sz.applet.pojo.dto.MiniLoginDTO;
import com.sz.applet.pojo.po.MiniLoginUser;
import com.sz.applet.pojo.po.MiniUser;
import com.sz.applet.pojo.vo.MiniUserVO;
import com.sz.applet.service.MiniUserService;
import com.sz.core.util.JsonUtils;
import com.sz.core.util.Utils;
import com.sz.wechat.WechatService;
import com.sz.wechat.pojo.LoginInfoResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 小程序用户表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MiniUserServiceImpl extends ServiceImpl<MiniUserMapper, MiniUser> implements MiniUserService {

    private final WechatService wechatService;

    @Override
    public MiniUserVO doLogin(MiniLoginDTO dto) {
        String REDACTED();
        LoginInfoResult loginInfoResult = wechatService.miniLogin(dto.getCode(), accessToken);
        log.info(" 小程序登录返回信息：{}", JsonUtils.toJsonString(loginInfoResult));
        // todo 结合实际业务进行处理
        return null;
    }

    @Override
    public MiniLoginUser getUserByOpenId(String openId, String unionid) {
        LambdaQueryWrapper<MiniUser> wrapper = Wrappers.lambdaQuery(MiniUser.class).eq(MiniUser::getOpenid, openId);
        MiniUser miniUser = getOne(wrapper);
        if (miniUser == null) {
            // todo 创建新的微信用户信息
        } else {
            // 绑定了sys_user账户
            if (Utils.isNotNull(miniUser.getSysUserId())) {
                // todo
            } else {
                // 未绑定sys_user账户
                // todo
            }
        }

        return null;
    }

}