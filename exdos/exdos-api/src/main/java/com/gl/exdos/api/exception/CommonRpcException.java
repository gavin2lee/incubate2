package com.gl.exdos.api.exception;

public class CommonRpcException extends Exception {

    public static interface ErrCode {
        String SYSTEM_ERR = "SE0000";
        String BIZ_ERR = "BE0000";
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String errCode;

    public CommonRpcException(String errCode) {
        super();
        this.errCode = errCode;
    }

    public CommonRpcException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public CommonRpcException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public CommonRpcException(String errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

}
