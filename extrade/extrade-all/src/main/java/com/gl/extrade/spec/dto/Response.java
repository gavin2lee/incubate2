package com.gl.extrade.spec.dto;

public abstract class Response extends DTO {

    public static interface RetCode {
        String OK = "000000";
        String DEFAULT_ERR = "E00000";
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1522995681700282809L;

    private String retCode;
    private String retMsg;

    public Response() {
        super();
    }

    public Response(String retCode) {
        super();
        this.retCode = retCode;
    }

    public Response(String retCode, String retMsg) {
        super();
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
