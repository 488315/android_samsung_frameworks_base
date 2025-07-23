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
            HashMap hashMap = new HashMap();
            hashMap.put(CONTROL_ID, CONTROL_ID);
            hashMap.put("package_name", "package_name");
            hashMap.put("time_stamp", "time_stamp");
            hashMap.put(ENABLE_NUMBER, ENABLE_NUMBER);
            hashMap.put(DISABLE_NUMBER, DISABLE_NUMBER);
            hashMap.put(LAST_CONTROL, LAST_CONTROL);
            hashMap.put(FIRST_CONTROL, FIRST_CONTROL);
            return hashMap;
        }

        String getDefaultSortOrder() {
            return "time_stamp ASC";
        }

        ContentValues checkAndGetContentValues(Cursor cursor, ContentValues contentValues) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("package_name", contentValues.getAsString("package_name"));
            long currentTimeMillis = System.currentTimeMillis();
            contentValues2.put("time_stamp", Long.valueOf(getLong("time_stamp", null, contentValues, currentTimeMillis)));
            int integer = getInteger(LAST_CONTROL, null, contentValues, 0);
            contentValues2.put(LAST_CONTROL, Integer.valueOf(integer));
            contentValues2.put(FIRST_CONTROL, Long.valueOf(getLong(FIRST_CONTROL, cursor, contentValues, currentTimeMillis)));
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
        Object obj = PerfettoProtoLogImpl.NULL_STRING;
        sb.append(strArr2 != null ? Integer.valueOf(strArr2.length) : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(" projection length ");
        if (strArr != null) {
            obj = Integer.valueOf(strArr.length);
        }
        sb.append(obj);
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
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (DBG) {
            Log.v(TAG, "insert uri " + uri.toSafeString());
        }
        Uri updateIfExist = updateIfExist(uri, contentValues);
        if (updateIfExist == null) {
            try {
                long insert = this.mDbHelper.getDatabase(true).insert(this.mDbHelper.getTableName(), null, this.mDbHelper.checkAndGetContentValues(null, contentValues));
                if (insert >= 0) {
                    Log.d(TAG, "Inserted at " + insert);
                    updateIfExist = getContentUri(insert);
                } else {
                    Log.e(TAG, "Failed to insert - " + insert);
                    return null;
                }
            } catch (SQLiteException e) {
                Log.e(TAG, "Failed to insert - " + e);
            }
        }
        this.mContext.getContentResolver().notifyChange(updateIfExist, null);
        return updateIfExist;
    }

    private Uri getContentUri(long j) {
        return Uri.parse("content://com.samsung.server.wifi/control/" + j);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x009d A[Catch: SQLiteException -> 0x00ad, TRY_ENTER, TRY_LEAVE, TryCatch #5 {SQLiteException -> 0x00ad, blocks: (B:18:0x009d, B:37:0x00ac, B:36:0x00a9, B:31:0x00a3), top: B:13:0x0056, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00df A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.net.Uri updateIfExist(android.net.Uri r12, android.content.ContentValues r13) {
        /*
            r11 = this;
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r0 = r11.mDbHelper
            java.lang.String r0 = r0.getUniqueColumnName()
            boolean r1 = r13.containsKey(r0)
            r2 = 0
            java.lang.String r3 = "WifiControlHistoryProvider"
            if (r1 != 0) goto L16
            java.lang.String r11 = "unique value is not in the content"
            android.util.Log.e(r3, r11)
            return r2
        L16:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r4 = "=?"
            r1.append(r4)
            java.lang.String r8 = r1.toString()
            java.lang.Object r0 = r13.get(r0)
            java.lang.String r1 = java.lang.String.valueOf(r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r4 = "try to find "
            r0.<init>(r4)
            r0.append(r8)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.util.Log.v(r3, r0)
            r4 = 0
            java.lang.String[] r9 = new java.lang.String[]{r1}     // Catch: android.database.sqlite.SQLiteException -> Lb2
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r0 = r11.mDbHelper     // Catch: android.database.sqlite.SQLiteException -> Lb2
            java.lang.String r10 = r0.getDefaultSortOrder()     // Catch: android.database.sqlite.SQLiteException -> Lb2
            r7 = 0
            r5 = r11
            r6 = r12
            android.database.Cursor r11 = r5.query(r6, r7, r8, r9, r10)     // Catch: android.database.sqlite.SQLiteException -> Lb0
            if (r11 == 0) goto L7d
            int r12 = r11.getCount()     // Catch: java.lang.Throwable -> L79
            if (r12 <= 0) goto L7d
            r11.moveToFirst()     // Catch: java.lang.Throwable -> L79
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r12 = r5.mDbHelper     // Catch: java.lang.Throwable -> L79
            android.content.ContentValues r12 = r12.checkAndGetContentValues(r11, r13)     // Catch: java.lang.Throwable -> L79
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r13 = r5.mDbHelper     // Catch: java.lang.Throwable -> L76
            java.lang.String r13 = r13.getIdColumnName()     // Catch: java.lang.Throwable -> L76
            int r13 = r11.getColumnIndex(r13)     // Catch: java.lang.Throwable -> L76
            int r4 = r11.getInt(r13)     // Catch: java.lang.Throwable -> L76
            goto L9b
        L76:
            r0 = move-exception
            r13 = r0
            goto La1
        L79:
            r0 = move-exception
            r13 = r0
            r12 = r2
            goto La1
        L7d:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L79
            r12.<init>()     // Catch: java.lang.Throwable -> L79
            r12.append(r1)     // Catch: java.lang.Throwable -> L79
            java.lang.String r13 = " is not existed in "
            r12.append(r13)     // Catch: java.lang.Throwable -> L79
            com.samsung.android.wifi.db.WifiControlHistoryProvider$DatabaseHelper r13 = r5.mDbHelper     // Catch: java.lang.Throwable -> L79
            java.lang.String r13 = r13.getTableName()     // Catch: java.lang.Throwable -> L79
            r12.append(r13)     // Catch: java.lang.Throwable -> L79
            java.lang.String r12 = r12.toString()     // Catch: java.lang.Throwable -> L79
            android.util.Log.v(r3, r12)     // Catch: java.lang.Throwable -> L79
            r12 = r2
        L9b:
            if (r11 == 0) goto Lc8
            r11.close()     // Catch: android.database.sqlite.SQLiteException -> Lad
            goto Lc8
        La1:
            if (r11 == 0) goto Lac
            r11.close()     // Catch: java.lang.Throwable -> La7
            goto Lac
        La7:
            r0 = move-exception
            r11 = r0
            r13.addSuppressed(r11)     // Catch: android.database.sqlite.SQLiteException -> Lad
        Lac:
            throw r13     // Catch: android.database.sqlite.SQLiteException -> Lad
        Lad:
            r0 = move-exception
            r11 = r0
            goto Lb7
        Lb0:
            r0 = move-exception
            goto Lb5
        Lb2:
            r0 = move-exception
            r5 = r11
            r6 = r12
        Lb5:
            r11 = r0
            r12 = r2
        Lb7:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "Failed to update - "
            r13.<init>(r0)
            r13.append(r11)
            java.lang.String r11 = r13.toString()
            android.util.Log.e(r3, r11)
        Lc8:
            if (r12 == 0) goto Ldf
            java.lang.String[] r11 = new java.lang.String[]{r1}
            int r11 = r5.update(r6, r12, r8, r11)
            if (r11 != 0) goto Ld9
            java.lang.String r11 = "Failed to update"
            android.util.Log.e(r3, r11)
        Ld9:
            long r11 = (long) r4
            android.net.Uri r11 = r5.getContentUri(r11)
            return r11
        Ldf:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.wifi.db.WifiControlHistoryProvider.updateIfExist(android.net.Uri, android.content.ContentValues):android.net.Uri");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        if (DBG) {
            Log.v(TAG, "update uri " + uri.toSafeString());
        }
        int i = 0;
        try {
            try {
                i = this.mDbHelper.getDatabase(true).update(this.mDbHelper.getTableName(), contentValues, str, strArr);
                Log.v(TAG, "updated " + i + " rows");
                return i;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to update - " + e);
                return i;
            }
        } catch (Throwable unused) {
            return i;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        if (DBG) {
            Log.v(TAG, "delete uri " + uri.toSafeString());
        }
        int i = 0;
        try {
            try {
                i = this.mDbHelper.getDatabase(true).delete(this.mDbHelper.getTableName(), str, strArr);
                Log.v(TAG, "deleted " + i + " rows");
                return i;
            } catch (SQLiteDiskIOException | SQLiteReadOnlyDatabaseException e) {
                Log.e(TAG, "Failed to delete - " + e);
                return i;
            }
        } catch (Throwable unused) {
            return i;
        }
    }

    public static Uri getContentUri() {
        return Uri.parse("content://com.samsung.server.wifi/control");
    }
}
