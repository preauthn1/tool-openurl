package com.sz.applet.pojo.po;

import com.baomidou.mybatisplus.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

import com.sz.mysql.EntityChangeListener;

import java.time.LocalDateTime;

/**
 * <p>
 * 小程序用户表
 * </p>
 *
 * @author sz
 * @since 2024-04-26
 */
@Data
@TableName("mini_user")
@Schema(description = "小程序用户表")
public class MiniUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "")
    private String id;

    @Schema(description = "关联的系统用户ID")
    private String sysUserId;

    @Schema(description = "小程序用户的唯一标识")
    private String openid;

    @Schema(description = "公众号的唯一标识")
    private String unionid;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "真实姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "用户头像URL")
    private String avatarUrl;

    @Schema(description = "是否订阅公众号（1是0否）")
    private Integer subscribe;

    @Schema(description = "性别，0-未知 1-男性，2-女性")
    private Integer sex;

    @Schema(description = "删除标识")
    @TableLogic
    private Integer delFlag;

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


    @Schema(description = "上级id")
    private String parentId;

    @Schema(description = "公众号Id")
    private String publicId;

    @Schema(description = "类型【0 -> 会员，1 -> 商城经营者】")
    private Integer type;

    @Schema(description = "配送类型【0->物流，1->自动发货】")
    private Integer sendType;

    @Schema(description = "分销员【0->否，1->是】")
    private Integer isSaleType;

}