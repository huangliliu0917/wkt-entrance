package com.wkt.entrance.utils.sysenum;

import com.wkt.entrance.utils.CodeInterface;

/**
 * @author zmj
 */

public enum SysCode implements CodeInterface {

    //系统返回代码
    SYS_CODE_STATUS_SUCCESS(200, "成功"),

    //性别区分
    MAN(0, "男"),
    WOMAN(1, "女"),
    SLIDESHOW_STATE_ON(0,"启用"),
    SLIDESHOW_STATE_OFF(1,"关闭"),

    //State
    STATE_T(0,"有效"),
    STATE_F(1,"无效"),

    //Able
    IS_ABLE_YES(0,"已审核"),
    IS_ABLE_NO(1,"未通过"),
    IS_ABLE_WAIT(2,"待审核"),
    ;

    /**
     * 说明
     */
    String description;

    /**
     * 代码
     */
    int code;

    SysCode() {
    }

    SysCode(int code, String description) {
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

    @Override
    public String getInfo() {
        return this.description;
    }

    @Override
    public void print() {
        System.out.println(this.code + ":" + this.description);
    }

    public static SysCode codeOf(int code) {
        for (SysCode codes : values()) {
            if (codes.getCode() == code) {
                return codes;
            }
        }

        return null;
    }

}
