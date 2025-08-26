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
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode = OFF;", null);
        if (cursorRawQuery != null) {
            cursorRawQuery.close();
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
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
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
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
        int iIntValue;
        long jInsert;
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
            iIntValue = 1;
            if (hasCleanedEventRow()) {
                jInsert = updateCleanedEvent(contentValues, writableDatabase, contentValues.getAsInteger("reason").intValue());
            } else {
                jInsert = writableDatabase.insert(tableAndWhereClauseFromType[0], null, contentValues);
            }
        } else {
            iIntValue = contentValues.getAsInteger("bulk").intValue();
            jInsert = writableDatabase.insert(tableAndWhereClauseFromType[0], null, contentValues);
        }
        if (jInsert == -1) {
            Log.e(str2, "addEvent(): Couldn't add event");
            return jInsert;
        }
        updateLastId((iIntValue + jInsert) - 1);
        return jInsert;
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
        Cursor cursorQuery = getReadableDatabase().query("events", new String[]{"id", "vid", "bulk", "data"}, null, null, null, null, null, null);
        if (num == null) {
            return cursorQuery;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", "vid", "bulk", "data"}, 1);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            int columnIndex = cursorQuery.getColumnIndex("id");
            int columnIndex2 = cursorQuery.getColumnIndex("vid");
            int columnIndex3 = cursorQuery.getColumnIndex("bulk");
            int columnIndex4 = cursorQuery.getColumnIndex("data");
            int i2 = 0;
            while (cursorQuery.moveToNext() && (i2 = i2 + (i = cursorQuery.getInt(columnIndex3))) <= num.intValue()) {
                matrixCursor.addRow(new Object[]{Long.valueOf(cursorQuery.getLong(columnIndex)), Integer.valueOf(cursorQuery.getInt(columnIndex2)), Integer.valueOf(i), cursorQuery.getBlob(columnIndex4)});
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return matrixCursor;
    }

    public Cursor getLastId() {
        Log.d(TAG, "getLastId()");
        Cursor cursorQuery = getReadableDatabase().query(LAST_EVENT_ID_TABLE, new String[]{LAST_EVENT_ID_FIELD}, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
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
        Cursor cursorQuery = getReadableDatabase().query("compressed_events", null, null, null, null, null, "id ASC", num != null ? String.valueOf(num) : null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            return cursorQuery;
        }
        Log.d(str, "getCompressedEventChunk(): There is no compressed data");
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    public long getEventCountValue() throws Throwable {
        Cursor eventCountCursor;
        try {
            eventCountCursor = getEventCountCursor();
            if (eventCountCursor != null) {
                try {
                    if (eventCountCursor.getCount() > 0) {
                        int i = 0;
                        while (eventCountCursor.moveToNext()) {
                            i += eventCountCursor.getInt(0);
                        }
                        if (eventCountCursor != null) {
                            eventCountCursor.close();
                        }
                        return i;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (eventCountCursor != null) {
                        eventCountCursor.close();
                    }
                    throw th;
                }
            }
            long j = 0;
            if (eventCountCursor != null) {
                eventCountCursor.close();
            }
            return j;
        } catch (Throwable th2) {
            th = th2;
            eventCountCursor = null;
        }
    }

    public Cursor getEventCountCursor() {
        Cursor cursorQuery = getReadableDatabase().query("events", new String[]{"bulk"}, null, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            return cursorQuery;
        }
        if (cursorQuery == null) {
            return null;
        }
        cursorQuery.close();
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
        Cursor cursorQuery = getReadableDatabase().query("compressed_events", new String[]{"bulk"}, null, null, null, null, null, str);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            return cursorQuery;
        }
        Log.d(str2, "getTotalCompressedEventCursor(): There is no compressed events");
        if (cursorQuery == null) {
            return null;
        }
        cursorQuery.close();
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
        int iDelete = writableDatabase.delete("events", "id <= ?", new String[]{String.valueOf(syntheticRowId)});
        if (iDelete > 1) {
            Log.d(TAG, "deleteEventsUpToSyntheticId(): " + iDelete + " events, up to " + syntheticRowId + "have been deleted");
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
        Cursor cursorQuery = getReadableDatabase().query("version", new String[]{"id"}, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getCurrentVersioningIdInternal() {
        int i;
        Cursor currentVersioningId = getCurrentVersioningId();
        if (currentVersioningId != null) {
            try {
                i = currentVersioningId.getCount() > 0 ? currentVersioningId.getInt(currentVersioningId.getColumnIndex("id")) : -1;
            } catch (Throwable th) {
                if (currentVersioningId != null) {
                    try {
                        currentVersioningId.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (currentVersioningId != null) {
            currentVersioningId.close();
        }
        return i;
    }

    public Cursor getCleanedEventsCursor() {
        Log.d(TAG, "getCleanedEventsCursor()");
        Cursor cursorQuery = getReadableDatabase().query("cleaned_events", null, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
    }

    public Cursor getVersioningBlob() {
        return getReadableDatabase().query("version", new String[]{"data", "id"}, null, null, null, null, null);
    }

    public int addVersioningBlob(ContentValues contentValues) {
        long jInsert;
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
            jInsert = getWritableDatabase().insert("version", null, contentValues);
        } else {
            jInsert = -1;
        }
        if (asLong == null || jInsert == -1) {
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
        Cursor cursorQuery = getReadableDatabase().query("feature_blocklist", new String[]{"feature", "event"}, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
    }

    public long deleteFeaturesBlacklist() {
        Log.d(TAG, "deleteFeaturesBlacklist()");
        return getWritableDatabase().delete("feature_blocklist", null, null);
    }

    int getSyntheticRowId() {
        Cursor cursorQuery = getReadableDatabase().query("synthetic_key", new String[]{"row_id"}, null, null, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    cursorQuery.moveToFirst();
                    int i = cursorQuery.getInt(cursorQuery.getColumnIndex("row_id"));
                    Log.d(TAG, "getSyntheticRowId(): " + i);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return i;
                }
            } catch (Throwable th) {
                if (cursorQuery == null) {
                    throw th;
                }
                try {
                    cursorQuery.close();
                    throw th;
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                    throw th;
                }
            }
        }
        Log.d(TAG, "getSyntheticRowId(): Key is deleted or it is not generated yet.");
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setSyntheticRowId() {
        String str = TAG;
        Log.d(str, "setSyntheticRowId()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor lastId = getLastId();
        if (lastId != null) {
            try {
                if (lastId.getCount() <= 0) {
                    Log.d(str, "setSyntheticRowId(): There is no data in events table.");
                    if (lastId != null) {
                        lastId.close();
                    }
                } else {
                    contentValues.put("row_id", Integer.valueOf(lastId.getInt(lastId.getColumnIndex(LAST_EVENT_ID_FIELD))));
                    Log.d(str, "setSyntheticRowId(): Marked event id = " + writableDatabase.insert("synthetic_key", null, contentValues));
                    if (lastId != null) {
                        lastId.close();
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
        } else {
            Log.d(str, "setSyntheticRowId(): There is no data in events table.");
            if (lastId != null) {
            }
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
        int iIntValue = contentValues.getAsInteger("plainEventsSize").intValue();
        contentValues.remove("plainEventsSize");
        writableDatabase.beginTransaction();
        try {
            try {
                if (writableDatabase.insert("compressed_events", null, contentValues) == -1) {
                    throw new SQLException("Transaction Failure. Not possible to insert compressed events.");
                }
                if (writableDatabase.delete("events", EVENTS_DELETE, new String[]{String.valueOf(iIntValue)}) == 0) {
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
        Cursor cursorQuery = getReadableDatabase().query("features_whitelist", new String[]{"feature", "enable_type"}, null, null, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
    }

    public long deleteFeaturesWhitelist(String[] strArr) {
        Log.d(TAG, "deleteFeaturesWhitelist()");
        if (strArr == null || strArr.length == 0) {
            return getWritableDatabase().delete("features_whitelist", null, null);
        }
        long jDeleteFeatureWhitelist = 0;
        for (String str : strArr) {
            jDeleteFeatureWhitelist += deleteFeatureWhitelist(str);
        }
        return jDeleteFeatureWhitelist;
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
        Cursor cursorQuery = getReadableDatabase().query("package_feature_b2c", new String[]{"packageName", "feature_name"}, (strArr == null || strArr.length <= 0) ? null : B2C_FEATURE_QUERY, strArr, null, null, null);
        if (cursorQuery != null && cursorQuery.getCount() > 0) {
            cursorQuery.moveToFirst();
        }
        return cursorQuery;
    }

    public long deleteB2CFeatures(String[] strArr) {
        Log.d(TAG, "deleteB2CFeatures()");
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (strArr == null || strArr.length == 0) {
            return 0L;
        }
        return writableDatabase.delete("package_feature_b2c", B2C_FEATURE_QUERY, strArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean hasCleanedEventRow() {
        boolean z;
        Cursor cursorQuery = getReadableDatabase().query("cleaned_events", new String[]{"id"}, null, null, null, null, null);
        if (cursorQuery != null) {
            try {
                z = cursorQuery.getCount() > 0;
            } finally {
            }
        }
        Log.d(TAG, "hasCleanedEventsRow(): " + z);
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return z;
    }

    public long updateCleanedEvent(ContentValues contentValues, SQLiteDatabase sQLiteDatabase, int i) {
        long jIntValue = -1;
        try {
            Cursor existingCleanEventValues = getExistingCleanEventValues();
            try {
                if (existingCleanEventValues == null) {
                    Log.d(TAG, "updateCleanedEvent(): No existing clean event");
                    if (existingCleanEventValues != null) {
                    }
                    return jIntValue;
                }
                contentValues.put("lastTimestamp", Long.valueOf(existingCleanEventValues.getLong(existingCleanEventValues.getColumnIndexOrThrow("lastTimestamp"))));
                contentValues.put("counter", Integer.valueOf((contentValues.getAsInteger("counter") != null ? contentValues.getAsInteger("counter").intValue() : 0) + existingCleanEventValues.getInt(existingCleanEventValues.getColumnIndexOrThrow("counter"))));
                contentValues.put("removedEvents", Integer.valueOf((contentValues.getAsInteger("removedEvents") != null ? contentValues.getAsInteger("removedEvents").intValue() : 0) + existingCleanEventValues.getInt(existingCleanEventValues.getColumnIndexOrThrow("removedEvents"))));
                contentValues.put("removedSize", Long.valueOf((contentValues.getAsLong("removedSize") != null ? contentValues.getAsLong("removedSize").longValue() : 0L) + existingCleanEventValues.getInt(existingCleanEventValues.getColumnIndexOrThrow("removedSize"))));
                if (i > existingCleanEventValues.getInt(existingCleanEventValues.getColumnIndex("reason"))) {
                    contentValues.put("reason", Integer.valueOf(i));
                }
                if (sQLiteDatabase.update("cleaned_events", contentValues, null, null) > 0) {
                    Log.d(TAG, "updateCleanedEvent(): Clean events updated");
                    jIntValue = contentValues.getAsInteger("id") != null ? contentValues.getAsInteger("id").intValue() : 0L;
                }
                if (existingCleanEventValues == null) {
                    return jIntValue;
                }
                existingCleanEventValues.close();
                return jIntValue;
            } finally {
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "updateCleanedEvent(): Failed to load ContentValues", e);
            return -1L;
        }
    }

    private Cursor getExistingCleanEventValues() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursorQuery = null;
        try {
            cursorQuery = readableDatabase.query("cleaned_events", null, null, null, null, null, null);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                cursorQuery.moveToFirst();
            }
            return cursorQuery;
        } catch (Throwable th) {
            Log.e(TAG, "getExistingCleanEventValues(): Failed: ", th);
            return cursorQuery;
        }
    }
}
