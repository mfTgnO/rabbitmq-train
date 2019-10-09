package com.example.rabbitmqdemo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @createDate: 2019-06-12 14:32
 * @description:
 */
@Setter
@Getter
public class JsonResult<T> implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /*@JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;*/

    private String code;

    private String msg;

    /**
     * 若没有数据返回，默认状态码为0，提示信息为：操作成功！
     */
    public JsonResult() {
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 根据code对象返回
     */
    public JsonResult(Code code) {
        this.code = code.code;
        this.msg = code.msg;
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     *
     * @param code
     * @param msg
     */
    public JsonResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 有数据返回时，状态码为0，默认提示信息为：操作成功！
     *
     * @param data
     */
    public JsonResult(T data) {
        this.data = data;
        this.code = "0";
        this.msg = "操作成功！";
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     *
     * @param data
     * @param msg
     */
    public JsonResult(T data, String msg) {
        this.data = data;
        this.code = "0";
        this.msg = msg;
    }

    /**
     * 有数据返回，状态码为0，人为指定提示信息
     *
     * @param data
     * @param total
     */
    public JsonResult(T data, Integer total) {
        this.data = data;
        this.code = "0";
        this.msg = "操作成功！";
//        this.total = total;
    }

    public JsonResult(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = (T) builder.data;
    }


    public JsonResult Msg(String msg) {
        this.msg = msg;
        return new JsonResult(this);
    }

    public final static String SUCCESS = "0";
    public final static String FAIL = "1";
    public final static String EXCEPTION = "-1";

    public enum Code {
        /**
         * 调用成功
         */
        SUCCESS("0", "调用成功"),
        /**
         * 操作失败
         */
        FAIL("1", "操作失败"),
        /**
         * 请求异常
         */
        EXCEPTION("-1", "请求异常");

        private final String code;
        private String msg;

        Code(String code) {
            this.code = code;
        }

        Code(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }


        public String code() {
            return this.code;
        }

        public String msg() {
            return this.msg;
        }
    }

    public static class Builder<T> {

        private String code;

        private String msg;

        private HashMap<String, Object> data;

        public Builder code(Code code) {
            this.code = code.code();
            return this;
        }

        public Builder message(String message) {
            this.msg = message;
            return this;
        }

        public Builder data(String key, Object value) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }
            this.data.put(key, value);
            return this;
        }

        public Builder data(Map<String, Object> map) {
            if (this.data == null) {
                this.data = new HashMap<>();
            }
            this.data.putAll(map);
            return this;
        }


        public JsonResult build() {
            return new JsonResult(this);
        }

        JsonResult build(Code code) {
            this.code = code.code;
            this.msg = code.msg;
            return new JsonResult(this);
        }

        public JsonResult build(boolean result) {
            return build(result ? Code.SUCCESS : Code.FAIL);
        }
    }
}