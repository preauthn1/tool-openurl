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
 * 部门祖籍关系表
 * </p>
 *
 * @author sz
 * @since 2024-03-28
 */
@Data
@TableName("sys_dept_closure")
@Schema(description = "部门祖籍关系表")
public class SysDeptClosure implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "祖先节点ID")
    private Long ancestorId;

    @Schema(description = "后代节点ID")
    private Long descendantId;

    @Schema(description = "祖先节点到后代节点的距离")
    private Integer depth;

}