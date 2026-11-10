//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDeptClosureTableDef extends TableDef {
//
//    /**
//     * <p>
// 部门祖籍关系表
// </p>
//
// @author sz
// @since 2024-03-28
//     */
//    public static final SysDeptClosureTableDef SYS_DEPT_CLOSURE = new SysDeptClosureTableDef();
//
//    public final QueryColumn DEPTH = new QueryColumn(this, "depth");
//
//    public final QueryColumn ANCESTOR_ID = new QueryColumn(this, "ancestor_id");
//
//    public final QueryColumn DESCENDANT_ID = new QueryColumn(this, "descendant_id");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{DEPTH, ANCESTOR_ID, DESCENDANT_ID};
//
//    public SysDeptClosureTableDef() {
//        super("", "sys_dept_closure");
//    }
//
//    private SysDeptClosureTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDeptClosureTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDeptClosureTableDef("", "sys_dept_closure", alias));
//    }
//
//}
