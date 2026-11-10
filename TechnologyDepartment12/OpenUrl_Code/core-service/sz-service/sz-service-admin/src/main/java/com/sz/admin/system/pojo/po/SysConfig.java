package com.sz.admin.system.pojo.po;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author sz
 * @since 2023-11-23
 */

@Data
@TableName("sys_config")
@Schema(description = "参数配置表")
public class SysConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "id")
    private Long id;

    @Schema(description = "参数名")
    private String configName;

    @Schema(description = "参数key")
    private String configKey;

    @Schema(description = "参数value")
    private String configValue;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否锁定")
    private String isLock;

    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    private Long updateId;


    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
