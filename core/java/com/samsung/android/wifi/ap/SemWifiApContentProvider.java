package com.samsung.android.wifi.ap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SemWifiApContentProvider extends ContentProvider {
    static final String CREATE_DB_TABLE =
            " CREATE TABLE SemWifiApContentProvider (_id INTEGER PRIMARY KEY AUTOINCREMENT,  name"
                + " TEXT NOT NULL,  value TEXT NOT NULL);";
    static final String DATABASE_NAME = "SemWifiApContentProvider.db";
    static final int DATABASE_VERSION = 1;
    static final String NAME = "name";
    static final String PROVIDER_NAME = "com.samsung.android.wifi.softap";
    static final int SOFTAPINFO = 1;
    static final int SOFTAPINFO_ID = 2;
    private static HashMap<String, String> SOFTAPINFO_PROJECTION_MAP = null;
    static final String SOFTAPINFO_TABLE_NAME = "SemWifiApContentProvider";
    private static final String TAG = "SemWifiApContentProvider";
    static final String VALUE = "value";
    static final String _ID = "_id";
    private static SQLiteDatabase db;
    private static DatabaseHelper dbHelper;
    private static Context mContext;
    private static List<String> mMHSDumpLogs;
    static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final UriMatcher uriMatcher = new UriMatcher(-1);

    static {
        uriMatcher.addURI(PROVIDER_NAME, "softapInfo", 1);
        uriMatcher.addURI(PROVIDER_NAME, "softapInfo/#", 2);
        mMHSDumpLogs = new ArrayList();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(
                    context,
                    SemWifiApContentProvider.DATABASE_NAME,
                    (SQLiteDatabase.CursorFactory) null,
                    1);
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper constructor");
            SemWifiApContentProvider.mContext = context;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper onCreate");
            createTable(db);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            SemWifiApContentProvider.addMHSDumpLog("DatabaseHelper onUpgrade");
            dropTable(db);
            onCreate(db);
        }

        SQLiteDatabase getDatabase(boolean writable) {
            if (writable) {
                return getWritableDatabase();
            }
            return getReadableDatabase();
        }

        public void createTable(SQLiteDatabase db) {
            SemWifiApContentProvider.addMHSDumpLog("createTable");
            try {
                db.execSQL(SemWifiApContentProvider.CREATE_DB_TABLE);
            } catch (SQLException e) {
                Log.e("SemWifiApContentProvider", "couldn't create table in  database");
                SemWifiApContentProvider.addMHSDumpLog("couldn't create table in  database");
            }
        }

        public void dropTable(SQLiteDatabase db) {
            SemWifiApContentProvider.addMHSDumpLog("dropTable");
            try {
                db.execSQL("DROP TABLE IF EXISTS SemWifiApContentProvider");
            } catch (SQLException e) {
                Log.e("SemWifiApContentProvider", "couldn't drop table in  database");
                SemWifiApContentProvider.addMHSDumpLog("couldn't drop table in  database");
            }
        }
    }

    public SemWifiApContentProvider() {
        addMHSDumpLog("SemWifiApContentProvider constructor");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert("SemWifiApContentProvider", "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            Log.i("SemWifiApContentProvider", "inserted" + _uri);
            return _uri;
        }
        Log.e("SemWifiApContentProvider", "Could not add" + uri);
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();
        addMHSDumpLog("SemWifiApContentProvider onCreate,db created?" + (db != null));
        return db != null;
    }

    @Override // android.content.ContentProvider
    public synchronized Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        Cursor c;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("SemWifiApContentProvider");
        switch (uriMatcher.match(uri)) {
            case 1:
                qb.setProjectionMap(SOFTAPINFO_PROJECTION_MAP);
                break;
            case 2:
                qb.appendWhere("_id=" + uri.getPathSegments().get(1));
                break;
        }
        c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override // android.content.ContentProvider
    public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        count = 0;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = db.delete("SemWifiApContentProvider", selection, selectionArgs);
                break;
            case 2:
                String id = uri.getPathSegments().get(1);
                count =
                        db.delete(
                                "SemWifiApContentProvider",
                                "_id = "
                                        + id
                                        + (!TextUtils.isEmpty(selection)
                                                ? " AND (" + selection + ')'
                                                : ""),
                                selectionArgs);
                break;
            default:
                Log.d("SemWifiApContentProvider", "delete Unknown URI " + uri);
                break;
        }
        return count;
    }

    @Override // android.content.ContentProvider
    public synchronized int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch (uriMatcher.match(uri)) {
            case 1:
                count = db.update("SemWifiApContentProvider", values, selection, selectionArgs);
                break;
            case 2:
                count =
                        db.update(
                                "SemWifiApContentProvider",
                                values,
                                "_id = "
                                        + uri.getPathSegments().get(1)
                                        + (!TextUtils.isEmpty(selection)
                                                ? " AND (" + selection + ')'
                                                : ""),
                                selectionArgs);
                break;
            default:
                Log.e("SemWifiApContentProvider", "Could not update" + uri);
                return 0;
        }
        Log.i("SemWifiApContentProvider", "updated:" + uri);
        return count;
    }

    public static void insert(Context mContext2, String key, String val) {
        ContentValues values = new ContentValues();
        if (val == null) {
            val = "";
        }
        values.put("name", key);
        values.put("value", val);
        if (isKeypresent(mContext2, key)) {
            mContext2.getContentResolver().update(CONTENT_URI, values, null, null);
        } else {
            mContext2.getContentResolver().insert(CONTENT_URI, values);
        }
    }

    public static String get(Context mContext2, String key) {
        String[] selectionArgs = {key};
        Cursor c =
                mContext2
                        .getContentResolver()
                        .query(
                                Uri.parse("content://com.samsung.android.wifi.softap"),
                                null,
                                "name = ?",
                                selectionArgs,
                                null);
        String returnValue = "";
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    returnValue = c.getString(c.getColumnIndex("value"));
                }
            } finally {
                c.close();
            }
        }
        return returnValue;
    }

    private static boolean isKeypresent(Context mContext2, String key) {
        String[] selectionArgs = {key};
        Cursor c =
                mContext2
                        .getContentResolver()
                        .query(
                                Uri.parse("content://com.samsung.android.wifi.softap"),
                                null,
                                "name = ?",
                                selectionArgs,
                                null);
        if (c == null) {
            return false;
        }
        try {
            boolean ret = c.moveToFirst();
            return ret;
        } finally {
            c.close();
        }
    }

    public static synchronized void reCreateDB() {
        synchronized (SemWifiApContentProvider.class) {
            File semWifiApContentProviderFile = null;
            try {
                if (db != null) {
                    String dbPath = db.getPath();
                    Log.i("SemWifiApContentProvider", "reCreateDB: dbPath " + dbPath);
                    semWifiApContentProviderFile = new File(dbPath);
                }
                if (db == null
                        || !semWifiApContentProviderFile.exists()
                        || !db.isDatabaseIntegrityOk()) {
                    addMHSDumpLog("databaseIntegrity is not Ok");
                    mContext.deleteDatabase(DATABASE_NAME);
                    if (db != null && db.isOpen()) {
                        addMHSDumpLog("databaseIntegrity is not Ok,closing DB");
                        db.close();
                    }
                    db = dbHelper.getWritableDatabase();
                    addMHSDumpLog("SemWifiApContentProvider query,db created?" + (db != null));
                }
            } catch (SQLException | IllegalStateException e) {
                e.printStackTrace();
                Log.e("SemWifiApContentProvider", "reCreateDB: exception");
            }
        }
    }

    public static synchronized void addMHSDumpLog(String log) {
        synchronized (SemWifiApContentProvider.class) {
            StringBuffer value = new StringBuffer();
            value.append(
                    new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US)
                                    .format(Long.valueOf(System.currentTimeMillis()))
                            + " "
                            + log
                            + "\n");
            Log.i("SemWifiApContentProvider", log);
            if (mMHSDumpLogs.size() > 100) {
                mMHSDumpLogs.remove(0);
            }
            mMHSDumpLogs.add(value.toString());
        }
    }

    public static String getDumpLogs() {
        StringBuffer retValue = new StringBuffer();
        retValue.append("====== SemWifiApContentProvider dump =======  \n");
        retValue.append(mMHSDumpLogs.toString());
        retValue.append("\n");
        return retValue.toString();
    }
}
