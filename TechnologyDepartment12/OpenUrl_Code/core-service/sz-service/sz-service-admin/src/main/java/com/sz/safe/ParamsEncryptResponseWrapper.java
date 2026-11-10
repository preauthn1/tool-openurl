package com.sz.safe;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义 Response Wrapper
 */
public class ParamsEncryptResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream outputStream;

    public ParamsEncryptResponseWrapper(HttpServletResponse response) {
        super(response);
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // Not implemented
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        outputStream.flush();
    }

    public String getResponseData() {
        byte[] dataBytes = outputStream.toByteArray();
        return new String(dataBytes, StandardCharsets.UTF_8);
    }
}
