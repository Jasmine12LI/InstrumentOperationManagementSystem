package com.scsse.workflow.util.result;

/**
 * @author Alfred Fu
 * Created on 2019-03-07 20:52
 */
public enum ResultCode {
    CODE_200(200, "访问成功"),
    CODE_400(400, "API参数绑定失败，请查看API!"),
    CODE_402(401, "Method使用错误，请查看API"),
    CODE_403(402, "没有权限访问!"),
    CODE_404(403, "找不到访问地址"),
    CODE_500(500, "系统内部错误"),
    CODE_501(501, "对象已存在，请检测唯一性"),
    CODE_502(502, "对象不存在，请检测请求参数"),
    CODE_503(503, "无法删除！"),
    CODE_504(504, "用户名不存在"),
    CODE_505(505, "密码输入错误"),
    CODE_506(506, "该用户被锁定，无法登录"),
    CODE_507(507, "访问失败");
    private Integer code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

