package com.sz.applet.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author MrPing
 * @Date 2025/6/1
 * @Version 1.0
 */
@Data
public class SearchVo implements Serializable {

    private Integer current;

    private Integer size;

    private Integer type;

    private String name;

    private String id;

    private String bathCode;

}
