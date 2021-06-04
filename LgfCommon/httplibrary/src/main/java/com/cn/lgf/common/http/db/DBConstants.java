package com.cn.lgf.common.http.db;

import com.cn.lgf.common.http.debug.HttpLog;

import java.util.ArrayList;
import java.util.List;

/**
 * DB SQLite 常量表
 */
public class DBConstants {
    //db name
    public static final String DATABASE_NAME = "http_SQLite.db";
    //db version
    public static final int DATABASE_VERSION = 1;
    //Table表名集合
    public static final String[] TABLE_NAMES = {"download_cache_db"};
    //下载标识
    public static final int TABLE_DOWN_CACHE = 0;
    //Create SQL 语句
    public static List<String> DB_CREATE_SQL;

    private interface ITable {
        String produceCreateSQL();
    }

    /**
     * 这里使用了static块
     * 只需要进行一次的初始化操作
     * 节省内存空间，优化性能
     */
    static {
        DownloadDB downloadDB = new DownloadDB(TABLE_NAMES[TABLE_DOWN_CACHE]);
        DB_CREATE_SQL = new ArrayList<>(TABLE_NAMES.length);
        DB_CREATE_SQL.add(downloadDB.produceCreateSQL());
    }

    public static class DownloadDB implements ITable {
        //列标题
        public static final String[] COLUMNS = {"download_id", "url", "total_size", "download_size", "download_status", "file_path"};
        //下载标识
        public static final int DOWNLOAD_ID = 0;//
        //URL标识
        public static final int URL = 1;
        //文件大小标识
        public static final int TOTAL_SIZE = 2;
        //已下载大小标识
        public static final int DOWNLOAD_SIZE = 3;
        //下载状态标识
        public static final int DOWNLOAD_STATUS = 4;
        //下载路径标识
        public static final int FILE_PATH = 5;
        private String mTableName;

        public DownloadDB(String tableName) {
            this.mTableName = tableName;
        }

        /***
         * 创建了一个名为mTableName的表
         * SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
         * @return
         */
        @Override
        public String produceCreateSQL() {
            final String sql = "CREATE TABLE IF NOT EXISTS "
                    + mTableName + "(" + COLUMNS[DOWNLOAD_ID]
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMNS[URL]
                    + " INTEGER NOT NULL,"
                    + COLUMNS[TOTAL_SIZE]
                    + " TEXT NOT NULL,"
                    + COLUMNS[DOWNLOAD_SIZE]
                    + " TEXT NOT NULL,"
                    + COLUMNS[DOWNLOAD_STATUS]
                    + " TEXT NOT NULL,"
                    + COLUMNS[FILE_PATH]
                    + " TEXT NOT NULL)";
            HttpLog.d(getClass().getSimpleName(), " createSQL = " + sql);
            return sql;
        }
    }
}
