package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sz.mysql.EntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 用户-部门关系表
 * </p>
 *
 * @author sz
 * @since 2024-04-02
 */
@Data
@TableName("sys_user_dept")
@Schema(description = "用户-部门关系表")
public class SysUserDept implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "")
    private Long id;

    @Schema(description = "sys_dept_id")
    private Long deptId;

    @Schema(description = "sys_user_id")
    private Long userId;

}