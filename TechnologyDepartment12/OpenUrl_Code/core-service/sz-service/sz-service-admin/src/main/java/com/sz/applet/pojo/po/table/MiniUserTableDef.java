//package com.sz.applet.pojo.po.table;
//
//import com.mybatisflex.core.table.TableDef;
//
//import com.mybatisflex.core.query.QueryColumn;
//
//public class MiniUserTableDef extends TableDef {
//
//    /**
//     * <p>
//     小程序用户表
//     </p>
//
//     @author sz
//     @since 2024-04-26
//     */
//    public static final MiniUserTableDef MINI_USER = new MiniUserTableDef();
//
//    public final QueryColumn ID = new QueryColumn(this, "id");
//
//    public final QueryColumn SEX = new QueryColumn(this, "sex");
//
//    public final QueryColumn NAME = new QueryColumn(this, "name");
//
//    public final QueryColumn PHONE = new QueryColumn(this, "phone");
//
//    public final QueryColumn OPENID = new QueryColumn(this, "openid");
//
//    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
//
//    public final QueryColumn UNIONID = new QueryColumn(this, "unionid");
//
//    public final QueryColumn NICKNAME = new QueryColumn(this, "nickname");
//
//    public final QueryColumn AVATAR_URL = new QueryColumn(this, "avatar_url");
//
//    public final QueryColumn SUBSCRIBE = new QueryColumn(this, "subscribe");
//
//    public final QueryColumn SYS_USER_ID = new QueryColumn(this, "sys_user_id");
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
//    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SEX, NAME, PHONE, OPENID, DEL_FLAG, UNIONID, NICKNAME, AVATAR_URL, SUBSCRIBE, SYS_USER_ID, CREATE_TIME, UPDATE_TIME};
//
//    public MiniUserTableDef() {
//        super("", "mini_user");
//    }
//
//    private MiniUserTableDef(String schema, String name, String alisa) {
//        super(schema, name, alisa);
//    }
//
//    public MiniUserTableDef as(String alias) {
//        String key = getNameWithSchema() + "." + alias;
//        return getCache(key, k -> new MiniUserTableDef("", "mini_user", alias));
//    }
//}
