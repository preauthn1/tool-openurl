package com.sz.applet.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchPlatDTO implements Serializable {

    private Integer current;

    private Integer size;

    private Integer id;

    private Integer status;
}
