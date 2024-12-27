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
import android.os.Debug;
import android.util.Log;

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
            Log.v(TAG, "populating new database");
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
            Log.v(
                    TAG,
                    "Upgrading downloads database from version "
                            + oldV
                            + " to "
                            + newV
                            + ", which will destroy all old data");
            dropTable(db);
            createTable(db);
        }

        private void createTable(SQLiteDatabase db) {
            Log.v(TAG, "createTable");
            try {
                db.execSQL(
                        "CREATE TABLE WifiHistory(conrol_id INTEGER PRIMARY KEY"
                            + " AUTOINCREMENT,package_name TEXT, time_stamp LONG, enable_number"
                            + " INTEGER, disable_number INTEGER, first_control LONG, last_control"
                            + " INTEGER) ");
            } catch (SQLException e) {
                Log.e(TAG, "couldn't create table in downloads database");
            }
        }

        private void dropTable(SQLiteDatabase db) {
            Log.v(TAG, "dropTable");
            try {
                db.execSQL("DROP TABLE IF EXISTS WifiHistory");
            } catch (SQLException e) {
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
            filteredValues.put(
                    "time_stamp", Long.valueOf(getLong("time_stamp", null, values, now)));
            int lastControl = getInteger(LAST_CONTROL, null, values, 0);
            filteredValues.put(LAST_CONTROL, Integer.valueOf(lastControl));
            filteredValues.put(
                    FIRST_CONTROL, Long.valueOf(getLong(FIRST_CONTROL, cursor, values, now)));
            filteredValues.put(
                    ENABLE_NUMBER,
                    Integer.valueOf(
                            getInteger(ENABLE_NUMBER, cursor, values, 0)
                                    + (lastControl == 1 ? 1 : 0)));
            filteredValues.put(
                    DISABLE_NUMBER,
                    Integer.valueOf(
                            getInteger(DISABLE_NUMBER, cursor, values, 0)
                                    + (lastControl == 0 ? 1 : 0)));
            return filteredValues;
        }

        private int getInteger(String key, Cursor cursor, ContentValues values, int defaultValue) {
            Integer retValue;
            int index;
            if (cursor == null || (index = cursor.getColumnIndex(key)) < 0) {
                return (values == null
                                || !values.containsKey(key)
                                || (retValue = values.getAsInteger(key)) == null)
                        ? defaultValue
                        : retValue.intValue();
            }
            return cursor.getInt(index);
        }

        private long getLong(String key, Cursor cursor, ContentValues values, long defaultValue) {
            Long retValue;
            int index;
            if (cursor == null || (index = cursor.getColumnIndex(key)) < 0) {
                return (values == null
                                || !values.containsKey(key)
                                || (retValue = values.getAsLong(key)) == null)
                        ? defaultValue
                        : retValue.longValue();
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
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        if (DBG) {
            Log.v(TAG, "query uri " + uri.toSafeString());
        }
        Log.v(
                TAG,
                "query table "
                        + this.mDbHelper.getTableName()
                        + " where "
                        + selection
                        + " arg length "
                        + (selectionArgs != null ? Integer.valueOf(selectionArgs.length) : "null")
                        + " projection length "
                        + (projection != null ? Integer.valueOf(projection.length) : "null")
                        + " sortOrder "
                        + sortOrder);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(this.mDbHelper.getTableName());
        qb.setStrict(true);
        qb.setProjectionMap(this.mDbHelper.getProjectionMap());
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(false);
                Cursor cursor =
                        qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor;
            } catch (SQLiteException e) {
                Log.e(TAG, "Failed to query - " + e);
                return null;
            }
        } catch (Throwable th) {
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        if (DBG) {
            Log.v(TAG, "insert uri " + uri.toSafeString());
        }
        Uri insertedUri = updateIfExist(uri, values);
        if (insertedUri == null) {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                long rowId =
                        db.insert(
                                this.mDbHelper.getTableName(),
                                null,
                                this.mDbHelper.checkAndGetContentValues(null, values));
                if (rowId < 0) {
                    Log.e(TAG, "Failed to insert - " + rowId);
                    return null;
                }
                Log.d(TAG, "Inserted at " + rowId);
                insertedUri = getContentUri(rowId);
            } catch (SQLiteException e) {
                Log.e(TAG, "Failed to insert - " + e);
            }
        }
        this.mContext.getContentResolver().notifyChange(insertedUri, null);
        return insertedUri;
    }

    private Uri getContentUri(long index) {
        return Uri.parse("content://com.samsung.server.wifi/control/" + index);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a4 A[Catch: SQLiteException -> 0x00b3, TRY_ENTER, TRY_LEAVE, TryCatch #1 {SQLiteException -> 0x00b3, blocks: (B:8:0x004c, B:12:0x00a4, B:24:0x00b2, B:29:0x00af, B:31:0x0060, B:33:0x0066, B:10:0x0082, B:26:0x00aa), top: B:7:0x004c, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e1 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.net.Uri updateIfExist(android.net.Uri r14, android.content.ContentValues r15) {
        /*
            r13 = this;
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r0 = r13.mDbHelper
            java.lang.String r0 = r0.getUniqueColumnName()
            boolean r1 = r15.containsKey(r0)
            r2 = 0
            java.lang.String r3 = "WifiControlHistoryProvider"
            if (r1 != 0) goto L15
            java.lang.String r1 = "unique value is not in the content"
            android.util.Log.e(r3, r1)
            return r2
        L15:
            r1 = 0
            r4 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "=?"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.Object r6 = r15.get(r0)
            java.lang.String r12 = java.lang.String.valueOf(r6)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "try to find "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r5)
            java.lang.StringBuilder r6 = r6.append(r12)
            java.lang.String r6 = r6.toString()
            android.util.Log.v(r3, r6)
            java.lang.String[] r10 = new java.lang.String[]{r12}     // Catch: android.database.sqlite.SQLiteException -> Lb3
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r6 = r13.mDbHelper     // Catch: android.database.sqlite.SQLiteException -> Lb3
            java.lang.String r11 = r6.getDefaultSortOrder()     // Catch: android.database.sqlite.SQLiteException -> Lb3
            r8 = 0
            r6 = r13
            r7 = r14
            r9 = r5
            android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11)     // Catch: android.database.sqlite.SQLiteException -> Lb3
            if (r6 == 0) goto L82
            int r7 = r6.getCount()     // Catch: java.lang.Throwable -> L80
            if (r7 <= 0) goto L82
            r6.moveToFirst()     // Catch: java.lang.Throwable -> L80
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r7 = r13.mDbHelper     // Catch: java.lang.Throwable -> L80
            android.content.ContentValues r7 = r7.checkAndGetContentValues(r6, r15)     // Catch: java.lang.Throwable -> L80
            r4 = r7
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r7 = r13.mDbHelper     // Catch: java.lang.Throwable -> L80
            java.lang.String r7 = r7.getIdColumnName()     // Catch: java.lang.Throwable -> L80
            int r7 = r6.getColumnIndex(r7)     // Catch: java.lang.Throwable -> L80
            int r7 = r6.getInt(r7)     // Catch: java.lang.Throwable -> L80
            r1 = r7
            goto La2
        L80:
            r7 = move-exception
            goto La8
        L82:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L80
            r7.<init>()     // Catch: java.lang.Throwable -> L80
            java.lang.StringBuilder r7 = r7.append(r12)     // Catch: java.lang.Throwable -> L80
            java.lang.String r8 = " is not existed in "
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch: java.lang.Throwable -> L80
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r8 = r13.mDbHelper     // Catch: java.lang.Throwable -> L80
            java.lang.String r8 = r8.getTableName()     // Catch: java.lang.Throwable -> L80
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch: java.lang.Throwable -> L80
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L80
            android.util.Log.v(r3, r7)     // Catch: java.lang.Throwable -> L80
        La2:
            if (r6 == 0) goto La7
            r6.close()     // Catch: android.database.sqlite.SQLiteException -> Lb3
        La7:
            goto Lca
        La8:
            if (r6 == 0) goto Lb2
            r6.close()     // Catch: java.lang.Throwable -> Lae
            goto Lb2
        Lae:
            r8 = move-exception
            r7.addSuppressed(r8)     // Catch: android.database.sqlite.SQLiteException -> Lb3
        Lb2:
            throw r7     // Catch: android.database.sqlite.SQLiteException -> Lb3
        Lb3:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Failed to update - "
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r6)
            java.lang.String r7 = r7.toString()
            android.util.Log.e(r3, r7)
        Lca:
            if (r4 == 0) goto Le1
            java.lang.String[] r2 = new java.lang.String[]{r12}
            int r2 = r13.update(r14, r4, r5, r2)
            if (r2 != 0) goto Ldb
            java.lang.String r2 = "Failed to update"
            android.util.Log.e(r3, r2)
        Ldb:
            long r2 = (long) r1
            android.net.Uri r2 = r13.getContentUri(r2)
            return r2
        Le1:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.wifi.db.WifiControlHistoryProvider.updateIfExist(android.net.Uri,"
                    + " android.content.ContentValues):android.net.Uri");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DBG) {
            Log.v(TAG, "update uri " + uri.toSafeString());
        }
        int count = 0;
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                count = db.update(this.mDbHelper.getTableName(), values, selection, selectionArgs);
                Log.v(TAG, "updated " + count + " rows");
                return count;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to update - " + e);
                return count;
            }
        } catch (Throwable th) {
            return count;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DBG) {
            Log.v(TAG, "delete uri " + uri.toSafeString());
        }
        int count = 0;
        try {
            try {
                SQLiteDatabase db = this.mDbHelper.getDatabase(true);
                count = db.delete(this.mDbHelper.getTableName(), selection, selectionArgs);
                Log.v(TAG, "deleted " + count + " rows");
                return count;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to delete - " + e);
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
