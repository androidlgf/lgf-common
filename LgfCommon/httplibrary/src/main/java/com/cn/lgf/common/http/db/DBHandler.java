package com.cn.lgf.common.http.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.cn.lgf.common.http.debug.HttpDebugLog;
import com.cn.lgf.common.http.exception.DBException;

/**
 * DB 操作类
 */
public class DBHandler {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static final String TAG = "DBHandler";

    public DBHandler(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        try {
            getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有打开的数据库对象
     */
    public void closeDatabase() {
        try {
            dbHelper.close();
        } catch (Exception e) {
            HttpDebugLog.i(TAG, e);
        }
    }

    /**
     * 创建或打开可以读/写的数据库
     *
     * @return
     * @throws DBException
     */
    public SQLiteDatabase getWritableDatabase()
            throws DBException {
        try {
            db = dbHelper.getWritableDatabase();
            return db;
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    /**
     * 创建或打开可以读/写的数据库
     *
     * @return
     * @throws DBException
     */
    public void openDatabase() throws DBException {
        try {
            this.getWritableDatabase();
        } catch (Exception e) {

        }
    }

    /***
     * 数据库对象是否开启状态
     * @return
     */
    public boolean isOpen() {
        boolean isOpen = true;
        try {
            if (db != null) {
                isOpen = db.isOpen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isOpen;
    }

    public void beginTransaction() {
        try {
            if (db != null) {
                db.beginTransaction();
            }
        } catch (Exception e) {

        }
    }

    public void commitTransaction() {
        try {
            if (db != null) {
                db.setTransactionSuccessful();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void endTransaction() {
        try {
            if (db != null) {
                db.endTransaction();
            }
        } catch (Exception e) {

        }
    }

    public Cursor query(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            if (db != null) {
                cursor = db.rawQuery(sql, selectionArgs);
            }
        } catch (Exception e) {

        }
        return cursor;
    }

    public Cursor query(String sql) {
        Cursor cursor = null;
        try {
            if (db != null) {
                cursor = db.rawQuery(sql, null);
            }
        } catch (Exception e) {

        }
        return cursor;
    }

    public Cursor query(boolean distinct, String table, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        Cursor cursor = null;
        try {
            cursor = db.query(distinct, table, columns, selection, selectionArgs,
                    groupBy, having, orderBy, limit);
        } catch (Exception e) {

        }
        return cursor;
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String orderBy) {
        Cursor cursor = null;
        try {
            if (db != null) {
                cursor = db.query(table, columns, selection, selectionArgs, null, null,
                        orderBy, null);
            }
        } catch (Exception e) {

        }
        return cursor;
    }

    public void execute(String sql) throws SQLException {
        try {
            if (db != null) {
                db.execSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public long insert(String table, ContentValues contentValues) {
        long result;
        try {
            result = db.insert(table, null, contentValues);
        } catch (Exception e) {
            return -1;
        }
        return result;
    }

    public int update(String table, ContentValues contentValues,
                      String whereClause, String[] whereArgs) {
        int result = 0;
        try {
            db.update(table, contentValues, whereClause, whereArgs);
        } catch (Exception e) {
            return -1;
        }
        return result;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int result;
        try {
            result = db.delete(table, whereClause, whereArgs);
        } catch (Exception e) {
            return -1;
        }
        return result;
    }
}
