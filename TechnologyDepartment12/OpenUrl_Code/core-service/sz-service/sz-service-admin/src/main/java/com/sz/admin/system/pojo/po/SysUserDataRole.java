package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 系统用户-数据角色关联表
 * </p>
 *
 * @author sz-admin
 * @since 2024-07-11
 */
@Data
@TableName(value = "sys_user_data_role")
@Schema(description = "系统用户-数据角色关联表")
public class SysUserDataRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "")
    private Long id;

    @Schema(description = "数据角色id (sys_data_role_id)")
    private Long roleId;

    @Schema(description = "用户id(sys_user_id)")
    private Long userId;

}