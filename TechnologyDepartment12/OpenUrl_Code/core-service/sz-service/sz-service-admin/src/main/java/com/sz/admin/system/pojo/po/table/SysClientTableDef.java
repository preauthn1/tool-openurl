//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysClientTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统授权表
// </p>
//
// @author sz
// @since 2024-01-22
//     */
//    public static final SysClientTableDef SYS_CLIENT = new SysClientTableDef();
//
//    public final QueryColumn IS_LOCK = new QueryColumn(this, "is_lock");
//
//    public final QueryColumn REMARK = new QueryColumn(this, "remark");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn TIMEOUT = new QueryColumn(this, "timeout");
//
//    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn CLIENT_KEY = new QueryColumn(this, "client_key");
//
//    public final QueryColumn CREATE_DEPT = new QueryColumn(this, "create_dept");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn GRANT_TYPE_CD = new QueryColumn(this, "grant_type_cd");
//
//    public final QueryColumn CLIENT_SECRET = new QueryColumn(this, "client_secret");
//
//    public final QueryColumn DEVICE_TYPE_CD = new QueryColumn(this, "device_type_cd");
//
//    public final QueryColumn ACTIVE_TIMEOUT = new QueryColumn(this, "active_timeout");
//
//    public final QueryColumn CLIENT_STATUS_CD = new QueryColumn(this, "client_status_cd");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{IS_LOCK, REMARK, DEL_FLAG, TIMEOUT, CLIENT_ID, CREATE_ID, UPDATE_ID, CLIENT_KEY, CREATE_DEPT, CREATE_TIME, UPDATE_TIME, GRANT_TYPE_CD, CLIENT_SECRET, DEVICE_TYPE_CD, ACTIVE_TIMEOUT, CLIENT_STATUS_CD};
//
//    public SysClientTableDef() {
//        super("", "sys_client");
//    }
//
//    private SysClientTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysClientTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysClientTableDef("", "sys_client", alias));
//    }
//
//}
