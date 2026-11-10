package com.sz.core.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回值
 *
 * @author MrPing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code = 0;
    private String message = "ok";
    private Object data;

    public ResultMsg(Object data) {
        this.data = data;
    }

    public static ResultMsg success() {
        return new ResultMsg();
    }

    public static ResultMsg success(Object data) {
        return new ResultMsg(data);
    }
    public static ResultMsg success(Integer code, String message) {
            return new ResultMsg(code, message, null);
    }
    public static ResultMsg error(String message) {
        return new ResultMsg(-1, message, null);
    }

    public static ResultMsg error(Integer code, String message) {
        return new ResultMsg(code, message, null);
    }

    public static ResultMsg error(Integer code, String message, Object data) {
        return new ResultMsg(code, message, data);
    }

    /**
     * <p>操作响应</p>
     * @author starshine 小郭
     */
    public static ResultMsg operation(boolean flag, String title) {
        return flag ? ResultMsg.success(title + "成功") : ResultMsg.error(title + "失败");
    }


    /**
     * <p>操作响应</p>
     * @author starshine 小郭
     */
    public static ResultMsg operation(boolean flag) {
        return flag ? ResultMsg.success("保存成功") : ResultMsg.error("保存失败");
    }
}
