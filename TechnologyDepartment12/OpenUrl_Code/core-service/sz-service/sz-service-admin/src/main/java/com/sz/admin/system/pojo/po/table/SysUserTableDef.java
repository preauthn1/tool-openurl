//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysUserTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统用户表
// </p>
//
// @author sz
// @since 2023-08-24
//     */
//    public static final SysUserTableDef SYS_USER = new SysUserTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn AGE = new QueryColumn(this, "age");
//
//    public final QueryColumn PWD = new QueryColumn(this, "pwd");
//
//    public final QueryColumn SEX = new QueryColumn(this, "sex");
//
//    public final QueryColumn LOGO = new QueryColumn(this, "logo");
//
//    public final QueryColumn EMAIL = new QueryColumn(this, "email");
//
//    public final QueryColumn PHONE = new QueryColumn(this, "phone");
//
//    public final QueryColumn ID_CARD = new QueryColumn(this, "id_card");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn BIRTHDAY = new QueryColumn(this, "birthday");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn USERNAME = new QueryColumn(this, "username");
//
//    public final QueryColumn USER_TAG_CD = new QueryColumn(this, "user_tag_cd");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn LAST_LOGIN_TIME = new QueryColumn(this, "last_login_time");
//
//    public final QueryColumn ACCOUNT_STATUS_CD = new QueryColumn(this, "account_status_cd");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, AGE, PWD, SEX, LOGO, EMAIL, PHONE, ID_CARD, BIRTHDAY, CREATE_ID, NICKNAME, UPDATE_ID, USERNAME, USER_TAG_CD, CREATE_TIME, UPDATE_TIME, LAST_LOGIN_TIME, ACCOUNT_STATUS_CD};
//
//    public SysUserTableDef() {
//        super("", "sys_user");
//    }
//
//    private SysUserTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysUserTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysUserTableDef("", "sys_user", alias));
//    }
//
//}
