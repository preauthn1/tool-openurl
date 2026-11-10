//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysUserDeptTableDef extends TableDef {
//
//    /**
//     * <p>
// 用户-部门关系表
// </p>
//
// @author sz
// @since 2024-04-02
//     */
//    public static final SysUserDeptTableDef SYS_USER_DEPT = new SysUserDeptTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");
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
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, DEPT_ID, USER_ID};
//
//    public SysUserDeptTableDef() {
//        super("", "sys_user_dept");
//    }
//
//    private SysUserDeptTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysUserDeptTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysUserDeptTableDef("", "sys_user_dept", alias));
//    }
//
//}
