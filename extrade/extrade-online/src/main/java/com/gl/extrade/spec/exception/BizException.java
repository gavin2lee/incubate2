package com.gl.extrade.spec.exception;

public class BizException extends Exception {

    public static interface ErrCode {
        String SYSTEM_ERR = "SE0000";
        String BIZ_ERR = "BE0000";
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String errCode;

    public BizException(String errCode) {
        super();
        this.errCode = errCode;
    }

    public BizException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public BizException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BizException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

}
