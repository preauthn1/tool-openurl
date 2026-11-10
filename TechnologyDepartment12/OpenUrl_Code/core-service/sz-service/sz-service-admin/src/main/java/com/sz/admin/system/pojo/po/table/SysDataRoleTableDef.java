//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDataRoleTableDef extends TableDef {
//
//    /**
//     * <p>
// 数据权限管理
// </p>
//
// @author sz-admin
// @since 2024-07-09
//     */
//    public static final SysDataRoleTableDef SYS_DATA_ROLE = new SysDataRoleTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn ROLE_NAME = new QueryColumn(this, "role_name");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn DATA_SCOPE_CD = new QueryColumn(this, "data_scope_cd");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, IS_LOCK, REMARK, CREATE_ID, ROLE_NAME, UPDATE_ID, CREATE_TIME, UPDATE_TIME, DATA_SCOPE_CD};
//
//    public SysDataRoleTableDef() {
//        super("", "sys_data_role");
//    }
//
//    private SysDataRoleTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDataRoleTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDataRoleTableDef("", "sys_data_role", alias));
//    }
//
//}
