//package com.sz.admin.system.pojo.po.table;
//
//import com.mybatisflex.core.query.QueryColumn;
//import com.mybatisflex.core.table.TableDef;
//
//// Auto generate by mybatis-flex, do not modify it.
//public class SysRoleMenuTableDef extends TableDef {
//
//    /**
//     * <p>
// 系统角色-菜单表
// </p>
//
// @author sz
// @since 2023-08-21
//     */
//    public static final SysRoleMenuTableDef SYS_ROLE_MENU = new SysRoleMenuTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn MENU_ID = new QueryColumn(this, "menu_id");
//
//    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
//
//    /**
//     * 所有字段。
//     */
//    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
//
//    /**
//     * 默认字段，不包含逻辑删除或者 large 等字段。
//     */
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, MENU_ID, ROLE_ID};
//
//    public SysRoleMenuTableDef() {
//        super("", "sys_role_menu");
//    }
//
//    private SysRoleMenuTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public SysRoleMenuTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new SysRoleMenuTableDef("", "sys_role_menu", alias));
//    }
//
//}
