package com.wh.web.myweb.constants;

import com.wh.web.myweb.model.vo.Result;

public class ResultConstant {

    /**
     * 默认成功返回
     */
    public static final Result SUCCESS = new Result("200", "处理成功");
    /**
     * 默认失败返回
     */
    public static final Result FAIL = new Result("500", "处理失败");

    /**
     * 默认异常返回
     */
    public static final Result EXCEPTION = new Result("555", "服务异常");

    /**
     * 默认身份验证不通过返回
     */
    public static final Result LOGIN_EXCEPTION = new Result("400", "用户身份信息验证不通过");

    /**
     * 自定义成功返回数据
     */
    public static Result newSuccess(Object data) {
        return new Result("200", "处理成功", data);
    }

    /**
     * 自定义失败返回数据
     */
    public static Result newFail(Object data) {
        return new Result("500", "处理失败", data);
    }
}
