//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysMenuTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统菜单表
// </p>
//
// @author sz
// @since 2023-08-21
//     */
//    public static final SysMenuTableDef SYS_MENU = new SysMenuTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn PID = new QueryColumn(this, "pid");
//
//    public final QueryColumn DEEP = new QueryColumn(this, "deep");
//
//    public final QueryColumn ICON = new QueryColumn(this, "icon");
//
//    public final QueryColumn NAME = new QueryColumn(this, "name");
//
//    public final QueryColumn PATH = new QueryColumn(this, "path");
//
//    public final QueryColumn SORT = new QueryColumn(this, "sort");
//
//    public final QueryColumn TITLE = new QueryColumn(this, "title");
//
//    public final QueryColumn IS_FULL = new QueryColumn(this, "is_full");
//
//    public final QueryColumn IS_LINK = new QueryColumn(this, "is_link");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn IS_AFFIX = new QueryColumn(this, "is_affix");
//
//    public final QueryColumn CREATE_ID = new QueryColumn(this, "create_id");
//
//    public final QueryColumn DELETE_ID = new QueryColumn(this, "delete_id");
//
//    public final QueryColumn IS_HIDDEN = new QueryColumn(this, "is_hidden");
//
//    public final QueryColumn REDIRECT = new QueryColumn(this, "redirect");
//
//    public final QueryColumn UPDATE_ID = new QueryColumn(this, "update_id");
//
//    public final QueryColumn COMPONENT = new QueryColumn(this, "component");
//
//    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
//
//    public final QueryColumn DELETE_TIME = new QueryColumn(this, "delete_time");
//
//    public final QueryColumn MENU_TYPE_CD = new QueryColumn(this, "menu_type_cd");
//
//    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
//
//    public final QueryColumn HAS_CHILDREN = new QueryColumn(this, "has_children");
//
//    public final QueryColumn IS_KEEP_ALIVE = new QueryColumn(this, "is_keep_alive");
//
//    public final QueryColumn PERMISSIONS = new QueryColumn(this, "permissions");
//
//    public final QueryColumn USE_DATA_SCOPE = new QueryColumn(this, "use_data_scope");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, PID, DEEP, ICON, NAME, PATH, SORT, TITLE, IS_FULL, IS_LINK, IS_AFFIX, CREATE_ID, DELETE_ID, IS_HIDDEN, REDIRECT, UPDATE_ID, COMPONENT, CREATE_TIME, DELETE_TIME, MENU_TYPE_CD, UPDATE_TIME, HAS_CHILDREN, IS_KEEP_ALIVE, PERMISSIONS, USE_DATA_SCOPE};
//
//    public SysMenuTableDef() {
//        super("", "sys_menu");
//    }
//
//    private SysMenuTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysMenuTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysMenuTableDef("", "sys_menu", alias));
//    }
//
//}
