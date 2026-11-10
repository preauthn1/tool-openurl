package com.sz.applet.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 社交平台表
 * </p>
 *
 * @author MrPing
 * @since 2025-12-26
 */
@Getter
@Setter
@ToString
@TableName("platform")
public class Platform implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 平台id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 社交平台
     */
    @TableField("name")
    private String name;

    /**
     * 图片
     */
    @TableField("img_url")
    private String imgUrl;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 状态【0->未启用，1->启用】
     */
    @TableField("status")
    private Integer status;

    @TableField(exist = false)
    private List<PlatformUrl> platformUrls = new ArrayList<>();
}
