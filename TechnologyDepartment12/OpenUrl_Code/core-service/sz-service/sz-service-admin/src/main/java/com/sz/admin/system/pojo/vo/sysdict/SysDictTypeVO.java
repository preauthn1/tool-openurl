package com.sz.admin.system.pojo.vo.sysdict;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "字典类型详情")
public class SysDictTypeVO {

    @Schema(description = "字典id", example = "修改时用")
    private Long id;

    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "账户状态")
    private String typeName;

    @Schema(description = "字典码(英文)", requiredMode = Schema.RequiredMode.REQUIRED, example = "account_status")
    private String typeCode;

    @Schema(description = "是否锁定,锁定的不可以再页面操作", allowableValues = "T,F", example = "F")
    private String isLock;

    @Schema(description = "是否显示", allowableValues = "T,F", example = "F")
    private String isShow;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Schema(description = "字典类型")
    private String type;

}
