//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDictTableDef extends TableDef {
//
//    /**
//     * <p>
// 字典表
// </p>
//
// @author sz
// @since 2023-08-20
//     */
//    public static final SysDictTableDef SYS_DICT = new SysDictTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn SORT = new QueryColumn(this, "sort");
//
//    public final QueryColumn ALIAS = new QueryColumn(this, "alias");
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn IS_SHOW = new QueryColumn(this, "is_show");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn CODE_NAME = new QueryColumn(this, "code_name");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn SYS_DICT_TYPE_ID = new QueryColumn(this, "sys_dict_type_id");
//
//    public final QueryColumn CALLBACK_SHOW_STYLE = new QueryColumn(this, "callback_show_style");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SORT, ALIAS, IS_LOCK, IS_SHOW, REMARK, CODE_NAME, CREATE_TIME, UPDATE_TIME, SYS_DICT_TYPE_ID, CALLBACK_SHOW_STYLE};
//
//    public SysDictTableDef() {
//        super("", "sys_dict");
//    }
//
//    private SysDictTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDictTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDictTableDef("", "sys_dict", alias));
//    }
//
//}
