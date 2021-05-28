package com.cn.lgf.common.http.download;

import android.content.Context;

import androidx.annotation.NonNull;

/***
 * 下载状态描述
 */
enum DownloadStatus {
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
            case "1":
                status = DOWNLOAD_FAILED;
                break;

            case "2":
                status = DOWNLOAD_SUCCESS;
                break;

            case "3":
                status = DOWNLOADING;
                break;

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

/**
 * DB 存储Download状态
 */
public class DownloadDbHelper {
    //上下文
    private Context mContext;

    public DownloadDbHelper(Context context) {
        mContext = context;
    }
    public DownloadInfo queryDownloadInfo(String url){
        return null;
    }
    public static class DownloadInfo {
        //下载 Url
        String url;
        //下载 Tag 标识
        int Id;
        //文件总大小
        long totalSize;
        //下载中文件大小
        long downloadSize;
        //下载状态
        public DownloadStatus status;
        //保存地址
        String filePath;
    }
}
