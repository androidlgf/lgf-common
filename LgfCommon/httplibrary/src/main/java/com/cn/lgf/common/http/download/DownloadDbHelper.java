package com.cn.lgf.common.http.download;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.cn.lgf.common.http.debug.HttpLog;
import com.cn.lgf.common.http.db.DBConstants;
import com.cn.lgf.common.http.db.DBController;
import com.cn.lgf.common.http.exception.DBException;

/**
 * DB 存储Download状态
 */
public class DownloadDbHelper {
    //标识
    private static final String TAG = "DownloadDbHelper";
    //上下文
    private Context mContext;

    public DownloadDbHelper(Context context) {
        mContext = context;
    }

    /**
     * 根据Url查询DownloadInfo
     *
     * @param url
     * @return
     */
    public DownloadInfo queryDownloadInfo(String url) {
        DownloadInfo info = null;
        //SQL Controller
        DBController controller = DBController.getInstance(mContext);
        Cursor cursor = controller.query(DBConstants.TABLE_NAMES[DBConstants.TABLE_DOWN_CACHE], DBConstants.DownloadDB.COLUMNS[DBConstants.DownloadDB.URL], url);
        try {
            if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {
                info = new DownloadInfo();
                info.Id = cursor.getInt(DBConstants.DownloadDB.DOWNLOAD_ID);
                String downloadedSize = cursor.getString(DBConstants.DownloadDB.DOWNLOAD_SIZE);
                info.downloadSize = TextUtils.isEmpty(downloadedSize) ? 0 : Long.valueOf(downloadedSize);
                String totalSize = cursor.getString(DBConstants.DownloadDB.TOTAL_SIZE);
                info.totalSize = TextUtils.isEmpty(totalSize) ? 0 : Long.valueOf(totalSize);
                info.filePath = cursor.getString(DBConstants.DownloadDB.FILE_PATH);
                info.url = cursor.getString(DBConstants.DownloadDB.URL);
                String downloadStatus = cursor.getString(DBConstants.DownloadDB.DOWNLOAD_STATUS);
                info.status = !TextUtils.isEmpty(downloadStatus) ? DownloadStatus.getStatusByType(downloadStatus) : DownloadStatus.NONE;
                cursor.close();
            }
        } catch (Exception e) {
            HttpLog.i(TAG, TAG + "with queryDownloadInfo error：" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return info;
    }

    /**
     *
     * @param info
     */
    public void updateDownloadInfoForDB(DownloadInfo info) {
        try {
            DBController.getInstance(mContext).update(DBConstants.TABLE_NAMES[DBConstants.TABLE_DOWN_CACHE], DBConstants.DownloadDB.COLUMNS, downloadInfoForArray(info), DBConstants.DownloadDB.COLUMNS[DBConstants.DownloadDB.URL], info.url, 1);
        } catch (DBException e) {
            HttpLog.e(TAG, TAG + "with updateDownloadInfoForDB error：" + e.getMessage());
        }
    }

    /**
     *
     * @param info
     */
    public void insertDownloadInfoForDB(DownloadInfo info) {
        try {
            DBController.getInstance(mContext).insert(DBConstants.TABLE_NAMES[DBConstants.TABLE_DOWN_CACHE], DBConstants.DownloadDB.COLUMNS, downloadInfoForArray(info), 1);
        } catch (DBException e) {
            HttpLog.e(TAG, TAG + "with insertDownloadInfoForDB error：" + e.getMessage());
        }
    }

    /**
     *
     */
    public void deleteDownloadingInfo() {
        try {
            DBController.getInstance(mContext).delete(DBConstants.TABLE_NAMES[DBConstants.TABLE_DOWN_CACHE], DBConstants.DownloadDB.COLUMNS[DBConstants.DownloadDB.DOWNLOAD_STATUS], DownloadStatus.DOWNLOADING.getType());
        } catch (DBException e) {
            HttpLog.e(TAG, TAG + "with deleteDownloadingInfo error：" + e.getMessage());
        }
    }

    /**
     *
     * @param info
     */
    public void delete(DownloadInfo info) {
        try {
            DBController.getInstance(mContext).delete(DBConstants.TABLE_NAMES[DBConstants.TABLE_DOWN_CACHE], DBConstants.DownloadDB.COLUMNS[DBConstants.DownloadDB.DOWNLOAD_ID], String.valueOf(info.Id));
        } catch (DBException e) {
            HttpLog.e(TAG, TAG + "with delete error：" + e.getMessage());
        }
    }

    private String[] downloadInfoForArray(DownloadInfo info) {
        return new String[]{String.valueOf(info.Id), info.url, String.valueOf(info.totalSize), String.valueOf(info.downloadSize), info.status.getType(), info.filePath};
    }

    /***
     * DownLoad 下载文件信息
     */
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
