//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysUserRoleTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统用户-角色关联表
// </p>
//
// @author sz
// @since 2023-08-29
//     */
//    public static final SysUserRoleTableDef SYS_USER_ROLE = new SysUserRoleTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
//
//    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, USER_ID};
//
//    public SysUserRoleTableDef() {
//        super("", "sys_user_role");
//    }
//
//    private SysUserRoleTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysUserRoleTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysUserRoleTableDef("", "sys_user_role", alias));
//    }
//
//}
