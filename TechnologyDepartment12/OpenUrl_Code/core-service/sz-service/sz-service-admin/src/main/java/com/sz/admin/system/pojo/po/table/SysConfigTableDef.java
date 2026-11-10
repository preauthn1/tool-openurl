//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysConfigTableDef extends TableDef {
//
//    /**
//     * <p>
// 参数配置表
// </p>
//
// @author sz
// @since 2023-11-23
//     */
//    public static final SysConfigTableDef SYS_CONFIG = new SysConfigTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn CONFIG_KEY = new QueryColumn(this, "config_key");
//
//    public final QueryColumn CONFIG_NAME = new QueryColumn(this, "config_name");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn CONFIG_VALUE = new QueryColumn(this, "config_value");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, IS_LOCK, REMARK, CREATE_ID, UPDATE_ID, CONFIG_KEY, CONFIG_NAME, CREATE_TIME, UPDATE_TIME, CONFIG_VALUE};
//
//    public SysConfigTableDef() {
//        super("", "sys_config");
//    }
//
//    private SysConfigTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysConfigTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysConfigTableDef("", "sys_config", alias));
//    }
//
//}
