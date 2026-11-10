package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.*;

import com.sz.mysql.EntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author sz
 * @since 2023-08-21
 */

@Data
@TableName("sys_role")
@Schema(description = "系统角色表")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "角色id")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "简介")
    private String remark;

    @Schema(description = "删除与否")
    @TableLogic
    private Integer delFlag;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Schema(description = "")
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Long createId;


    @Schema(description = "")
    @TableField(value = "update_id", fill = FieldFill.UPDATE)
    private Long updateId;


    @Schema(description = "是否锁定")
    private String isLock;

    @Schema(description = "标识，唯一")
    private String permissions;
}
