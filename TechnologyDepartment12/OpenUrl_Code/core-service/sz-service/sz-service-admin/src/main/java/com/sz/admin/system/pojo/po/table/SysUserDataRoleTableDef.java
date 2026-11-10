//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysUserDataRoleTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统用户-数据角色关联表
// </p>
//
// @author sz-admin
// @since 2024-07-11
//     */
//    public static final SysUserDataRoleTableDef SYS_USER_DATA_ROLE = new SysUserDataRoleTableDef();
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
//    public SysUserDataRoleTableDef() {
//        super("", "sys_user_data_role");
//    }
//
//    private SysUserDataRoleTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysUserDataRoleTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysUserDataRoleTableDef("", "sys_user_data_role", alias));
//    }
//
//}
