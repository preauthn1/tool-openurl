package com.sz.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES 加解密工具类
 */
@Slf4j
public class AesUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String decrypt(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)), StandardCharsets.UTF_8);
    }

    public static String encrypt(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    public static void main(String[] args) throws Exception {
        JSONObject json = new JSONObject();
        json.putOnce("timestamp", DateUtil.current());
//        String encrypt = encrypt(ParamEncryptFilter.AES_KEY, JSONUtil.toJsonStr(json));
//        String encode = URLEncoder.encode("ZQsqo66YoaT90a/T1YqACruLgInUpD8D9EDGdPykNfQwjUrmWzmAFYLRw20+QQud0GkIurLGMQDAp/iwuB1GTmFkASTZYnMfA6G33uQNyZU=", "UTF-8");
//        System.out.println(decrypt(ParamEncryptFilter.AES_KEY, URLDecoder.decode(encode, "UTF-8")));
    }
}