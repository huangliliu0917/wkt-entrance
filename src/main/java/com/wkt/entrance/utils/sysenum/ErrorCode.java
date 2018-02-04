package com.wkt.entrance.utils.sysenum;

public enum ErrorCode {

    DATEBASE_ERRPR(1001,"数据库操作异常"),
    NULL_ERROR(301,"空异常"),
    VERIFY_ERROR(302,"验证异常"),
    FILE_UPLOAD_ERROR(401,"文件上传异常"),
    NOT_LOGIN(403,"未登录"),
    NOT_FIND_ERROR(601,"数据未找到"),
    NOT_FIND_USER_ERROR(603,"未找到该用户"),
    KNOWS_ERROR(500, "已知异常"),
    UNKNOWNS_ERROR(501, "未知异常"),
    HTTPREQUESTMETHODNOTSUPPORTED(405,"请求方法不被允许"),

    ;
    /**
     * 说明
     */
    String description;

    /**
     * 代码
     */
    int code;

    ErrorCode( int code ,String description) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
