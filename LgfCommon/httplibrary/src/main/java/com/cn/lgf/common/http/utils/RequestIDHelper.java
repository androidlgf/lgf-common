package com.cn.lgf.common.http.utils;

import java.io.Serializable;

public enum RequestIDHelper implements Serializable {
    INSTANCE;
    private int _ID = Integer.MIN_VALUE;

    /***
     * 生成RequestID
     * @return Http 生成请求标识
     */
    public int createRequestId() {
        _ID = _ID + 1;
        if (_ID >= Integer.MAX_VALUE) {
            _ID = Integer.MIN_VALUE;
            createRequestId();
        }
        return _ID;
    }
}