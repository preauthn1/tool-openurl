//package com.sz.safe;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.sz.core.common.entity.ResultMsg;
//import com.sz.utils.AesUtils;
//import com.sz.utils.RsaUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.util.UrlPathHelper;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//
///**
// * 请求参数加密、解密过滤器
// *
// * @author Tellsea
// * @date 2023/8/6
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@EnableConfigurationProperties(ParamsEncryptProperties.class)
//@ConditionalOnProperty(prefix = "jmemoir.safe.params.encrypt", name = "enabled", havingValue = "true")
//public class ParamsEncryptFilter implements Filter {
//
//    public static final String POST = "POST";
//    public static final String GET = "GET";
//    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
//    private static final UrlPathHelper URL_PATH_HELPER = new UrlPathHelper();
//
//    private final ParamsEncryptProperties properties;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        String lookupPathForRequest = URL_PATH_HELPER.getLookupPathForRequest(httpRequest);
//        log.info("请求路径：{}", lookupPathForRequest);
//        for (String ignoreUrlPattern : properties.getWhiteUrlList()) {
//            if (ANT_PATH_MATCHER.match(ignoreUrlPattern, lookupPathForRequest)) {
//                log.info("白名单：{}", lookupPathForRequest);
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//        String method = httpRequest.getMethod();
//        if (!method.equalsIgnoreCase(POST) && !method.equalsIgnoreCase(GET)) {
//            httpResponse.getWriter().write(JSONUtil.toJsonStr(ResultMsg.error("请使用 GET 或 POST 方式请求")));
//            return;
//        }
//        HttpServletRequestWrapper requestWrapper;
//        httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        httpResponse.setContentType("text/plain");
//
//        String reqHead = httpRequest.getHeader(properties.getReqHead());
//        log.info("请求头部：{}", reqHead);
//        if (StrUtil.isEmpty(reqHead)) {
//            httpResponse.getWriter().write(JSONUtil.toJsonStr(ResultMsg.error("请求头不能为空")));
//            return;
//        }
//        String aesKey;
//        try {
//            reqHead = base64UrlDecode(reqHead);
//            aesKey = RsaUtils.decrypt(reqHead, properties.getPrivateKey());
//            if (method.equalsIgnoreCase(POST)) {
//                String reqBody = getPostParams(httpRequest);
//
//                ResultMsg checkReqBody = checkReqBody(reqBody);
//                if (!(checkReqBody.getCode() == 0)) {
//                    httpResponse.getWriter().write(JSONUtil.toJsonStr(checkReqBody));
//                    return;
//                }
//                String encryptData = String.valueOf(checkReqBody.getData());
//                encryptData = base64UrlDecode(encryptData);
//                String decryptedData = AesUtils.decrypt(aesKey, encryptData);
//                log.info("解密参数：{}", decryptedData);
//
//                requestWrapper = new ParamEncryptPostRequestWrapper(httpRequest, decryptedData);
//            } else {
//                String reqBody = httpRequest.getParameter(properties.getReqBody());
//
//                if (StrUtil.isEmpty(reqBody)) {
//                    httpResponse.getWriter().write(JSONUtil.toJsonStr(ResultMsg.error("请求参数不能为空")));
//                    return;
//                }
//                reqBody = base64UrlDecode(reqBody);
//                String decryptedData = AesUtils.decrypt(aesKey, reqBody);
//                log.info("解密参数：{}", decryptedData);
//
//                requestWrapper = new ParamsEncryptGetRequestWrapper(httpRequest, decryptedData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            httpResponse.getWriter().write(JSONUtil.toJsonStr(ResultMsg.error("请求参数解析异常")));
//            return;
//        }
//        ParamsEncryptResponseWrapper responseWrapper = new ParamsEncryptResponseWrapper(httpResponse);
//        chain.doFilter(requestWrapper, responseWrapper);
//
//        String result = responseWrapper.getResponseData();
//        httpResponse.getWriter().write(result);
//        try {
//            log.info("响应结果：{}", result);
//            String encryptedResult = AesUtils.encrypt(aesKey, result);
//            log.info("响应加密：{}", encryptedResult);
//
//            httpResponse.getWriter().write(encryptedResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//            httpResponse.getWriter().write(JSONUtil.toJsonStr(ResultMsg.error("响应结果处理异常")));
//        }
//    }
//
//    private ResultMsg checkReqBody(String reqBody) {
//        if (!JSONUtil.isTypeJSONObject(reqBody)) {
//            return ResultMsg.error("请求参数格式不是json");
//        }
//        JSONObject reqBodyObj = JSONUtil.parseObj(reqBody);
//        String encryptedBody = reqBodyObj.getStr("reqBody");
//        if (StrUtil.isBlank(encryptedBody)) {
//            return ResultMsg.error("请求参数缺失或为空");
//        }
//        String encryptData = reqBodyObj.getStr(properties.getReqBody());
//        log.info("请求参数：{}", encryptData);
//        if (StrUtil.isEmpty(encryptData)) {
//            return ResultMsg.error("请求参数不能为空");
//        }
//        return ResultMsg.success(encryptData);
//    }
//
//    private String getPostParams(HttpServletRequest request) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        }
//        return sb.toString();
//    }
//
//    private static String base64UrlDecode(String input) {
//        String base64 = input
//                .replace('-', '+')
//                .replace('_', '/');
//
//        int padding = base64.length() % 4;
//        if (padding > 0) {
//            base64 += "=".repeat(4 - padding);
//        }
//        return base64;
//    }
//}
