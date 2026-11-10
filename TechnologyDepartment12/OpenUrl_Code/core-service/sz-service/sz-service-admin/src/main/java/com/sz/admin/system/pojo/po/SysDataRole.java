package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 数据权限管理
 * </p>
 *
 * @author sz-admin
 * @since 2024-07-09
 */
@Data
@TableName("sys_data_role")
@Schema(description = "数据权限管理")
public class SysDataRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "角色id")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "数据范围")
    private String dataScopeCd;

    @Schema(description = "简介")
    private String remark;

    @Schema(description = "删除与否")
    @TableLogic
    private Integer delFlag;

    @Schema(description = "是否锁定")
    private String isLock;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Schema(description = "创建人")
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;


    @Schema(description = "更新人")
    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    private Long updateId;


}