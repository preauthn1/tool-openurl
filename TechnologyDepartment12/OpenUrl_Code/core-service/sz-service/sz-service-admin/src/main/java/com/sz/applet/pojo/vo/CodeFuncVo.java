package com.sz.applet.pojo.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author MrPing
 * @Date 2025/8/21
 * @Version 1.0
 */
@Data
public class CodeFuncVo implements Serializable {

    /** 公式id */
    @NotBlank(message = "公式id不能为空")
    private Integer id;

    /** 录入的参数 */
    private Map<String, Double> vars;


}
