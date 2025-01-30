package com.samsung.android.wifi.p048db;

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
import android.p009os.Debug;
import android.util.Log;
import com.samsung.android.ims.options.SemCapabilities;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class WifiControlHistoryProvider extends ContentProvider {
    public static final String AUTHORITY = "com.samsung.server.wifi";
    private static final int CODE = 1;
    private static final boolean DBG = Debug.semIsProductDev();
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
        public void onCreate(SQLiteDatabase db) {
            Log.m100v(TAG, "populating new database");
            createTable(db);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
            if (oldV == 0) {
                if (newV == 1) {
                    return;
                } else {
                    oldV = 1;
                }
            }
            Log.m100v(TAG, "Upgrading downloads database from version " + oldV + " to " + newV + ", which will destroy all old data");
            dropTable(db);
            createTable(db);
        }

        private void createTable(SQLiteDatabase db) {
            Log.m100v(TAG, "createTable");
            try {
                db.execSQL("CREATE TABLE WifiHistory(conrol_id INTEGER PRIMARY KEY AUTOINCREMENT,package_name TEXT, time_stamp LONG, enable_number INTEGER, disable_number INTEGER, first_control LONG, last_control INTEGER) ");
            } catch (SQLException e) {
                Log.m96e(TAG, "couldn't create table in downloads database");
            }
        }

        private void dropTable(SQLiteDatabase db) {
            Log.m100v(TAG, "dropTable");
            try {
                db.execSQL("DROP TABLE IF EXISTS WifiHistory");
            } catch (SQLException e) {
                Log.m96e(TAG, "couldn't drop table in downloads database");
            }
        }

        String getType() {
            return "vnd.android.cursor.dir/vnd.samsung.wifi.control";
        }

        String getTableName() {
            return DB_TABLE;
        }

        Map<String, String> getProjectionMap() {
            Map<String, String> projectionMap = new HashMap<>();
            projectionMap.put(CONTROL_ID, CONTROL_ID);
            projectionMap.put("package_name", "package_name");
            projectionMap.put("time_stamp", "time_stamp");
            projectionMap.put(ENABLE_NUMBER, ENABLE_NUMBER);
            projectionMap.put(DISABLE_NUMBER, DISABLE_NUMBER);
            projectionMap.put(LAST_CONTROL, LAST_CONTROL);
            projectionMap.put(FIRST_CONTROL, FIRST_CONTROL);
            return projectionMap;
        }

        String getDefaultSortOrder() {
            return "time_stamp ASC";
        }

        ContentValues checkAndGetContentValues(Cursor cursor, ContentValues values) {
            ContentValues filteredValues = new ContentValues();
            filteredValues.put("package_name", values.getAsString("package_name"));
            long now = System.currentTimeMillis();
            filteredValues.put("time_stamp", Long.valueOf(getLong("time_stamp", null, values, now)));
            int lastControl = getInteger(LAST_CONTROL, null, values, 0);
            filteredValues.put(LAST_CONTROL, Integer.valueOf(lastControl));
            filteredValues.put(FIRST_CONTROL, Long.valueOf(getLong(FIRST_CONTROL, cursor, values, now)));
            filteredValues.put(ENABLE_NUMBER, Integer.valueOf(getInteger(ENABLE_NUMBER, cursor, values, 0) + (lastControl == 1 ? 1 : 0)));
            filteredValues.put(DISABLE_NUMBER, Integer.valueOf(getInteger(DISABLE_NUMBER, cursor, values, 0) + (lastControl == 0 ? 1 : 0)));
            return filteredValues;
        }

        private int getInteger(String key, Cursor cursor, ContentValues values, int defaultValue) {
            Integer retValue;
            int index;
            if (cursor == null || (index = cursor.getColumnIndex(key)) < 0) {
                return (values == null || !values.containsKey(key) || (retValue = values.getAsInteger(key)) == null) ? defaultValue : retValue.intValue();
            }
            return cursor.getInt(index);
        }

        private long getLong(String key, Cursor cursor, ContentValues values, long defaultValue) {
            Long retValue;
            int index;
            if (cursor == null || (index = cursor.getColumnIndex(key)) < 0) {
                return (values == null || !values.containsKey(key) || (retValue = values.getAsLong(key)) == null) ? defaultValue : retValue.longValue();
            }
            return cursor.getLong(index);
        }

        String getUniqueColumnName() {
            return "package_name";
        }

        String getIdColumnName() {
            return CONTROL_ID;
        }

        SQLiteDatabase getDatabase(boolean writable) {
            if (writable) {
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DBG) {
            Log.m100v(TAG, "query uri " + uri.toSafeString());
        }
        StringBuilder append = new StringBuilder().append("query table ").append(this.mDbHelper.getTableName()).append(" where ").append(selection).append(" arg length ");
        Object obj = SemCapabilities.FEATURE_TAG_NULL;
        StringBuilder append2 = append.append(selectionArgs != null ? Integer.valueOf(selectionArgs.length) : SemCapabilities.FEATURE_TAG_NULL).append(" projection length ");
        if (projection != null) {
            obj = Integer.valueOf(projection.length);
        }
        Log.m100v(TAG, append2.append(obj).append(" sortOrder ").append(sortOrder).toString());
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(this.mDbHelper.getTableName());
        qb.setStrict(true);
        qb.setProjectionMap(this.mDbHelper.getProjectionMap());
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(false);
                Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor;
            } catch (SQLiteException e) {
                Log.m96e(TAG, "Failed to query - " + e);
                return null;
            }
        } catch (Throwable th) {
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        if (DBG) {
            Log.m100v(TAG, "insert uri " + uri.toSafeString());
        }
        Uri insertedUri = updateIfExist(uri, values);
        if (insertedUri == null) {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                long rowId = db.insert(this.mDbHelper.getTableName(), null, this.mDbHelper.checkAndGetContentValues(null, values));
                if (rowId < 0) {
                    Log.m96e(TAG, "Failed to insert - " + rowId);
                    return null;
                }
                Log.m94d(TAG, "Inserted at " + rowId);
                insertedUri = getContentUri(rowId);
            } catch (SQLiteException e) {
                Log.m96e(TAG, "Failed to insert - " + e);
            }
        }
        this.mContext.getContentResolver().notifyChange(insertedUri, null);
        return insertedUri;
    }

    private Uri getContentUri(long index) {
        return Uri.parse("content://com.samsung.server.wifi/control/" + index);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a4 A[Catch: SQLiteException -> 0x00b3, TRY_ENTER, TRY_LEAVE, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:8:0x004d, B:12:0x00a4, B:24:0x00b2, B:29:0x00af, B:31:0x0060, B:33:0x0066, B:10:0x0082, B:26:0x00aa), top: B:7:0x004d, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e1 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Uri updateIfExist(Uri uri, ContentValues values) {
        Cursor cursor;
        String uniqueColName = this.mDbHelper.getUniqueColumnName();
        if (!values.containsKey(uniqueColName)) {
            Log.m96e(TAG, "unique value is not in the content");
            return null;
        }
        int id = 0;
        ContentValues updateValues = null;
        String selectionString = uniqueColName + "=?";
        String uniqueValue = String.valueOf(values.get(uniqueColName));
        Log.m100v(TAG, "try to find " + selectionString + uniqueValue);
        try {
            cursor = query(uri, null, selectionString, new String[]{uniqueValue}, this.mDbHelper.getDefaultSortOrder());
        } catch (SQLiteException e) {
            Log.m96e(TAG, "Failed to update - " + e);
        }
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    updateValues = this.mDbHelper.checkAndGetContentValues(cursor, values);
                    id = cursor.getInt(cursor.getColumnIndex(this.mDbHelper.getIdColumnName()));
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (updateValues != null) {
                        return null;
                    }
                    if (update(uri, updateValues, selectionString, new String[]{uniqueValue}) == 0) {
                        Log.m96e(TAG, "Failed to update");
                    }
                    return getContentUri(id);
                }
            } finally {
            }
        }
        Log.m100v(TAG, uniqueValue + " is not existed in " + this.mDbHelper.getTableName());
        if (cursor != null) {
        }
        if (updateValues != null) {
        }
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DBG) {
            Log.m100v(TAG, "update uri " + uri.toSafeString());
        }
        int count = 0;
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                count = db.update(this.mDbHelper.getTableName(), values, selection, selectionArgs);
                Log.m100v(TAG, "updated " + count + " rows");
                return count;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.m96e(TAG, "Failed to update - " + e);
                return count;
            }
        } catch (Throwable th) {
            return count;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DBG) {
            Log.m100v(TAG, "delete uri " + uri.toSafeString());
        }
        int count = 0;
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                count = db.delete(this.mDbHelper.getTableName(), selection, selectionArgs);
                Log.m100v(TAG, "deleted " + count + " rows");
                return count;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.m96e(TAG, "Failed to delete - " + e);
                return count;
            }
        } catch (Throwable th) {
            return count;
        }
    }

    public static Uri getContentUri() {
        return Uri.parse("content://com.samsung.server.wifi/control");
    }
}
