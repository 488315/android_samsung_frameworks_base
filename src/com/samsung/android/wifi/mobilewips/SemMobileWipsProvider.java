package com.samsung.android.wifi.mobilewips;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemMobileWipsProvider extends ContentProvider {
    public static final String ATTACK_TYPE = "attack_type";
    private static final String AUTHORITY = "com.samsung.server.wifi.mwips";
    public static final String BEACON_SEEN = "beacon_seen";
    public static final String BEACON_TSF = "beacon_tsf";
    private static final String DB_NAME = "MobileWIPS.db";
    public static final String DB_TABLE_DUMP = "MobileWIPSDUMP";
    public static final String DB_TABLE_IELIST = "MobileWIPSIE";
    public static final String DB_TABLE_WHITELIST = "MobileWIPSWHITE";
    private static final int DB_VERSION_NEW = 10;
    private static final int DB_VERSION_NOP_UPGRADE_9 = 9;
    private static final int DB_VERSION_OLD = 9;
    public static final int DUMP = 2;
    public static final String EXCEPTION_TYPE = "exception_type";
    public static final String FREQUENCY = "frequency";
    public static final String HISTORY_ID = "history_id";
    public static final int IELIST = 1;
    public static final String IES = "ies";
    public static final String IE_SAVED_TIME = "time_saved";
    public static final String MAC_ADDR = "mac_addr";
    public static final String REASON_STR = "reason";
    public static final String SEEN_TIME = "seen_time";
    public static final String SSID_NAME = "ssid_name";
    private static final String TAG = "MobileWipsFrameworkProvider";
    public static final String TIME_STAMP = "time_stamp";
    public static final int WHITELIST = 0;
    private static final String[] mDBCreationSQL;
    private static final String[] mDBDeleteSQL;
    private static final UriMatcher sURIMatcher;
    private SQLiteOpenHelper mOpenHelper;
    public static final Uri CONTENT_URI_WHITELIST = Uri.parse("content://com.samsung.server.wifi.mwips/whitelist");
    public static final Uri CONTENT_URI_IELIST = Uri.parse("content://com.samsung.server.wifi.mwips/ielist");
    public static final Uri CONTENT_URI_DUMP = Uri.parse("content://com.samsung.server.wifi.mwips/dump");

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sURIMatcher = uriMatcher;
        mDBCreationSQL = new String[]{"CREATE TABLE IF NOT EXISTS MobileWIPSWHITE(history_id INTEGER PRIMARY KEY AUTOINCREMENT,mac_addr TEXT, exception_type Integer DEFAULT 0, ssid_name TEXT ) ", "CREATE TABLE IF NOT EXISTS MobileWIPSIE(history_id INTEGER PRIMARY KEY AUTOINCREMENT,mac_addr TEXT, frequency INTEGER, time_stamp LONG, seen_time LONG, ies varbinary, time_saved LONG, beacon_tsf LONG, beacon_seen LONG ) ", "CREATE TABLE IF NOT EXISTS MobileWIPSDUMP(history_id INTEGER PRIMARY KEY AUTOINCREMENT,time_stamp LONG, attack_type Integer, reason TEXT) "};
        mDBDeleteSQL = new String[]{"DROP TABLE IF EXISTS MobileWIPSWHITE", "DROP TABLE IF EXISTS MobileWIPSIE", "DROP TABLE IF EXISTS MobileWIPSDUMP"};
        uriMatcher.addURI(AUTHORITY, "whitelist", 0);
        uriMatcher.addURI(AUTHORITY, "ielist", 1);
        uriMatcher.addURI(AUTHORITY, "dump", 2);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        if (sURIMatcher.match(uri) == 0) {
            return "whitelist";
        }
        Log.d(TAG, "calling getType on an unknown URI: " + uri);
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mOpenHelper = new DatabaseHelper(getContext(), 9);
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (str != null && str.equals("drop")) {
            Log.d(TAG, "drop allowlist table");
            this.mOpenHelper = new DatabaseHelper(getContext(), 10);
            return null;
        }
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        try {
            SQLiteDatabase writableDatabase = this.mOpenHelper.getWritableDatabase();
            Log.d(TAG, "sURI macher match");
            if (sURIMatcher.match(uri) == 0) {
                sQLiteQueryBuilder.setTables(DB_TABLE_WHITELIST);
                Log.d(TAG, "allowlist");
                Cursor query = sQLiteQueryBuilder.query(writableDatabase, strArr, str, strArr2, null, null, str2);
                if (query == null) {
                    Log.d(TAG, "query failed in downloads database");
                }
                return query;
            }
            Log.d(TAG, "querying unknown URI: " + uri);
            return null;
        } catch (SQLiteException e) {
            Log.e(TAG, "Exception: " + e);
            return null;
        }
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, int i) {
            super(context, SemMobileWipsProvider.DB_NAME, (SQLiteDatabase.CursorFactory) null, i);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.v(SemMobileWipsProvider.TAG, "populating new database");
            createTable(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i2 == 10) {
                dropTable(sQLiteDatabase);
            }
            if (i < 9) {
                execSql(sQLiteDatabase, "DROP TABLE IF EXISTS MobileWIPS");
                dropTable(sQLiteDatabase);
                createTable(sQLiteDatabase);
            }
            Log.i(SemMobileWipsProvider.TAG, "Upgrading downloads database from version " + i + " to " + i2 + ", which will destroy all old data");
        }

        private void addTable(SQLiteDatabase sQLiteDatabase, int i) {
            execSql(sQLiteDatabase, SemMobileWipsProvider.mDBCreationSQL[i]);
        }

        private void delTable(SQLiteDatabase sQLiteDatabase, int i) {
            execSql(sQLiteDatabase, SemMobileWipsProvider.mDBDeleteSQL[i]);
        }

        private void execSql(SQLiteDatabase sQLiteDatabase, String str) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL(str);
                sQLiteDatabase.setTransactionSuccessful();
            } catch (IllegalStateException e) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't exec " + e);
            } finally {
                sQLiteDatabase.endTransaction();
            }
        }

        private void createTable(SQLiteDatabase sQLiteDatabase) {
            Log.d(SemMobileWipsProvider.TAG, "createTable");
            try {
                addTable(sQLiteDatabase, 0);
                addTable(sQLiteDatabase, 1);
                addTable(sQLiteDatabase, 2);
            } catch (SQLException e) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't create table in downloads database " + e);
            }
        }

        private void dropTable(SQLiteDatabase sQLiteDatabase) {
            Log.d(SemMobileWipsProvider.TAG, "dropTable");
            try {
                delTable(sQLiteDatabase, 0);
                delTable(sQLiteDatabase, 1);
                delTable(sQLiteDatabase, 2);
            } catch (SQLException e) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't drop table in downloads database " + e);
            }
        }

        public void dropWhitelistTable(SQLiteDatabase sQLiteDatabase) {
            Log.d(SemMobileWipsProvider.TAG, "drop Allow List Table");
            try {
                delTable(sQLiteDatabase, 0);
            } catch (SQLException e) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't drop table in downloads database " + e);
            }
        }
    }
}
