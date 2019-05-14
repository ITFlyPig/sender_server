package com.wangyuelin.app.bean;

public class Code {
    private String code;
    private long time;//发送的时间，用来超时过期使用
    private String phone;//手机号

    public Code(String code, long time, String phone) {
        this.code = code;
        this.time = time;
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public long getTime() {
        return time;
    }

    public String getPhone() {
        return phone;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
