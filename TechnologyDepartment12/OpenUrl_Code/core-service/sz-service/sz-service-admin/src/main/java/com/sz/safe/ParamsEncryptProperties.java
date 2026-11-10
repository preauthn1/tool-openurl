package com.sz.safe;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 参数加解密：属性配置
 */
@Data
@ConfigurationProperties(prefix = "jmemoir.safe.params.encrypt")
public class ParamsEncryptProperties {

    private boolean enabled;

    private String reqHead;

    private String reqBody;

    private String publicKey;

    private String privateKey;

    private List<String> whiteUrlList;
}