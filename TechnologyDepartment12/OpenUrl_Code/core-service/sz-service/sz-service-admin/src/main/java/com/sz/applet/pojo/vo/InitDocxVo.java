package com.sz.applet.pojo.vo;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class InitDocxVo implements Serializable {

    /** html字符串 */
    private String htmlVal;

    /** 公式集合 */
    private JSONObject printVal;

    /** 表格气压 */
    private String selTable;

    /** 表格海拔 */
    private String selLabel;

    /** Q需 */
    private String maxValue;

    /** Q供 */
    private String qgValue;

    /** Q修 */
    private String qxXiu;

    /** Q修s */
    private String qxXius;

    /** h供 */
    private String hgValue;

}
