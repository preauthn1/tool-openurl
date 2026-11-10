package com.sz.safe;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 自定义 Post 方式 Wrapper
 */
@Slf4j
public class ParamEncryptPostRequestWrapper extends HttpServletRequestWrapper {

    String decryptedData;

    public ParamEncryptPostRequestWrapper(HttpServletRequest request, String decryptedData) {
        super(request);
        this.decryptedData = decryptedData;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        Map<String, Object> dataMap = JSONUtil.parseObj(decryptedData).toBean(Map.class);

        byte[] bytes = JSONUtil.toJsonStr(dataMap).getBytes("utf-8");
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}