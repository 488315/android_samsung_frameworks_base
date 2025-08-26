package com.samsung.android.wifi.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteReadOnlyDatabaseException;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class WifiControlHistoryProvider extends ContentProvider {
    public static final String AUTHORITY = "com.samsung.server.wifi";
    private static final int CODE = 1;
    private static final boolean DBG = !SystemProperties.getBoolean("ro.product_ship", true);
    private static final String PATH = "control";
    private static final String TAG = "WifiControlHistoryProvider";
    private Context mContext;
    private DatabaseHelper mDbHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(-1);

    public static final class DatabaseHelper extends SQLiteOpenHelper {
        public static final String CONTROL_ID = "conrol_id";
        private static final String DB_NAME = "WifiHistory.db";
        private static final String DB_TABLE = "WifiHistory";
        private static final int DB_VERSION = 1;
        private static final int DB_VERSION_NOP_UPGRADE_FROM = 0;
        private static final int DB_VERSION_NOP_UPGRADE_TO = 1;
        public static final String DISABLE_NUMBER = "disable_number";
        public static final int DISABLE_WIFI = 0;
        public static final String ENABLE_NUMBER = "enable_number";
        public static final int ENABLE_WIFI = 1;
        public static final String FIRST_CONTROL = "first_control";
        public static final String LAST_CONTROL = "last_control";
        public static final String PACKAGE_NAME = "package_name";
        private static final String TAG = "DatabaseHelper";
        public static final String TIME_STAMP = "time_stamp";

        DatabaseHelper(Context context) {
            super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.v(TAG, "populating new database");
            createTable(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i == 0) {
                i = 1;
                if (i2 == 1) {
                    return;
                }
            }
            Log.v(TAG, "Upgrading downloads database from version " + i + " to " + i2 + ", which will destroy all old data");
            dropTable(sQLiteDatabase);
            createTable(sQLiteDatabase);
        }

        private void createTable(SQLiteDatabase sQLiteDatabase) {
            Log.v(TAG, "createTable");
            try {
                sQLiteDatabase.execSQL("CREATE TABLE WifiHistory(conrol_id INTEGER PRIMARY KEY AUTOINCREMENT,package_name TEXT, time_stamp LONG, enable_number INTEGER, disable_number INTEGER, first_control LONG, last_control INTEGER) ");
            } catch (SQLException unused) {
                Log.e(TAG, "couldn't create table in downloads database");
            }
        }

        private void dropTable(SQLiteDatabase sQLiteDatabase) {
            Log.v(TAG, "dropTable");
            try {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS WifiHistory");
            } catch (SQLException unused) {
                Log.e(TAG, "couldn't drop table in downloads database");
            }
        }

        String getType() {
            return "vnd.android.cursor.dir/vnd.samsung.wifi.control";
        }

        String getTableName() {
            return DB_TABLE;
        }

        Map<String, String> getProjectionMap() {
            HashMap map = new HashMap();
            map.put(CONTROL_ID, CONTROL_ID);
            map.put("package_name", "package_name");
            map.put("time_stamp", "time_stamp");
            map.put(ENABLE_NUMBER, ENABLE_NUMBER);
            map.put(DISABLE_NUMBER, DISABLE_NUMBER);
            map.put(LAST_CONTROL, LAST_CONTROL);
            map.put(FIRST_CONTROL, FIRST_CONTROL);
            return map;
        }

        String getDefaultSortOrder() {
            return "time_stamp ASC";
        }

        ContentValues checkAndGetContentValues(Cursor cursor, ContentValues contentValues) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("package_name", contentValues.getAsString("package_name"));
            long jCurrentTimeMillis = System.currentTimeMillis();
            contentValues2.put("time_stamp", Long.valueOf(getLong("time_stamp", null, contentValues, jCurrentTimeMillis)));
            int integer = getInteger(LAST_CONTROL, null, contentValues, 0);
            contentValues2.put(LAST_CONTROL, Integer.valueOf(integer));
            contentValues2.put(FIRST_CONTROL, Long.valueOf(getLong(FIRST_CONTROL, cursor, contentValues, jCurrentTimeMillis)));
            contentValues2.put(ENABLE_NUMBER, Integer.valueOf(getInteger(ENABLE_NUMBER, cursor, contentValues, 0) + (integer == 1 ? 1 : 0)));
            contentValues2.put(DISABLE_NUMBER, Integer.valueOf(getInteger(DISABLE_NUMBER, cursor, contentValues, 0) + (integer == 0 ? 1 : 0)));
            return contentValues2;
        }

        private int getInteger(String str, Cursor cursor, ContentValues contentValues, int i) {
            Integer asInteger;
            int columnIndex;
            if (cursor == null || (columnIndex = cursor.getColumnIndex(str)) < 0) {
                return (contentValues == null || !contentValues.containsKey(str) || (asInteger = contentValues.getAsInteger(str)) == null) ? i : asInteger.intValue();
            }
            return cursor.getInt(columnIndex);
        }

        private long getLong(String str, Cursor cursor, ContentValues contentValues, long j) {
            Long asLong;
            int columnIndex;
            if (cursor == null || (columnIndex = cursor.getColumnIndex(str)) < 0) {
                return (contentValues == null || !contentValues.containsKey(str) || (asLong = contentValues.getAsLong(str)) == null) ? j : asLong.longValue();
            }
            return cursor.getLong(columnIndex);
        }

        String getUniqueColumnName() {
            return "package_name";
        }

        String getIdColumnName() {
            return CONTROL_ID;
        }

        SQLiteDatabase getDatabase(boolean z) {
            if (z) {
                return getWritableDatabase();
            }
            return getReadableDatabase();
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mContext = getContext();
        this.mDbHelper = new DatabaseHelper(this.mContext);
        this.mUriMatcher.addURI(AUTHORITY, "control", 1);
        return true;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        return this.mDbHelper.getType();
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (DBG) {
            Log.v(TAG, "query uri " + uri.toSafeString());
        }
        StringBuilder sb = new StringBuilder("query table ");
        sb.append(this.mDbHelper.getTableName());
        sb.append(" where ");
        sb.append(str);
        sb.append(" arg length ");
        Object objValueOf = PerfettoProtoLogImpl.NULL_STRING;
        sb.append(strArr2 != null ? Integer.valueOf(strArr2.length) : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(" projection length ");
        if (strArr != null) {
            objValueOf = Integer.valueOf(strArr.length);
        }
        sb.append(objValueOf);
        sb.append(" sortOrder ");
        sb.append(str2);
        Log.v(TAG, sb.toString());
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(this.mDbHelper.getTableName());
        sQLiteQueryBuilder.setStrict(true);
        sQLiteQueryBuilder.setProjectionMap(this.mDbHelper.getProjectionMap());
        try {
            try {
                return sQLiteQueryBuilder.query(this.mDbHelper.getDatabase(false), strArr, str, strArr2, null, null, str2);
            } catch (SQLiteException e) {
                Log.e(TAG, "Failed to query - " + e);
                return null;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) throws Throwable {
        if (DBG) {
            Log.v(TAG, "insert uri " + uri.toSafeString());
        }
        Uri uriUpdateIfExist = updateIfExist(uri, contentValues);
        if (uriUpdateIfExist == null) {
            try {
                long jInsert = this.mDbHelper.getDatabase(true).insert(this.mDbHelper.getTableName(), null, this.mDbHelper.checkAndGetContentValues(null, contentValues));
                if (jInsert >= 0) {
                    Log.d(TAG, "Inserted at " + jInsert);
                    uriUpdateIfExist = getContentUri(jInsert);
                } else {
                    Log.e(TAG, "Failed to insert - " + jInsert);
                    return null;
                }
            } catch (SQLiteException e) {
                Log.e(TAG, "Failed to insert - " + e);
            }
        }
        this.mContext.getContentResolver().notifyChange(uriUpdateIfExist, null);
        return uriUpdateIfExist;
    }

    private Uri getContentUri(long j) {
        return Uri.parse("content://com.samsung.server.wifi/control/" + j);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007d A[Catch: all -> 0x0079, TRY_ENTER, TRY_LEAVE, TryCatch #3 {all -> 0x0079, blocks: (B:11:0x0058, B:13:0x005e, B:20:0x007d), top: B:53:0x0058 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00df A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Uri updateIfExist(Uri uri, ContentValues contentValues) throws Throwable {
        WifiControlHistoryProvider wifiControlHistoryProvider;
        Uri uri2;
        SQLiteException sQLiteException;
        ContentValues contentValues2;
        Cursor cursorQuery;
        Throwable th;
        String uniqueColumnName = this.mDbHelper.getUniqueColumnName();
        if (!contentValues.containsKey(uniqueColumnName)) {
            Log.e(TAG, "unique value is not in the content");
            return null;
        }
        String str = uniqueColumnName + "=?";
        String strValueOf = String.valueOf(contentValues.get(uniqueColumnName));
        Log.v(TAG, "try to find " + str + strValueOf);
        int i = 0;
        try {
            wifiControlHistoryProvider = this;
            uri2 = uri;
            try {
                cursorQuery = wifiControlHistoryProvider.query(uri2, null, str, new String[]{strValueOf}, this.mDbHelper.getDefaultSortOrder());
            } catch (SQLiteException e) {
                e = e;
                sQLiteException = e;
                contentValues2 = null;
                wifiControlHistoryProvider = wifiControlHistoryProvider;
                Log.e(TAG, "Failed to update - " + sQLiteException);
                if (contentValues2 == null) {
                }
            }
        } catch (SQLiteException e2) {
            e = e2;
            wifiControlHistoryProvider = this;
            uri2 = uri;
        }
        try {
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        cursorQuery.moveToFirst();
                        ContentValues contentValuesCheckAndGetContentValues = wifiControlHistoryProvider.mDbHelper.checkAndGetContentValues(cursorQuery, contentValues);
                        try {
                            i = cursorQuery.getInt(cursorQuery.getColumnIndex(wifiControlHistoryProvider.mDbHelper.getIdColumnName()));
                            contentValues2 = contentValuesCheckAndGetContentValues;
                        } catch (Throwable th2) {
                            th = th2;
                            uri = contentValuesCheckAndGetContentValues;
                            if (cursorQuery == null) {
                                throw th;
                            }
                            try {
                                cursorQuery.close();
                                throw th;
                            } catch (Throwable th3) {
                                th.addSuppressed(th3);
                                throw th;
                            }
                        }
                    } else {
                        Log.v(TAG, strValueOf + " is not existed in " + wifiControlHistoryProvider.mDbHelper.getTableName());
                        contentValues2 = null;
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    uri = null;
                }
            }
        } catch (SQLiteException e3) {
            sQLiteException = e3;
            wifiControlHistoryProvider = wifiControlHistoryProvider;
            contentValues2 = uri;
            Log.e(TAG, "Failed to update - " + sQLiteException);
            if (contentValues2 == null) {
            }
        }
        if (contentValues2 == null) {
            return null;
        }
        if (wifiControlHistoryProvider.update(uri2, contentValues2, str, new String[]{strValueOf}) == 0) {
            Log.e(TAG, "Failed to update");
        }
        return wifiControlHistoryProvider.getContentUri(i);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        if (DBG) {
            Log.v(TAG, "update uri " + uri.toSafeString());
        }
        int iUpdate = 0;
        try {
            try {
                iUpdate = this.mDbHelper.getDatabase(true).update(this.mDbHelper.getTableName(), contentValues, str, strArr);
                Log.v(TAG, "updated " + iUpdate + " rows");
                return iUpdate;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to update - " + e);
                return iUpdate;
            }
        } catch (Throwable unused) {
            return iUpdate;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        if (DBG) {
            Log.v(TAG, "delete uri " + uri.toSafeString());
        }
        int iDelete = 0;
        try {
            try {
                iDelete = this.mDbHelper.getDatabase(true).delete(this.mDbHelper.getTableName(), str, strArr);
                Log.v(TAG, "deleted " + iDelete + " rows");
                return iDelete;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to delete - " + e);
                return iDelete;
            }
        } catch (Throwable unused) {
            return iDelete;
        }
    }

    public static Uri getContentUri() {
        return Uri.parse("content://com.samsung.server.wifi/control");
    }
}
