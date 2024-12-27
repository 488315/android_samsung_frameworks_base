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
    private SQLiteOpenHelper mOpenHelper;
    public static final Uri CONTENT_URI_WHITELIST =
            Uri.parse("content://com.samsung.server.wifi.mwips/whitelist");
    public static final Uri CONTENT_URI_IELIST =
            Uri.parse("content://com.samsung.server.wifi.mwips/ielist");
    public static final Uri CONTENT_URI_DUMP =
            Uri.parse("content://com.samsung.server.wifi.mwips/dump");
    private static final UriMatcher sURIMatcher = new UriMatcher(-1);
    private static final String[] mDBCreationSQL = {
        "CREATE TABLE IF NOT EXISTS MobileWIPSWHITE(history_id INTEGER PRIMARY KEY"
            + " AUTOINCREMENT,mac_addr TEXT, exception_type Integer DEFAULT 0, ssid_name TEXT ) ",
        "CREATE TABLE IF NOT EXISTS MobileWIPSIE(history_id INTEGER PRIMARY KEY"
            + " AUTOINCREMENT,mac_addr TEXT, frequency INTEGER, time_stamp LONG, seen_time LONG,"
            + " ies varbinary, time_saved LONG, beacon_tsf LONG, beacon_seen LONG ) ",
        "CREATE TABLE IF NOT EXISTS MobileWIPSDUMP(history_id INTEGER PRIMARY KEY"
            + " AUTOINCREMENT,time_stamp LONG, attack_type Integer, reason TEXT) "
    };
    private static final String[] mDBDeleteSQL = {
        "DROP TABLE IF EXISTS MobileWIPSWHITE",
        "DROP TABLE IF EXISTS MobileWIPSIE",
        "DROP TABLE IF EXISTS MobileWIPSDUMP"
    };

    static {
        sURIMatcher.addURI(AUTHORITY, "whitelist", 0);
        sURIMatcher.addURI(AUTHORITY, "ielist", 1);
        sURIMatcher.addURI(AUTHORITY, "dump", 2);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        int match = sURIMatcher.match(uri);
        if (match == 0) {
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
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        if (selection != null && selection.equals("drop")) {
            Log.d(TAG, "drop allowlist table");
            this.mOpenHelper = new DatabaseHelper(getContext(), 10);
            return null;
        }
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        try {
            SQLiteDatabase db = this.mOpenHelper.getWritableDatabase();
            Log.d(TAG, "sURI macher match");
            int match = sURIMatcher.match(uri);
            switch (match) {
                case 0:
                    qb.setTables(DB_TABLE_WHITELIST);
                    Log.d(TAG, "allowlist");
                    Cursor ret =
                            qb.query(
                                    db,
                                    projection,
                                    selection,
                                    selectionArgs,
                                    null,
                                    null,
                                    sortOrder);
                    if (ret == null) {
                        Log.d(TAG, "query failed in downloads database");
                        break;
                    }
                    break;
                default:
                    Log.d(TAG, "querying unknown URI: " + uri);
                    break;
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "Exception: " + e);
            return null;
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, int version) {
            super(
                    context,
                    SemMobileWipsProvider.DB_NAME,
                    (SQLiteDatabase.CursorFactory) null,
                    version);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            Log.v(SemMobileWipsProvider.TAG, "populating new database");
            createTable(db);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
            if (newV == 10) {
                dropTable(db);
            }
            if (oldV < 9) {
                execSql(db, "DROP TABLE IF EXISTS MobileWIPS");
                dropTable(db);
                createTable(db);
            }
            Log.i(
                    SemMobileWipsProvider.TAG,
                    "Upgrading downloads database from version "
                            + oldV
                            + " to "
                            + newV
                            + ", which will destroy all old data");
        }

        private void addTable(SQLiteDatabase db, int index) {
            String query = SemMobileWipsProvider.mDBCreationSQL[index];
            execSql(db, query);
        }

        private void delTable(SQLiteDatabase db, int index) {
            String query = SemMobileWipsProvider.mDBDeleteSQL[index];
            execSql(db, query);
        }

        private void execSql(SQLiteDatabase db, String query) {
            try {
                try {
                    db.beginTransaction();
                    db.execSQL(query);
                    db.setTransactionSuccessful();
                } catch (IllegalStateException e) {
                    Log.e(SemMobileWipsProvider.TAG, "couldn't exec " + e);
                }
            } finally {
                db.endTransaction();
            }
        }

        private void createTable(SQLiteDatabase db) {
            Log.d(SemMobileWipsProvider.TAG, "createTable");
            try {
                addTable(db, 0);
                addTable(db, 1);
                addTable(db, 2);
            } catch (SQLException ex) {
                Log.e(
                        SemMobileWipsProvider.TAG,
                        "couldn't create table in downloads database " + ex);
            }
        }

        private void dropTable(SQLiteDatabase db) {
            Log.d(SemMobileWipsProvider.TAG, "dropTable");
            try {
                delTable(db, 0);
                delTable(db, 1);
                delTable(db, 2);
            } catch (SQLException ex) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't drop table in downloads database " + ex);
            }
        }

        public void dropWhitelistTable(SQLiteDatabase db) {
            Log.d(SemMobileWipsProvider.TAG, "drop Allow List Table");
            try {
                delTable(db, 0);
            } catch (SQLException ex) {
                Log.e(SemMobileWipsProvider.TAG, "couldn't drop table in downloads database " + ex);
            }
        }
    }
}
