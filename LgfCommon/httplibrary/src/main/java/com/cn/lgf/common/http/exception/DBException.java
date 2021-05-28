package com.cn.lgf.common.http.exception;

public class DBException extends Exception {
    private static final long serialVersionUID = 1L;

    public DBException() {
        super();
    }

    public DBException(String msg) {
        super(msg);
    }

    public DBException(String msg, Exception exception) {
        super(msg, exception);
    }

    public DBException(Exception exception) {
        super(exception);
    }
}
