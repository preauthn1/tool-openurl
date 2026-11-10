package com.sz.admin.system.pojo.po;

import com.baomidou.mybatisplus.annotation.*;

import com.sz.core.common.enums.TrueFalseEnum;
import com.sz.mysql.EntityChangeListener;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author sz
 * @since 2023-08-20
 */
@Data
@TableName("sys_dict")
@Schema(description = "字典表")
public class SysDict implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "字典id(规则)")
    private Long id;

    @Schema(description = "关联sys_dict_type id")
    private Long sysDictTypeId;

    @Schema(description = "字典名称")
    private String codeName;

    @Schema(description = "字典别名")
    private String alias;

    @Schema(description = "排序(正序)")
    private Integer sort;

    @Schema(description = "回显样式")
    private String callbackShowStyle;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否锁定")
    private TrueFalseEnum isLock;

    @Schema(description = "是否展示")
    private TrueFalseEnum isShow;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer delFlag;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}
