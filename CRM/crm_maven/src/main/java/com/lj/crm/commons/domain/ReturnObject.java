package com.lj.crm.commons.domain;

public class ReturnObject {
    private String code; // 1:success 0: failure
    private String message; // warning message
    private Object retData; //other data

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
