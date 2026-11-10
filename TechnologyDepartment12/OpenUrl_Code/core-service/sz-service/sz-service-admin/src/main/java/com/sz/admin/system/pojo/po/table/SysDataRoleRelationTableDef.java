//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDataRoleRelationTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统数据角色-关联表
// </p>
//
// @author sz-admin
// @since 2024-07-11
//     */
//    public static final SysDataRoleRelationTableDef SYS_DATA_ROLE_RELATION = new SysDataRoleRelationTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
//
//    public final QueryColumn RELATION_ID = new QueryColumn(this, "relation_id");
//
//    public final QueryColumn RELATION_TYPE_CD = new QueryColumn(this, "relation_type_cd");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, RELATION_ID, RELATION_TYPE_CD};
//
//    public SysDataRoleRelationTableDef() {
//        super("", "sys_data_role_relation");
//    }
//
//    private SysDataRoleRelationTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDataRoleRelationTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDataRoleRelationTableDef("", "sys_data_role_relation", alias));
//    }
//
//}
