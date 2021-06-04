package com.cn.lgf.common.http.download;

import androidx.annotation.NonNull;

/**
 * 下载状态
 */
public enum DownloadStatus {
    DOWNLOAD_FAILED("1"),
    DOWNLOAD_SUCCESS("2"),
    DOWNLOADING("3"),
    NONE("0");

    String mType = "0";

    DownloadStatus(String type) {
        mType = type;
    }

    public static DownloadStatus getStatusByType(@NonNull String type) {
        DownloadStatus status;
        switch (type) {
            //下载失败
            case "1":
                status = DOWNLOAD_FAILED;
                break;
            //下载成功
            case "2":
                status = DOWNLOAD_SUCCESS;
                break;
            //正在下载中ing
            case "3":
                status = DOWNLOADING;
                break;
            //无状态
            default:
                status = NONE;
                break;
        }
        return status;
    }

    public String getType() {
        return mType;
    }
}
