package com.sz.applet.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author MrPing
 * @Date 2025/8/24
 * @Version 1.0
 */
@Data
public class FacilityVo implements Serializable {

    private Integer current;

    private Integer size;

    // 机号
    private String machineSn;

    // 控制方式
    private String type;

    // min流量
    private Integer minFlow;

    // max流量
    private Integer maxFlow;

    // min压力
    private Integer minPa;

    // max压力
    private Integer maxPa;
}
