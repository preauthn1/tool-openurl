//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDeptTableDef extends TableDef {
//
//    /**
//     * <p>
// 部门表
// </p>
//
// @author sz
// @since 2024-03-20
//     */
//    public static final SysDeptTableDef SYS_DEPT = new SysDeptTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn PID = new QueryColumn(this, "pid");
//
//    public final QueryColumn DEEP = new QueryColumn(this, "deep");
//
//    public final QueryColumn NAME = new QueryColumn(this, "name");
//
//    public final QueryColumn SORT = new QueryColumn(this, "sort");
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn HAS_CHILDREN = new QueryColumn(this, "has_children");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PID, DEEP, NAME, SORT, IS_LOCK, REMARK, CREATE_ID, UPDATE_ID, CREATE_TIME, UPDATE_TIME, HAS_CHILDREN};
//
//    public SysDeptTableDef() {
//        super("", "sys_dept");
//    }
//
//    private SysDeptTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDeptTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDeptTableDef("", "sys_dept", alias));
//    }
//
//}
