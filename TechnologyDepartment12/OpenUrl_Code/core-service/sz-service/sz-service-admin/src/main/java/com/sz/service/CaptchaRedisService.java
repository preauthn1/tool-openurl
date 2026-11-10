//package com.sz.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * 验证码Redis实现服务
// *
// * @author MrPing
// * @version 1.0
// * @date 2022-11-30
// */
//@Component
//public class CaptchaRedisService implements CaptchaCacheService {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    private final String PREFIX = "CaptchaCode:";
//
//    private ValueOperations<String, String> opsForValue() {
//        return stringRedisTemplate.opsForValue();
//    }
//
//    @Override
//    public void set(String key, String value, long expireSeconds) {
//        opsForValue().set(PREFIX + key, value, expireSeconds, TimeUnit.SECONDS);
//    }
//
//    @Override
//    public boolean exists(String key) {
//        Boolean exists = stringRedisTemplate.hasKey(PREFIX + key);
//        return exists != null && exists;
//    }
//
//    @Override
//    public void delete(String key) {
//        stringRedisTemplate.delete(PREFIX + key);
//    }
//
//    @Override
//    public String get(String key) {
//        return opsForValue().get(PREFIX + key);
//    }
//
//    @Override
//    public String type() {
//        return "redis";
//    }
//}