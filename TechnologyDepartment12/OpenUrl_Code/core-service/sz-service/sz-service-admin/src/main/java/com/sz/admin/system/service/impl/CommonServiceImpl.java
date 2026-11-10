package com.sz.admin.system.service.impl;

import com.sz.admin.system.pojo.vo.common.ChallengeVO;
import com.sz.admin.system.service.*;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.util.*;
import com.sz.redis.RedisCache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final RedisCache redisCache;


    @Override
    public ChallengeVO challenge() {
        HttpServletRequest request = HttpReqResUtil.getRequest();
        String requestId = Utils.generateSha256Id(Utils.generateAgentRequestId(request));
        int limit = Utils.getIntVal(SysConfigUtils.getConfValue("sys.login.requestLimit"));
        Long requestCycle = Utils.getLongVal(SysConfigUtils.getConfValue("sys.login.requestCycle"));

        if (limit != 0) {
            // 初始化请求限制
            redisCache.initializeLoginRequestLimit(requestId, requestCycle);
            Long cacheLimit = redisCache.countLoginRequestLimit(requestId);
            CommonResponseEnum.LOGIN_LIMIT.assertTrue(cacheLimit > limit);
        }

        // 根据request标识生成Sha256Id
        String secretKey = AESUtil.getRandomString(16);

        if (limit != 0) {
            redisCache.limitLoginRequest(requestId);
        }
        // 清除
        redisCache.clearLoginSecret(requestId);
        redisCache.putLoginSecret(requestId, secretKey, 60);
        return new ChallengeVO().setRequestId(requestId).setSecretKey(secretKey);
    }

}