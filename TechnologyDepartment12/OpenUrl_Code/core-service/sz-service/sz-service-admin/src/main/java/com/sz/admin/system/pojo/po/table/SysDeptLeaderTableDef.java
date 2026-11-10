//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDeptLeaderTableDef extends TableDef {
//
//    /**
//     * <p>
// 部门领导人表
// </p>
//
// @author sz
// @since 2024-03-26
//     */
//    public static final SysDeptLeaderTableDef SYS_DEPT_LEADER = new SysDeptLeaderTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");
//
//    public final QueryColumn LEADER_ID = new QueryColumn(this, "leader_id");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, DEPT_ID, LEADER_ID};
//
//    public SysDeptLeaderTableDef() {
//        super("", "sys_dept_leader");
//    }
//
//    private SysDeptLeaderTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDeptLeaderTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDeptLeaderTableDef("", "sys_dept_leader", alias));
//    }
//
//}
