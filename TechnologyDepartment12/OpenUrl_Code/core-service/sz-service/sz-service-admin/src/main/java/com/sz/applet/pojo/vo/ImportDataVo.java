package com.sz.applet.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author MrPing
 * @Date 2025/8/24
 * @Version 1.0
 */
@Data
public class ImportDataVo implements Serializable {

    /**
     * 机号
     */
    @ExcelProperty(value = "机号", index = 0)
    private String machineSn;


    /**
     * 序号
     */
    @ExcelProperty(value = "序号", index = 1)
    private Integer sort;

    /**
     * 转速
     */
    @ExcelProperty(value = "转速", index = 2)
    private Integer rotatingSpeed;

    /**
     * 最小流量
     */
    @ExcelProperty(value = "最小流量", index = 3)
    private Integer minFlow;

    /**
     * 最大流量
     */
    @ExcelProperty(value = "最大流量", index = 4)
    private Integer maxFlow;

    /**
     * 最小压力
     */
    @ExcelProperty(value = "最小压力", index = 5)
    private Integer minPa;

    /**
     * 最大压力
     */
    @ExcelProperty(value = "最大压力", index = 6)
    private Integer maxPa;

    /**
     * 功率
     */
    @ExcelProperty(value = "功率", index = 7)
    private BigDecimal power;

    /**
     * 配套功率
     */
    @ExcelProperty(value = "配套功率", index = 8)
    private BigDecimal assortPower;

    /**
     * 配套数量
     */
    @ExcelProperty(value = "配套数量", index = 9)
    private Integer num;

    /**
     * 噪音
     */
    @ExcelProperty(value = "噪音", index = 10)
    private String noise;

    /**
     * 控制方式
     */
    @ExcelProperty(value = "控制方式", index = 11)
    private String type;
}
