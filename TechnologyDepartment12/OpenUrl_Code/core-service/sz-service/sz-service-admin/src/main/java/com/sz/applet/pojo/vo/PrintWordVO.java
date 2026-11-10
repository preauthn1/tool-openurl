package com.sz.applet.pojo.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author MrPing
 * @Date 2025/8/23
 * @Version 1.0
 */
@Data
public class PrintWordVO implements Serializable {

    private String htmlVal;

    private JSONObject jsonVal;

    private String userId;

    private String userName;

    private String fileUrl;
}
