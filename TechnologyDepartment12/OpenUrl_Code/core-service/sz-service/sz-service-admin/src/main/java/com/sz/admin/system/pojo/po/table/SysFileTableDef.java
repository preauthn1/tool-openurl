//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysFileTableDef extends TableDef {
//
//    /**
//     * <p>
//
// </p>
//
// @author sz
// @since 2023-08-31
//     */
//    public static final SysFileTableDef SYS_FILE = new SysFileTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn URL = new QueryColumn(this, "url");
//
//    public final QueryColumn PATH = new QueryColumn(this, "path");
//
//    public final QueryColumn SIZE = new QueryColumn(this, "size");
//
//    public final QueryColumn TYPE = new QueryColumn(this, "type");
//
//    public final QueryColumn FILENAME = new QueryColumn(this, "filename");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, URL, PATH, SIZE, TYPE, FILENAME, CREATE_TIME};
//
//    public SysFileTableDef() {
//        super("", "sys_file");
//    }
//
//    private SysFileTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysFileTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysFileTableDef("", "sys_file", alias));
//    }
//
//}
