package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 系统数据角色-菜单表
 * </p>
 *
 * @author sz-admin
 * @since 2024-07-11
 */
@Data
@TableName("sys_data_role_menu")
@Schema(description = "系统数据角色-菜单表")
public class SysDataRoleMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "")
    private Long id;

    @Schema(description = "sys_data_role_id （数据角色表）")
    private Long roleId;

    @Schema(description = "sys_menu_id （菜单表）")
    private String menuId;

}