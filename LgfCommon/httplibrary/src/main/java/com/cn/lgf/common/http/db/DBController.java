package com.cn.lgf.common.http.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;

import com.cn.lgf.common.debug.DebugLog;
import com.cn.lgf.common.http.exception.DBException;

import java.util.List;

public class DBController {
    //标识
    private static final String TAG = "DBController";
    //SQL操作实现类
    private DBHandler dbHandler;

    private static DBController sDbController;

    /**
     * 单利
     *
     * @param context
     * @return
     */
    public static synchronized DBController getInstance(Context context) {
        if (sDbController == null || sDbController.isNotOpen()) {
            sDbController = new DBController(context.getApplicationContext());
        }
        return sDbController;
    }

    private DBController(Context context) {
        init(context);
    }

    DBHandler init(Context context) {
        if (dbHandler == null) {
            DBHelper dbHelper = DBHelper.getInstance(context, DBConstants.DATABASE_NAME, DBConstants.DATABASE_VERSION, DBConstants.DB_CREATE_SQL, null);
            dbHandler = new DBHandler(dbHelper);
        }
        if (!dbHandler.isOpen()) {
            try {
                dbHandler.openDatabase();
            } catch (SQLiteException e) {
                DebugLog.e(TAG, e);
            } catch (DBException e) {
                DebugLog.e(TAG, e);
            }
        }
        return dbHandler;
    }

    private boolean isNotOpen() {
        return dbHandler == null || !dbHandler.isOpen();
    }

    public boolean isOpen() {
        return !isNotOpen();
    }

    public void insert(String tableName, String columns[], String[] contents,
                       int startIndex) throws DBException {
        try {
            if (isNotOpen()) {
                return;
            }
            dbHandler.beginTransaction();
            ContentValues contentValues = buildContentValues(columns, startIndex,
                    contents);
            long temp = dbHandler.insert(tableName, contentValues);
            if (-1 == temp) {
                throw new DBException(getClass().getSimpleName()
                        + ": insert error ");
            }
            dbHandler.commitTransaction();
        } finally {
            dbHandler.endTransaction();
        }
    }

    public void insert(String tableName, String columns[], @NonNull List<String[]> contents,
                       int startIndex) throws DBException {

        try {
            if (isNotOpen()) {
                return;
            }
            dbHandler.beginTransaction();
            for (String[] content : contents) {
                ContentValues contentValues = buildContentValues(columns, startIndex,
                        content);
                long temp = dbHandler.insert(tableName, contentValues);
                if (-1 == temp) {
                    throw new DBException(getClass().getSimpleName()
                            + ": insert error ");
                }
            }
            dbHandler.commitTransaction();
        } finally {
            dbHandler.endTransaction();
        }
    }


    public boolean update(String tableName, String columns[], String[] contents, String targetColumn, String targetColumnValue, int startIndex) throws DBException {
        try {
            if (isNotOpen()) {
                return false;
            }
            dbHandler.beginTransaction();
            ContentValues contentValues = buildContentValues(columns, startIndex, contents);
            String whereClause = targetColumn + "=?";
            String[] whereArgs = {targetColumnValue};
            int temp = dbHandler.update(tableName, contentValues, whereClause, whereArgs);
            dbHandler.commitTransaction();
            return temp >= 1;
        } catch (Exception e) {
            DebugLog.e(TAG, e);
        } finally {
            dbHandler.endTransaction();
        }
        return false;
    }

    public void delete(String tableName, String columnName, String columnValue) throws DBException {
        try {
            if (isNotOpen()) {
                return;
            }
            dbHandler.beginTransaction();
            String whereClause = columnName + "=?";
            String whereArgs[] = {columnValue};
            dbHandler.delete(tableName, whereClause, whereArgs);
            dbHandler.commitTransaction();
        } catch (Exception e) {
            DebugLog.e(TAG, e);
        } finally {
            dbHandler.endTransaction();
        }
    }

    public void deleteAll(String tableName) throws DBException {
        try {
            if (isNotOpen()) {
                return;
            }
            dbHandler.beginTransaction();
            dbHandler.delete(tableName, null, null);
            dbHandler.commitTransaction();
        } catch (Exception e) {
            DebugLog.e(TAG, e);
        } finally {
            dbHandler.endTransaction();
        }
    }


    public Cursor query(String tableName, String columnName, String... argsValue) {
        if (isNotOpen()) {
            return null;
        }
        String querySQL = "select * from " + tableName + " where "
                + columnName + " =?";
        String[] selectionArgs = argsValue;
        Cursor cursor = dbHandler.query(querySQL, selectionArgs);
        return cursor;
    }

    public Cursor queryCount(String tableName) {
        if (isNotOpen()) {
            return null;
        }
        String sql = "select count(*) from " + tableName;
        Cursor cursor = dbHandler.query(sql, null);
        return cursor;
    }


    public Cursor query(String tableName) {
        if (isNotOpen()) {
            return null;
        }
        String querySQL = "select * from " + tableName;
        Cursor cursor = dbHandler.query(querySQL);
        return cursor;
    }

    public Cursor queryAll(String tableName) {
        if (isNotOpen()) {
            return null;
        }
        return dbHandler.query(tableName, null, null, null, null);
    }


    public ContentValues buildContentValues(String columns[], int startIndex,
                                            String[] contents) {
        if (columns == null || contents == null) {
            throw new IllegalArgumentException("array is null");
        }
        final int N = columns.length;
        if (startIndex < 0 || startIndex >= N || N != contents.length) {
            throw new IllegalArgumentException("array index error.");
        }
        ContentValues contentValues = new ContentValues();
        for (int i = startIndex; i < N; i++) {
            contentValues.put(columns[i], contents[i]);
        }
        return contentValues;
    }

    public boolean update(String tableName, int userIdIndex, int otherIdIndex, String columns[],
                          int startIndex, String[] contents, String otherid, String userid) throws DBException {
        if (isNotOpen()) {
            return false;
        }
        ContentValues contentValues = buildContentValues(columns, startIndex,
                contents);
        String whereClause = columns[userIdIndex] + "=? and " + columns[otherIdIndex] + "=?";
        String[] whereArgs = {userid, otherid};
        int temp = dbHandler.update(tableName, contentValues, whereClause,
                whereArgs);
        return temp >= 1;
    }

    public void onDestroy() {
        if (dbHandler != null) {
            dbHandler.closeDatabase();
        }
    }
}
