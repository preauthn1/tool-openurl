package com.sz.applet.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 跳转链接
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@Getter
@Setter
@ToString
@TableName("platform_url")
public class PlatformUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 跳转链接
     */
    @TableField("open_url")
    private String openUrl;

    /**
     * 社交平台
     */
    @TableField("platform_id")
    private Integer platformId;

    /**
     * 平台名称
     */
    @TableField(exist = false)
    private String platformName;

    /**
     * 关联时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
