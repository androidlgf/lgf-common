package com.cn.lgf.common.http.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cn.lgf.common.http.debug.HttpLog;

import java.util.List;

/**
 * SQLiteOpenHelper是一个辅助类
 * 管理数据库（创建、增、修、删） & 版本的控制。
 */
public class DBHelper extends SQLiteOpenHelper {
    private List<String> databaseCreate;
    private static DBHelper mDBHelper;

    private DBHelper(Context context, String name, int version, List<String> databaseCreate) {
        this(context, name, version, databaseCreate, null);
    }

    private DBHelper(Context context, String name, int version, List<String> databaseCreate, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, version);
        this.databaseCreate = databaseCreate;
    }

    /***
     * 单利生成DBHelper
     * @param context
     * @param name
     * @param version
     * @param databaseCreate
     * @param factory
     * @return
     */
    public static synchronized DBHelper getInstance(Context context, String name, int version, List<String> databaseCreate, SQLiteDatabase.CursorFactory factory) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context, name, version, databaseCreate, factory);
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if (databaseCreate != null && sqLiteDatabase != null) {
            execSql(sqLiteDatabase, databaseCreate);
        } else {
            throw new IllegalArgumentException(getClass().getSimpleName()
                    + "\tonCreate()");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        HttpLog.i("oldVersion=" + oldVersion + ",newVersion" + newVersion);
        if (newVersion <= oldVersion || sqLiteDatabase == null) {
            return;
        }
        execSql(sqLiteDatabase, databaseCreate);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 针对降级处理 ，如果版本小于上一个版本 ，先删除原先的表在创建新的表
        db.beginTransaction();
        try {
            for (String tableName : DBConstants.TABLE_NAMES) {
                db.execSQL("DROP TABLE " + tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        onCreate(db);
    }

    /***
     * 数据库的创建
     * @param db
     * @param createSQLs
     */
    private void execSql(SQLiteDatabase db, List<String> createSQLs) {
        db.beginTransaction();
        try {
            for (String sql : createSQLs) {
                db.execSQL(sql);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }
}
