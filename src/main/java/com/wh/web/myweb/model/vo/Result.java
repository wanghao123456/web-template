package com.wh.web.myweb.model.vo;

import lombok.Data;

@Data
public class Result<R> {
    /**
     * 状态码
     */
    private String code;
    /**
     * 状态简述
     */
    private String message;
    /**
     * 数据
     */
    private R data;

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(String code, String message, R data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
