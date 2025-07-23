package com.samsung.android.knox.analytics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.util.Log;

/* loaded from: classes6.dex */
class DatabaseHelper extends SQLiteOpenHelper {
    private static final String B2C_FEATURE_CREATE_TABLE = "CREATE TABLE package_feature_b2c ( packageName TEXT PRIMARY KEY, feature_name TEXT)";
    public static final String B2C_FEATURE_FIELD_FEATURE = "feature_name";
    public static final String B2C_FEATURE_FIELD_PACKAGE = "packageName";
    private static final String B2C_FEATURE_QUERY = "packageName = ?";
    private static final String B2C_FEATURE_TABLE = "package_feature_b2c";
    private static final String CLEANED_EVENTS_CREATE_TABLE = "CREATE TABLE cleaned_events ( id INTEGER PRIMARY KEY, vid INTEGER, firstTimestamp INTEGER, lastTimestamp INTEGER, counter INTEGER, removedEvents INTEGER, removedSize INTEGER, reason INTEGER  )";
    private static final String CLEANED_EVENTS_DELETE = "id IN (SELECT id FROM cleaned_events ORDER BY + id LIMIT ?)";
    static final String CLEANED_EVENTS_TABLE = "cleaned_events";
    static final String CLEAN_EVENT_COUNTER = "counter";
    static final String CLEAN_EVENT_FIRST_TIMESTAMP = "firstTimestamp";
    static final String CLEAN_EVENT_LAST_TIMESTAMP = "lastTimestamp";
    static final String CLEAN_EVENT_REASON = "reason";
    static final String CLEAN_EVENT_REMOVED_BYTES = "removedSize";
    static final String CLEAN_EVENT_REMOVED_EVENTS = "removedEvents";
    private static final String COMPRESSED_EVENTS_ADD_BULK_COLUMN = "ALTER TABLE compressed_events ADD bulk INTEGER DEFAULT 1000";
    private static final String COMPRESSED_EVENTS_CREATE_TABLE = "CREATE TABLE compressed_events ( id INTEGER PRIMARY KEY, length INTEGER, original_length INTEGER, bulk INTEGER DEFAULT 1000, content BLOB )";
    private static final String COMPRESSED_EVENTS_DELETE = "id IN (SELECT id FROM compressed_events ORDER BY + id LIMIT ?)";
    private static final String COMPRESSED_EVENTS_FIELD_BULK = "bulk";
    private static final String COMPRESSED_EVENTS_FIELD_CONTENT = "content";
    private static final String COMPRESSED_EVENTS_FIELD_ID = "id";
    private static final String COMPRESSED_EVENTS_FIELD_LENGTH = "length";
    private static final String COMPRESSED_EVENTS_FIELD_ORIGINAL_LENGTH = "original_length";
    private static final String COMPRESSED_EVENTS_KEY_PLAIN_EVENTS_SIZE = "plainEventsSize";
    private static final String COMPRESSED_EVENTS_TABLE = "compressed_events";
    private static final String DATABASE_NAME = "analytics.db";
    private static final int DATABASE_VERSION = 10;
    private static final int DB_CLEAN_EVENT_CONTENT_SIZE = 8;
    private static final String EVENTS_ADD_BULK_COLUMN = "ALTER TABLE events ADD bulk INTEGER DEFAULT 1";
    private static final String EVENTS_CREATE_TABLE = "CREATE TABLE events ( id INTEGER PRIMARY KEY, vid INTEGER, bulk INTEGER DEFAULT 1, data BLOB )";
    private static final String EVENTS_DELETE = "id IN (SELECT id FROM events ORDER BY + id LIMIT ?)";
    private static final String EVENTS_DELETE_UP_TO_ID = "id <= ?";
    static final String EVENTS_FIELD_BULK = "bulk";
    static final String EVENTS_FIELD_DATA = "data";
    static final String EVENTS_FIELD_ID = "id";
    static final String EVENTS_FIELD_VID = "vid";
    static final String EVENTS_TABLE = "events";
    private static final String FEATURES_BLACKLIST_CREATE_TABLE = "CREATE TABLE feature_blocklist ( feature TEXT PRIMARY KEY, event TEXT NOT NULL)";
    private static final String FEATURES_BLACKLIST_FIELD_EVENT = "event";
    private static final String FEATURES_BLACKLIST_FIELD_FEATURE = "feature";
    private static final String FEATURES_BLACKLIST_LEGACY_TABLE = "features_blacklist";
    private static final String FEATURES_BLACKLIST_TABLE = "feature_blocklist";
    private static final String FEATURES_WHITELIST_CREATE_TABLE = "CREATE TABLE features_whitelist ( feature TEXT PRIMARY KEY, enable_type INTEGER)";
    private static final String FEATURES_WHITELIST_DELETE = "feature = ?";
    public static final String FEATURES_WHITELIST_FIELD_ENABLE = "enable_type";
    public static final String FEATURES_WHITELIST_FIELD_FEATURE = "feature";
    private static final String FEATURES_WHITELIST_TABLE = "features_whitelist";
    private static final String LAST_EVENT_ID_CREATE_TABLE = "CREATE TABLE internal_data ( last_event_id INTEGER )";
    private static final String LAST_EVENT_ID_FIELD = "last_event_id";
    private static final String LAST_EVENT_ID_TABLE = "internal_data";
    private static final int NORMAL_EVENT_CONTENT_SIZE = 4;
    private static final String SYNTHETIC_KEY_CREATE_TABLE = "CREATE TABLE synthetic_key ( row_id INTEGER)";
    static final String SYNTHETIC_KEY_TABLE = "synthetic_key";
    private static final String SYNTHETIC_ROW_ID = "row_id";
    private static final String TAG = "[KnoxAnalytics] DatabaseHelper";
    private static final String VERSIONING_CREATE_TABLE = "CREATE TABLE version ( id INTEGER PRIMARY KEY, data TEXT )";
    private static final String VERSIONING_DELETE_UP_TO_ID = "id <= ?";
    private static final String VERSIONING_FIELD_DATA = "data";
    private static final String VERSIONING_FIELD_ID = "id";
    private static final String VERSIONING_TABLE = "version";
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 10);
        Log.d(TAG, "constructor()");
        this.mContext = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        super.onConfigure(sQLiteDatabase);
        Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode = OFF;", null);
        if (rawQuery != null) {
            rawQuery.close();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(EVENTS_CREATE_TABLE);
        sQLiteDatabase.execSQL(VERSIONING_CREATE_TABLE);
        sQLiteDatabase.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
        sQLiteDatabase.execSQL(LAST_EVENT_ID_CREATE_TABLE);
        sQLiteDatabase.execSQL(CLEANED_EVENTS_CREATE_TABLE);
        sQLiteDatabase.execSQL(SYNTHETIC_KEY_CREATE_TABLE);
        sQLiteDatabase.execSQL(COMPRESSED_EVENTS_CREATE_TABLE);
        sQLiteDatabase.execSQL(FEATURES_WHITELIST_CREATE_TABLE);
        sQLiteDatabase.execSQL(B2C_FEATURE_CREATE_TABLE);
        putLastIdDefaultValue(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.d(TAG, String.format("oldVersion=%d, newVersion=%d", Integer.valueOf(i), Integer.valueOf(i2)));
        if (i < 2) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS events");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS version");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS feature_blocklist");
            sQLiteDatabase.execSQL(EVENTS_CREATE_TABLE);
            sQLiteDatabase.execSQL(VERSIONING_CREATE_TABLE);
            sQLiteDatabase.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
        }
        if (i < 3) {
            sQLiteDatabase.execSQL(LAST_EVENT_ID_CREATE_TABLE);
            putLastIdDefaultValue(sQLiteDatabase);
        }
        if (i < 4) {
            sQLiteDatabase.execSQL(CLEANED_EVENTS_CREATE_TABLE);
        }
        if (i < 5) {
            sQLiteDatabase.execSQL(SYNTHETIC_KEY_CREATE_TABLE);
        }
        if (i < 6) {
            sQLiteDatabase.execSQL(COMPRESSED_EVENTS_CREATE_TABLE);
        }
        if (i < 7) {
            sQLiteDatabase.execSQL(FEATURES_WHITELIST_CREATE_TABLE);
            sQLiteDatabase.execSQL(B2C_FEATURE_CREATE_TABLE);
        }
        if (i < 8) {
            sQLiteDatabase.execSQL(EVENTS_ADD_BULK_COLUMN);
            sQLiteDatabase.execSQL(COMPRESSED_EVENTS_ADD_BULK_COLUMN);
        }
        if (i < 9) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS features_blacklist");
            sQLiteDatabase.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
        }
        if (i < 10) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS cleaned_events");
            sQLiteDatabase.execSQL(CLEANED_EVENTS_CREATE_TABLE);
        }
    }

    private boolean isContentValuesValid(ContentValues contentValues, int i) {
        if (!contentValues.containsKey("id") || contentValues.getAsInteger("id") == null || !contentValues.containsKey("vid") || contentValues.getAsInteger("vid") == null) {
            Log.e(TAG, "Wrong fields! Missing id/vid");
            return false;
        }
        if (i == 0) {
            if (!contentValues.containsKey("firstTimestamp") || contentValues.getAsLong("firstTimestamp") == null || !contentValues.containsKey("lastTimestamp") || contentValues.getAsLong("lastTimestamp") == null || !contentValues.containsKey("counter") || contentValues.getAsInteger("counter") == null || !contentValues.containsKey("removedEvents") || contentValues.getAsInteger("removedEvents") == null || !contentValues.containsKey("removedSize") || contentValues.getAsLong("removedSize") == null || !contentValues.containsKey("reason") || contentValues.getAsInteger("reason") == null || contentValues.size() != 8) {
                Log.e(TAG, "Wrong fields! Invalid clean event");
                return false;
            }
        } else if (i == 1) {
            if (!contentValues.containsKey("bulk") || contentValues.getAsInteger("bulk") == null || !contentValues.containsKey("data") || contentValues.getAsString("data") == null || contentValues.getAsString("data").isEmpty() || contentValues.size() != 4) {
                Log.e(TAG, "Wrong fields! Invalid event");
                return false;
            }
        } else {
            Log.e(TAG, "Unknown table");
            return false;
        }
        return true;
    }

    public long addEvent(ContentValues contentValues, int i) {
        String str;
        int intValue;
        long insert;
        if (!isContentValuesValid(contentValues, i)) {
            Log.e(TAG, "addEvent() : Invalid content values");
            return -1L;
        }
        String str2 = TAG;
        Log.d(str2, "addEvent()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String[] tableAndWhereClauseFromType = getTableAndWhereClauseFromType(i);
        if (tableAndWhereClauseFromType == null || tableAndWhereClauseFromType.length <= 0 || (str = tableAndWhereClauseFromType[0]) == null || str.isEmpty()) {
            Log.d(str2, "addEvent(): Wrong log type");
            return -1L;
        }
        if (i == 0) {
            intValue = 1;
            if (hasCleanedEventRow()) {
                insert = updateCleanedEvent(contentValues, writableDatabase, contentValues.getAsInteger("reason").intValue());
            } else {
                insert = writableDatabase.insert(tableAndWhereClauseFromType[0], null, contentValues);
            }
        } else {
            intValue = contentValues.getAsInteger("bulk").intValue();
            insert = writableDatabase.insert(tableAndWhereClauseFromType[0], null, contentValues);
        }
        if (insert == -1) {
            Log.e(str2, "addEvent(): Couldn't add event");
            return insert;
        }
        updateLastId((intValue + insert) - 1);
        return insert;
    }

    private String[] getTableAndWhereClauseFromType(int i) {
        String[] strArr = new String[2];
        if (i == 0) {
            strArr[0] = "cleaned_events";
            strArr[1] = CLEANED_EVENTS_DELETE;
            return strArr;
        }
        if (i == 1) {
            strArr[0] = "events";
            strArr[1] = EVENTS_DELETE;
            return strArr;
        }
        Log.d(TAG, "getTableAndWhereClauseFromType(): Unknown table");
        return strArr;
    }

    public Cursor getEventChunk(Integer num) {
        int i;
        Log.d(TAG, "getEventChunk()");
        Cursor query = getReadableDatabase().query("events", new String[]{"id", "vid", "bulk", "data"}, null, null, null, null, null, null);
        if (num == null) {
            return query;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "vid", "bulk", "data"}, 1);
        if (query != null && query.getCount() > 0) {
            int columnIndex = query.getColumnIndex("id");
            int columnIndex2 = query.getColumnIndex("vid");
            int columnIndex3 = query.getColumnIndex("bulk");
            int columnIndex4 = query.getColumnIndex("data");
            int i2 = 0;
            while (query.moveToNext() && (i2 = i2 + (i = query.getInt(columnIndex3))) <= num.intValue()) {
                matrixCursor.addRow(new Object[]{Long.valueOf(query.getLong(columnIndex)), Integer.valueOf(query.getInt(columnIndex2)), Integer.valueOf(i), query.getBlob(columnIndex4)});
            }
        }
        if (query != null) {
            query.close();
        }
        return matrixCursor;
    }

    public Cursor getLastId() {
        Log.d(TAG, "getLastId()");
        Cursor query = getReadableDatabase().query(LAST_EVENT_ID_TABLE, new String[]{LAST_EVENT_ID_FIELD}, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    private void updateLastId(long j) {
        Log.d(TAG, "updateLastId(" + j + NavigationBarInflaterView.KEY_CODE_END);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAST_EVENT_ID_FIELD, Long.valueOf(j));
        writableDatabase.update(LAST_EVENT_ID_TABLE, contentValues, null, null);
    }

    private void putLastIdDefaultValue(SQLiteDatabase sQLiteDatabase) {
        String str = TAG;
        Log.d(str, "putLastIdDefaultValue()");
        ContentValues contentValues = new ContentValues();
        contentValues.put(LAST_EVENT_ID_FIELD, (Integer) (-1));
        if (sQLiteDatabase.insert(LAST_EVENT_ID_TABLE, null, contentValues) == -1) {
            Log.e(str, "putLastIdDefaultValue(): Error");
        }
    }

    public Cursor getEventCount() {
        Log.d(TAG, "getEventCount()");
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Contract.Events.Projection.COUNT_ONLY}, 1);
        matrixCursor.addRow(new Object[]{Long.valueOf(getEventCountValue())});
        return matrixCursor;
    }

    Cursor getCompressedEventChunk(Integer num) {
        String str = TAG;
        Log.d(str, "getCompressedEventChunk()");
        Cursor query = getReadableDatabase().query("compressed_events", null, null, null, null, null, "id ASC", num != null ? String.valueOf(num) : null);
        if (query != null && query.getCount() > 0) {
            return query;
        }
        Log.d(str, "getCompressedEventChunk(): There is no compressed data");
        if (query != null) {
            query.close();
        }
        return null;
    }

    public long getEventCountValue() {
        Cursor cursor;
        try {
            cursor = getEventCountCursor();
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        int i = 0;
                        while (cursor.moveToNext()) {
                            i += cursor.getInt(0);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return i;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            long j = 0;
            if (cursor != null) {
                cursor.close();
            }
            return j;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    public Cursor getEventCountCursor() {
        Cursor query = getReadableDatabase().query("events", new String[]{"bulk"}, null, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            return query;
        }
        if (query == null) {
            return null;
        }
        query.close();
        return null;
    }

    public long getCompressedEventCountValue() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), "compressed_events");
    }

    public int getTotalCompressedEvent(Integer num) {
        Cursor totalCompressedEventCursor;
        Log.d(TAG, "getTotalCompressedEvent()");
        if (num == null || num.intValue() <= 0 || (totalCompressedEventCursor = getTotalCompressedEventCursor(String.valueOf(num))) == null) {
            return 0;
        }
        int i = 0;
        while (totalCompressedEventCursor.moveToNext()) {
            try {
                i += totalCompressedEventCursor.getInt(0);
            } finally {
                totalCompressedEventCursor.close();
            }
        }
        return i;
    }

    public Cursor getTotalCompressedEventCursor() {
        return getTotalCompressedEventCursor(null);
    }

    public Cursor getTotalCompressedEventCursor(String str) {
        String str2 = TAG;
        Log.d(str2, "getTotalCompressedEventCursor(" + str + NavigationBarInflaterView.KEY_CODE_END);
        Cursor query = getReadableDatabase().query("compressed_events", new String[]{"bulk"}, null, null, null, null, null, str);
        if (query != null && query.getCount() > 0) {
            return query;
        }
        Log.d(str2, "getTotalCompressedEventCursor(): There is no compressed events");
        if (query == null) {
            return null;
        }
        query.close();
        return null;
    }

    public long deleteEventChunk(long j, int i) {
        String str;
        String str2 = TAG;
        Log.d(str2, "deleteEventChunk(" + j + NavigationBarInflaterView.KEY_CODE_END);
        if (j < 1) {
            Log.e(str2, "deleteEventChunk(): invalid number");
            return 0L;
        }
        String[] tableAndWhereClauseFromType = getTableAndWhereClauseFromType(i);
        if (tableAndWhereClauseFromType == null || tableAndWhereClauseFromType.length <= 0 || (str = tableAndWhereClauseFromType[0]) == null || tableAndWhereClauseFromType[1] == null || str.isEmpty() || tableAndWhereClauseFromType[1].isEmpty()) {
            Log.d(str2, "deleteEventChunk(): Wrong log type");
            return -1L;
        }
        return getWritableDatabase().delete(tableAndWhereClauseFromType[0], tableAndWhereClauseFromType[1], new String[]{String.valueOf(j)});
    }

    public void deleteEventsUpToSyntheticId() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int syntheticRowId = getSyntheticRowId();
        if (syntheticRowId == -1) {
            Log.d(TAG, "deleteEventsUpToSyntheticId(): No legacy content");
            return;
        }
        int delete = writableDatabase.delete("events", "id <= ?", new String[]{String.valueOf(syntheticRowId)});
        if (delete > 1) {
            Log.d(TAG, "deleteEventsUpToSyntheticId(): " + delete + " events, up to " + syntheticRowId + "have been deleted");
            ContentValues contentValues = new ContentValues();
            contentValues.put("row_id", "-1");
            writableDatabase.update("synthetic_key", contentValues, null, null);
        }
    }

    public long deleteFromAllEventTables() {
        Log.d(TAG, "deleteAllEvents()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete("events", null, null) + writableDatabase.delete("compressed_events", null, null) + writableDatabase.delete("cleaned_events", null, null);
    }

    public long deleteUpTo(long j) {
        Log.d(TAG, "deleteUpTo(" + j + NavigationBarInflaterView.KEY_CODE_END);
        return getWritableDatabase().delete("events", "id <= ?", new String[]{String.valueOf(j)});
    }

    public long getCurrentDatabaseSizeInBytes() {
        Log.d(TAG, "getCurrentDatabaseSizeInBytes()");
        return this.mContext.getDatabasePath(DATABASE_NAME).length();
    }

    public Cursor getCurrentDatabaseSizeCursor() {
        Log.d(TAG, "getCurrentDatabaseSizeCursor()");
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"databaseSize"}, 1);
        matrixCursor.addRow(new Object[]{Long.valueOf(getCurrentDatabaseSizeInBytes())});
        if (matrixCursor.getCount() > 0) {
            matrixCursor.moveToFirst();
        }
        return matrixCursor;
    }

    private Cursor getCurrentVersioningId() {
        Cursor query = getReadableDatabase().query("version", new String[]{"id"}, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getCurrentVersioningIdInternal() {
        /*
            r1 = this;
            android.database.Cursor r1 = r1.getCurrentVersioningId()
            if (r1 == 0) goto L23
            int r0 = r1.getCount()     // Catch: java.lang.Throwable -> L17
            if (r0 <= 0) goto L23
            java.lang.String r0 = "id"
            int r0 = r1.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L17
            int r0 = r1.getInt(r0)     // Catch: java.lang.Throwable -> L17
            goto L24
        L17:
            r0 = move-exception
            if (r1 == 0) goto L22
            r1.close()     // Catch: java.lang.Throwable -> L1e
            goto L22
        L1e:
            r1 = move-exception
            r0.addSuppressed(r1)
        L22:
            throw r0
        L23:
            r0 = -1
        L24:
            if (r1 == 0) goto L29
            r1.close()
        L29:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.DatabaseHelper.getCurrentVersioningIdInternal():int");
    }

    public Cursor getCleanedEventsCursor() {
        Log.d(TAG, "getCleanedEventsCursor()");
        Cursor query = getReadableDatabase().query("cleaned_events", null, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    public Cursor getVersioningBlob() {
        return getReadableDatabase().query("version", new String[]{"data", "id"}, null, null, null, null, null);
    }

    public int addVersioningBlob(ContentValues contentValues) {
        long j;
        String str = TAG;
        Log.d(str, "addVersioningBlob()");
        if (contentValues == null || !contentValues.containsKey("id") || !contentValues.containsKey("data") || !contentValues.containsKey(Contract.Versioning.AUX_FIELD_EVENT_ID) || contentValues.size() != 3) {
            Log.e(str, "addVersioningBlob(): wrong fields!");
            return -1;
        }
        Integer asInteger = contentValues.getAsInteger("id");
        if (asInteger == null) {
            Log.e(str, "addVersioningBlob(): versioning id is null!");
            return -1;
        }
        Long asLong = contentValues.getAsLong(Contract.Versioning.AUX_FIELD_EVENT_ID);
        if (asLong != null) {
            contentValues.remove(Contract.Versioning.AUX_FIELD_EVENT_ID);
            j = getWritableDatabase().insert("version", null, contentValues);
        } else {
            j = -1;
        }
        if (asLong == null || j == -1) {
            Log.e(str, "addVersioningBlob(): error");
            return -1;
        }
        updateLastId(asLong.longValue());
        return asInteger.intValue();
    }

    public long deleteFromVersion(long j) {
        Log.d(TAG, "deleteFromVersion()");
        return getWritableDatabase().delete("version", "id <= ?", new String[]{String.valueOf(j)});
    }

    public long addFeaturesBlacklist(ContentValues contentValues) {
        String str = TAG;
        Log.e(str, "addFeaturesBlacklist()");
        if (!contentValues.containsKey("feature") || TextUtils.isEmpty(contentValues.getAsString("feature")) || !contentValues.containsKey("event") || TextUtils.isEmpty(contentValues.getAsString("event")) || contentValues.size() != 2) {
            Log.e(str, "addFeaturesBlacklist(): invalid fields!");
            return -1L;
        }
        return getWritableDatabase().replace("feature_blocklist", null, contentValues);
    }

    public Cursor getFeaturesBlacklist() {
        Log.d(TAG, "getFeaturesBlacklist()");
        Cursor query = getReadableDatabase().query("feature_blocklist", new String[]{"feature", "event"}, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    public long deleteFeaturesBlacklist() {
        Log.d(TAG, "deleteFeaturesBlacklist()");
        return getWritableDatabase().delete("feature_blocklist", null, null);
    }

    int getSyntheticRowId() {
        Cursor query = getReadableDatabase().query("synthetic_key", new String[]{"row_id"}, null, null, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    query.moveToFirst();
                    int i = query.getInt(query.getColumnIndex("row_id"));
                    Log.d(TAG, "getSyntheticRowId(): " + i);
                    if (query != null) {
                        query.close();
                    }
                    return i;
                }
            } catch (Throwable th) {
                if (query == null) {
                    throw th;
                }
                try {
                    query.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
        Log.d(TAG, "getSyntheticRowId(): Key is deleted or it is not generated yet.");
        if (query != null) {
            query.close();
        }
        return -1;
    }

    public void setSyntheticRowId() {
        String str = TAG;
        Log.d(str, "setSyntheticRowId()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor lastId = getLastId();
        if (lastId != null) {
            try {
                if (lastId.getCount() > 0) {
                    contentValues.put("row_id", Integer.valueOf(lastId.getInt(lastId.getColumnIndex(LAST_EVENT_ID_FIELD))));
                    Log.d(str, "setSyntheticRowId(): Marked event id = " + writableDatabase.insert("synthetic_key", null, contentValues));
                    if (lastId != null) {
                        lastId.close();
                        return;
                    }
                }
            } catch (Throwable th) {
                if (lastId != null) {
                    try {
                        lastId.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        Log.d(str, "setSyntheticRowId(): There is no data in events table.");
        if (lastId != null) {
            lastId.close();
        }
    }

    long deleteCompressedEventChunk(long j) {
        String str = TAG;
        Log.d(str, "deleteCompressedEventChunk(" + j + NavigationBarInflaterView.KEY_CODE_END);
        if (j <= 0) {
            Log.e(str, "deleteCompressedEventChunk(): invalid number");
            return 0L;
        }
        return getWritableDatabase().delete("compressed_events", COMPRESSED_EVENTS_DELETE, new String[]{String.valueOf(j)});
    }

    boolean performCompressedEventsTransaction(ContentValues contentValues) {
        String str = TAG;
        Log.d(str, "performCompressedEventsTransaction()");
        if (!contentValues.containsKey("content") || TextUtils.isEmpty(contentValues.getAsString("content")) || !contentValues.containsKey("length") || contentValues.getAsInteger("length") == null || !contentValues.containsKey("original_length") || contentValues.getAsInteger("original_length") == null || !contentValues.containsKey("plainEventsSize") || contentValues.getAsInteger("plainEventsSize") == null || !contentValues.containsKey("bulk") || contentValues.getAsInteger("bulk") == null || contentValues.size() != 5) {
            Log.e(str, "performCompressedEventsTransaction(): wrong fields!");
            return false;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int intValue = contentValues.getAsInteger("plainEventsSize").intValue();
        contentValues.remove("plainEventsSize");
        writableDatabase.beginTransaction();
        try {
            try {
                if (writableDatabase.insert("compressed_events", null, contentValues) == -1) {
                    throw new SQLException("Transaction Failure. Not possible to insert compressed events.");
                }
                if (writableDatabase.delete("events", EVENTS_DELETE, new String[]{String.valueOf(intValue)}) == 0) {
                    throw new SQLException("Transaction Failure. Not possible to delete plain-text events.");
                }
                writableDatabase.setTransactionSuccessful();
                writableDatabase.endTransaction();
                return true;
            } catch (SQLException e) {
                Log.e(TAG, "performCompressedEventsTransaction(): ", e);
                writableDatabase.endTransaction();
                return false;
            }
        } catch (Throwable th) {
            writableDatabase.endTransaction();
            throw th;
        }
    }

    public long addFeaturesWhitelist(ContentValues contentValues) {
        String str = TAG;
        Log.d(str, "addFeaturesWhitelist()");
        if (!contentValues.containsKey("feature") || contentValues.getAsString("feature") == null || contentValues.getAsString("feature").isEmpty() || contentValues.size() != 2) {
            Log.e(str, "addFeaturesWhitelist(): missing feature field!");
            return -1L;
        }
        if (!contentValues.containsKey("enable_type") || contentValues.getAsInteger("enable_type") == null) {
            contentValues.put("enable_type", (Integer) 1);
        }
        return getWritableDatabase().replace("features_whitelist", null, contentValues);
    }

    public Cursor getFeaturesWhitelist() {
        Log.d(TAG, "getFeaturesWhitelist()");
        Cursor query = getReadableDatabase().query("features_whitelist", new String[]{"feature", "enable_type"}, null, null, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    public long deleteFeaturesWhitelist(String[] strArr) {
        Log.d(TAG, "deleteFeaturesWhitelist()");
        if (strArr == null || strArr.length == 0) {
            return getWritableDatabase().delete("features_whitelist", null, null);
        }
        long j = 0;
        for (String str : strArr) {
            j += deleteFeatureWhitelist(str);
        }
        return j;
    }

    public long deleteFeatureWhitelist(String str) {
        Log.d(TAG, "deleteFeatureWhitelist()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (str == null || str.isEmpty()) {
            return 0L;
        }
        return writableDatabase.delete("features_whitelist", FEATURES_WHITELIST_DELETE, new String[]{str});
    }

    public long addB2CFeatures(ContentValues contentValues) {
        Log.e(TAG, "addB2CFeatures()");
        if (!contentValues.containsKey("packageName") || !contentValues.containsKey("feature_name") || contentValues.getAsString("packageName") == null || contentValues.getAsString("feature_name") == null) {
            return -1L;
        }
        return getWritableDatabase().replace("package_feature_b2c", null, contentValues);
    }

    public Cursor getB2CFeatures(String[] strArr) {
        Log.d(TAG, "getB2CFeatures()");
        Cursor query = getReadableDatabase().query("package_feature_b2c", new String[]{"packageName", "feature_name"}, (strArr == null || strArr.length <= 0) ? null : B2C_FEATURE_QUERY, strArr, null, null, null);
        if (query != null && query.getCount() > 0) {
            query.moveToFirst();
        }
        return query;
    }

    public long deleteB2CFeatures(String[] strArr) {
        Log.d(TAG, "deleteB2CFeatures()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (strArr == null || strArr.length == 0) {
            return 0L;
        }
        return writableDatabase.delete("package_feature_b2c", B2C_FEATURE_QUERY, strArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean hasCleanedEventRow() {
        /*
            r9 = this;
            java.lang.String r0 = "hasCleanedEventsRow(): "
            android.database.sqlite.SQLiteDatabase r1 = r9.getReadableDatabase()
            java.lang.String r9 = "id"
            java.lang.String[] r3 = new java.lang.String[]{r9}
            r7 = 0
            r8 = 0
            java.lang.String r2 = "cleaned_events"
            r4 = 0
            r5 = 0
            r6 = 0
            android.database.Cursor r9 = r1.query(r2, r3, r4, r5, r6, r7, r8)
            if (r9 == 0) goto L24
            int r1 = r9.getCount()     // Catch: java.lang.Throwable -> L21
            if (r1 <= 0) goto L24
            r1 = 1
            goto L25
        L21:
            r0 = move-exception
            r1 = r0
            goto L3c
        L24:
            r1 = 0
        L25:
            java.lang.String r2 = com.samsung.android.knox.analytics.database.DatabaseHelper.TAG     // Catch: java.lang.Throwable -> L21
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L21
            r3.<init>(r0)     // Catch: java.lang.Throwable -> L21
            r3.append(r1)     // Catch: java.lang.Throwable -> L21
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L21
            com.samsung.android.knox.analytics.util.Log.d(r2, r0)     // Catch: java.lang.Throwable -> L21
            if (r9 == 0) goto L3b
            r9.close()
        L3b:
            return r1
        L3c:
            if (r9 == 0) goto L47
            r9.close()     // Catch: java.lang.Throwable -> L42
            goto L47
        L42:
            r0 = move-exception
            r9 = r0
            r1.addSuppressed(r9)
        L47:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.DatabaseHelper.hasCleanedEventRow():boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001f, code lost:
    
        if (r12 != null) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long updateCleanedEvent(android.content.ContentValues r13, android.database.sqlite.SQLiteDatabase r14, int r15) {
        /*
            r12 = this;
            java.lang.String r0 = "id"
            java.lang.String r1 = "reason"
            java.lang.String r2 = "lastTimestamp"
            java.lang.String r3 = "removedSize"
            java.lang.String r4 = "removedEvents"
            java.lang.String r5 = "counter"
            r6 = -1
            android.database.Cursor r12 = r12.getExistingCleanEventValues()     // Catch: java.lang.IllegalArgumentException -> Ldb
            if (r12 != 0) goto L28
            java.lang.String r13 = com.samsung.android.knox.analytics.database.DatabaseHelper.TAG     // Catch: java.lang.Throwable -> L25
            java.lang.String r14 = "updateCleanedEvent(): No existing clean event"
            com.samsung.android.knox.analytics.util.Log.d(r13, r14)     // Catch: java.lang.Throwable -> L25
            if (r12 == 0) goto L24
        L21:
            r12.close()     // Catch: java.lang.IllegalArgumentException -> Ldb
        L24:
            return r6
        L25:
            r13 = move-exception
            goto Ld0
        L28:
            int r8 = r12.getColumnIndexOrThrow(r2)     // Catch: java.lang.Throwable -> L25
            long r8 = r12.getLong(r8)     // Catch: java.lang.Throwable -> L25
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch: java.lang.Throwable -> L25
            r13.put(r2, r8)     // Catch: java.lang.Throwable -> L25
            java.lang.Integer r2 = r13.getAsInteger(r5)     // Catch: java.lang.Throwable -> L25
            r8 = 0
            if (r2 == 0) goto L47
            java.lang.Integer r2 = r13.getAsInteger(r5)     // Catch: java.lang.Throwable -> L25
            int r2 = r2.intValue()     // Catch: java.lang.Throwable -> L25
            goto L48
        L47:
            r2 = r8
        L48:
            int r9 = r12.getColumnIndexOrThrow(r5)     // Catch: java.lang.Throwable -> L25
            int r9 = r12.getInt(r9)     // Catch: java.lang.Throwable -> L25
            int r2 = r2 + r9
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch: java.lang.Throwable -> L25
            r13.put(r5, r2)     // Catch: java.lang.Throwable -> L25
            java.lang.Integer r2 = r13.getAsInteger(r4)     // Catch: java.lang.Throwable -> L25
            if (r2 == 0) goto L66
            java.lang.Integer r2 = r13.getAsInteger(r4)     // Catch: java.lang.Throwable -> L25
            int r8 = r2.intValue()     // Catch: java.lang.Throwable -> L25
        L66:
            int r2 = r12.getColumnIndexOrThrow(r4)     // Catch: java.lang.Throwable -> L25
            int r2 = r12.getInt(r2)     // Catch: java.lang.Throwable -> L25
            int r8 = r8 + r2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L25
            r13.put(r4, r2)     // Catch: java.lang.Throwable -> L25
            java.lang.Long r2 = r13.getAsLong(r3)     // Catch: java.lang.Throwable -> L25
            r4 = 0
            if (r2 == 0) goto L87
            java.lang.Long r2 = r13.getAsLong(r3)     // Catch: java.lang.Throwable -> L25
            long r8 = r2.longValue()     // Catch: java.lang.Throwable -> L25
            goto L88
        L87:
            r8 = r4
        L88:
            int r2 = r12.getColumnIndexOrThrow(r3)     // Catch: java.lang.Throwable -> L25
            int r2 = r12.getInt(r2)     // Catch: java.lang.Throwable -> L25
            long r10 = (long) r2     // Catch: java.lang.Throwable -> L25
            long r8 = r8 + r10
            java.lang.Long r2 = java.lang.Long.valueOf(r8)     // Catch: java.lang.Throwable -> L25
            r13.put(r3, r2)     // Catch: java.lang.Throwable -> L25
            int r2 = r12.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L25
            int r2 = r12.getInt(r2)     // Catch: java.lang.Throwable -> L25
            if (r15 <= r2) goto Laa
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)     // Catch: java.lang.Throwable -> L25
            r13.put(r1, r15)     // Catch: java.lang.Throwable -> L25
        Laa:
            java.lang.String r15 = "cleaned_events"
            r1 = 0
            int r14 = r14.update(r15, r13, r1, r1)     // Catch: java.lang.Throwable -> L25
            if (r14 <= 0) goto Lcb
            java.lang.String r14 = com.samsung.android.knox.analytics.database.DatabaseHelper.TAG     // Catch: java.lang.Throwable -> L25
            java.lang.String r15 = "updateCleanedEvent(): Clean events updated"
            com.samsung.android.knox.analytics.util.Log.d(r14, r15)     // Catch: java.lang.Throwable -> L25
            java.lang.Integer r14 = r13.getAsInteger(r0)     // Catch: java.lang.Throwable -> L25
            if (r14 == 0) goto Lca
            java.lang.Integer r13 = r13.getAsInteger(r0)     // Catch: java.lang.Throwable -> L25
            int r13 = r13.intValue()     // Catch: java.lang.Throwable -> L25
            long r4 = (long) r13
        Lca:
            r6 = r4
        Lcb:
            if (r12 == 0) goto Lcf
            goto L21
        Lcf:
            return r6
        Ld0:
            if (r12 == 0) goto Lda
            r12.close()     // Catch: java.lang.Throwable -> Ld6
            goto Lda
        Ld6:
            r12 = move-exception
            r13.addSuppressed(r12)     // Catch: java.lang.IllegalArgumentException -> Ldb
        Lda:
            throw r13     // Catch: java.lang.IllegalArgumentException -> Ldb
        Ldb:
            r12 = move-exception
            java.lang.String r13 = com.samsung.android.knox.analytics.database.DatabaseHelper.TAG
            java.lang.String r14 = "updateCleanedEvent(): Failed to load ContentValues"
            com.samsung.android.knox.analytics.util.Log.e(r13, r14, r12)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.DatabaseHelper.updateCleanedEvent(android.content.ContentValues, android.database.sqlite.SQLiteDatabase, int):long");
    }

    private Cursor getExistingCleanEventValues() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = readableDatabase.query("cleaned_events", null, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
            }
            return cursor;
        } catch (Throwable th) {
            Log.e(TAG, "getExistingCleanEventValues(): Failed: ", th);
            return cursor;
        }
    }
}
