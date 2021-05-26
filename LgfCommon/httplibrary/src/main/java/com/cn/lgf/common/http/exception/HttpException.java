package com.cn.lgf.common.http.exception;

public class HttpException extends Exception{
    private static final long serialVersionUID = 1L;

    public HttpException() {
        super();
    }

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(String msg, Exception exception) {
        super(msg, exception);
    }

    public HttpException(Exception exception) {
        super(exception);
    }
}
