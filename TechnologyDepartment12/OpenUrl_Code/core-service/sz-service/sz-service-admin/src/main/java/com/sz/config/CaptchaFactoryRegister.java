//package com.sz.config;
//
//import com.sz.service.CaptchaRedisService;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
///**
// * 注入 redis 到 缓存实例中
// */
//@Component
//public class CaptchaFactoryRegister implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final CaptchaRedisService captchaRedisService;
//
//    public CaptchaFactoryRegister(CaptchaRedisService captchaRedisService) {
//        this.captchaRedisService = captchaRedisService;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        // 注册自定义缓存到工厂
//        CaptchaServiceFactory.cacheService.put("redis", captchaRedisService);
//    }
//}