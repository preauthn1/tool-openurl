//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysDictTypeTableDef extends TableDef {
//
//    /**
//     * <p>
// 字典类型
// </p>
//
// @author sz
// @since 2023-08-18
//     */
//    public static final SysDictTypeTableDef SYS_DICT_TYPE = new SysDictTypeTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn TYPE = new QueryColumn(this, "type");
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn IS_SHOW = new QueryColumn(this, "is_show");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn TYPE_CODE = new QueryColumn(this, "type_code");
//
//    public final QueryColumn TYPE_NAME = new QueryColumn(this, "type_name");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TYPE, IS_LOCK, IS_SHOW, REMARK, TYPE_CODE, TYPE_NAME, CREATE_TIME, UPDATE_TIME};
//
//    public SysDictTypeTableDef() {
//        super("", "sys_dict_type");
//    }
//
//    private SysDictTypeTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysDictTypeTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysDictTypeTableDef("", "sys_dict_type", alias));
//    }
//
//}
