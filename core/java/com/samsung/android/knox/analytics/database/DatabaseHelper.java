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
import com.samsung.android.knox.analytics.util.Log;
import java.io.File;

/* loaded from: classes5.dex */
class DatabaseHelper extends SQLiteOpenHelper {
  private static final String B2C_FEATURE_CREATE_TABLE =
      "CREATE TABLE package_feature_b2c ( packageName TEXT PRIMARY KEY, feature_name TEXT)";
  public static final String B2C_FEATURE_FIELD_FEATURE = "feature_name";
  public static final String B2C_FEATURE_FIELD_PACKAGE = "packageName";
  private static final String B2C_FEATURE_QUERY = "packageName = ?";
  private static final String B2C_FEATURE_TABLE = "package_feature_b2c";
  private static final String CLEANED_EVENTS_CREATE_TABLE =
      "CREATE TABLE cleaned_events ( id INTEGER PRIMARY KEY, vid INTEGER, data BLOB )";
  private static final String CLEANED_EVENTS_DELETE =
      "id IN (SELECT id FROM cleaned_events ORDER BY + id LIMIT ?)";
  static final String CLEANED_EVENTS_TABLE = "cleaned_events";
  private static final String COMPRESSED_EVENTS_ADD_BULK_COLUMN =
      "ALTER TABLE compressed_events ADD bulk INTEGER DEFAULT 1000";
  private static final String COMPRESSED_EVENTS_CREATE_TABLE =
      "CREATE TABLE compressed_events ( id INTEGER PRIMARY KEY, length INTEGER, original_length"
          + " INTEGER, bulk INTEGER DEFAULT 1000, content BLOB )";
  private static final String COMPRESSED_EVENTS_DELETE =
      "id IN (SELECT id FROM compressed_events ORDER BY + id LIMIT ?)";
  private static final String COMPRESSED_EVENTS_FIELD_BULK = "bulk";
  private static final String COMPRESSED_EVENTS_FIELD_CONTENT = "content";
  private static final String COMPRESSED_EVENTS_FIELD_ID = "id";
  private static final String COMPRESSED_EVENTS_FIELD_LENGTH = "length";
  private static final String COMPRESSED_EVENTS_FIELD_ORIGINAL_LENGTH = "original_length";
  private static final String COMPRESSED_EVENTS_KEY_PLAIN_EVENTS_SIZE = "plainEventsSize";
  private static final String COMPRESSED_EVENTS_TABLE = "compressed_events";
  private static final String DATABASE_NAME = "analytics.db";
  private static final int DATABASE_VERSION = 9;
  private static final String EVENTS_ADD_BULK_COLUMN =
      "ALTER TABLE events ADD bulk INTEGER DEFAULT 1";
  private static final String EVENTS_CREATE_TABLE =
      "CREATE TABLE events ( id INTEGER PRIMARY KEY, vid INTEGER, bulk INTEGER DEFAULT 1, data BLOB"
          + " )";
  private static final String EVENTS_DELETE = "id IN (SELECT id FROM events ORDER BY + id LIMIT ?)";
  private static final String EVENTS_DELETE_UP_TO_ID = "id <= ?";
  static final String EVENTS_FIELD_BULK = "bulk";
  static final String EVENTS_FIELD_DATA = "data";
  static final String EVENTS_FIELD_ID = "id";
  static final String EVENTS_FIELD_VID = "vid";
  static final String EVENTS_TABLE = "events";
  private static final String FEATURES_BLACKLIST_CREATE_TABLE =
      "CREATE TABLE feature_blocklist ( feature TEXT PRIMARY KEY, event TEXT NOT NULL)";
  private static final String FEATURES_BLACKLIST_FIELD_EVENT = "event";
  private static final String FEATURES_BLACKLIST_FIELD_FEATURE = "feature";
  private static final String FEATURES_BLACKLIST_LEGACY_TABLE = "features_blacklist";
  private static final String FEATURES_BLACKLIST_TABLE = "feature_blocklist";
  private static final String FEATURES_WHITELIST_CREATE_TABLE =
      "CREATE TABLE features_whitelist ( feature TEXT PRIMARY KEY, enable_type INTEGER)";
  private static final String FEATURES_WHITELIST_DELETE = "feature = ?";
  public static final String FEATURES_WHITELIST_FIELD_ENABLE = "enable_type";
  public static final String FEATURES_WHITELIST_FIELD_FEATURE = "feature";
  private static final String FEATURES_WHITELIST_TABLE = "features_whitelist";
  private static final String LAST_EVENT_ID_CREATE_TABLE =
      "CREATE TABLE internal_data ( last_event_id INTEGER )";
  private static final String LAST_EVENT_ID_FIELD = "last_event_id";
  private static final String LAST_EVENT_ID_TABLE = "internal_data";
  private static final String SYNTHETIC_KEY_CREATE_TABLE =
      "CREATE TABLE synthetic_key ( row_id INTEGER)";
  static final String SYNTHETIC_KEY_TABLE = "synthetic_key";
  private static final String SYNTHETIC_ROW_ID = "row_id";
  private static final String TAG = "[KnoxAnalytics] " + DatabaseHelper.class.getSimpleName();
  private static final String VERSIONING_CREATE_TABLE =
      "CREATE TABLE version ( id INTEGER PRIMARY KEY, data TEXT )";
  private static final String VERSIONING_DELETE_UP_TO_ID = "id <= ?";
  private static final String VERSIONING_FIELD_DATA = "data";
  private static final String VERSIONING_FIELD_ID = "id";
  private static final String VERSIONING_TABLE = "version";
  private final Context mContext;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 9);
    Log.m286d(TAG, "constructor()");
    this.mContext = context;
  }

  @Override // android.database.sqlite.SQLiteOpenHelper
  public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    Cursor c = db.rawQuery("PRAGMA journal_mode = OFF;", null);
    if (c != null) {
      c.close();
    }
  }

  @Override // android.database.sqlite.SQLiteOpenHelper
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(EVENTS_CREATE_TABLE);
    db.execSQL(VERSIONING_CREATE_TABLE);
    db.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
    db.execSQL(LAST_EVENT_ID_CREATE_TABLE);
    db.execSQL(CLEANED_EVENTS_CREATE_TABLE);
    db.execSQL(SYNTHETIC_KEY_CREATE_TABLE);
    db.execSQL(COMPRESSED_EVENTS_CREATE_TABLE);
    db.execSQL(FEATURES_WHITELIST_CREATE_TABLE);
    db.execSQL(B2C_FEATURE_CREATE_TABLE);
    putLastIdDefaultValue(db);
  }

  @Override // android.database.sqlite.SQLiteOpenHelper
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.m286d(
        TAG,
        String.format(
            "oldVersion=%d, newVersion=%d",
            Integer.valueOf(oldVersion), Integer.valueOf(newVersion)));
    if (oldVersion < 2) {
      db.execSQL("DROP TABLE IF EXISTS events");
      db.execSQL("DROP TABLE IF EXISTS version");
      db.execSQL("DROP TABLE IF EXISTS feature_blocklist");
      db.execSQL(EVENTS_CREATE_TABLE);
      db.execSQL(VERSIONING_CREATE_TABLE);
      db.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
    }
    if (oldVersion < 3) {
      db.execSQL(LAST_EVENT_ID_CREATE_TABLE);
      putLastIdDefaultValue(db);
    }
    if (oldVersion < 4) {
      db.execSQL(CLEANED_EVENTS_CREATE_TABLE);
    }
    if (oldVersion < 5) {
      db.execSQL(SYNTHETIC_KEY_CREATE_TABLE);
    }
    if (oldVersion < 6) {
      db.execSQL(COMPRESSED_EVENTS_CREATE_TABLE);
    }
    if (oldVersion < 7) {
      db.execSQL(FEATURES_WHITELIST_CREATE_TABLE);
      db.execSQL(B2C_FEATURE_CREATE_TABLE);
    }
    if (oldVersion < 8) {
      db.execSQL(EVENTS_ADD_BULK_COLUMN);
      db.execSQL(COMPRESSED_EVENTS_ADD_BULK_COLUMN);
    }
    if (oldVersion < 9) {
      db.execSQL("DROP TABLE IF EXISTS features_blacklist");
      db.execSQL(FEATURES_BLACKLIST_CREATE_TABLE);
    }
  }

  private boolean isContentValuesValid(ContentValues contentValues, int type) {
    if (!contentValues.containsKey("id")
        || contentValues.getAsInteger("id") == null
        || !contentValues.containsKey("vid")
        || contentValues.getAsInteger("vid") == null
        || !contentValues.containsKey("data")
        || contentValues.getAsString("data") == null
        || contentValues.getAsString("data").isEmpty()) {
      Log.m287e(TAG, "Wrong fields! Missing id/vid/data");
      return false;
    }
    if (type == 0) {
      if (contentValues.size() != 3) {
        Log.m287e(TAG, "Wrong fields! Invalid clean event");
        return false;
      }
    } else if (type == 1) {
      if (!contentValues.containsKey("bulk")
          || contentValues.getAsInteger("bulk") == null
          || contentValues.size() != 4) {
        Log.m287e(TAG, "Wrong fields! Invalid event");
        return false;
      }
    } else {
      Log.m287e(TAG, "Unknown table");
      return false;
    }
    return true;
  }

  public long addEvent(ContentValues contentValues, int type) {
    if (!isContentValuesValid(contentValues, type)) {
      Log.m287e(TAG, "addEvent() : Invalid content values");
      return -1L;
    }
    String str = TAG;
    Log.m286d(str, "addEvent()");
    SQLiteDatabase db = getWritableDatabase();
    String[] content = getTableAndWhereClauseFromType(type);
    if (content == null || content.length <= 0 || content[0] == null || content[0].isEmpty()) {
      Log.m286d(str, "addEvent(): Wrong log type");
      return -1L;
    }
    int eventsAmount = 1;
    if (type == 1) {
      eventsAmount = contentValues.getAsInteger("bulk").intValue();
    }
    long lastId = db.insert(content[0], null, contentValues);
    if (lastId == -1) {
      Log.m287e(str, "addEvent(): Couldn't add event");
    } else {
      updateLastId((eventsAmount + lastId) - 1);
    }
    return lastId;
  }

  private String[] getTableAndWhereClauseFromType(int type) {
    String[] content = new String[2];
    switch (type) {
      case 0:
        content[0] = "cleaned_events";
        content[1] = CLEANED_EVENTS_DELETE;
        return content;
      case 1:
        content[0] = "events";
        content[1] = EVENTS_DELETE;
        return content;
      default:
        Log.m286d(TAG, "getTableAndWhereClauseFromType(): Unknown table");
        return content;
    }
  }

  public Cursor getEventChunk(Integer size) {
    Log.m286d(TAG, "getEventChunk()");
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query(
            "events",
            new String[] {"id", "vid", "bulk", "data"},
            null,
            null,
            null,
            null,
            null,
            null);
    if (size == null) {
      return cursor;
    }
    MatrixCursor matrixCursor = new MatrixCursor(new String[] {"id", "vid", "bulk", "data"}, 1);
    int amountOfEvents = 0;
    if (cursor != null && cursor.getCount() > 0) {
      int idColumnIndex = cursor.getColumnIndex("id");
      int vidColumnIndex = cursor.getColumnIndex("vid");
      int bulkColumnIndex = cursor.getColumnIndex("bulk");
      int dataColumnIndex = cursor.getColumnIndex("data");
      while (cursor.moveToNext()) {
        int eventsToAdd = cursor.getInt(bulkColumnIndex);
        if (amountOfEvents + eventsToAdd > size.intValue()) {
          break;
        }
        matrixCursor.addRow(
            new Object[] {
              Long.valueOf(cursor.getLong(idColumnIndex)),
              Integer.valueOf(cursor.getInt(vidColumnIndex)),
              Integer.valueOf(eventsToAdd),
              cursor.getBlob(dataColumnIndex)
            });
        amountOfEvents += eventsToAdd;
      }
    }
    if (cursor != null) {
      cursor.close();
    }
    return matrixCursor;
  }

  public Cursor getLastId() {
    Log.m286d(TAG, "getLastId()");
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query(
            LAST_EVENT_ID_TABLE, new String[] {LAST_EVENT_ID_FIELD}, null, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  private void updateLastId(long id) {
    Log.m286d(TAG, "updateLastId(" + id + NavigationBarInflaterView.KEY_CODE_END);
    SQLiteDatabase db = getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(LAST_EVENT_ID_FIELD, Long.valueOf(id));
    db.update(LAST_EVENT_ID_TABLE, cv, null, null);
  }

  private void putLastIdDefaultValue(SQLiteDatabase db) {
    String str = TAG;
    Log.m286d(str, "putLastIdDefaultValue()");
    ContentValues cv = new ContentValues();
    cv.put(LAST_EVENT_ID_FIELD, (Integer) (-1));
    long id = db.insert(LAST_EVENT_ID_TABLE, null, cv);
    if (id == -1) {
      Log.m287e(str, "putLastIdDefaultValue(): Error");
    }
  }

  public Cursor getEventCount() {
    Log.m286d(TAG, "getEventCount()");
    MatrixCursor cursor = new MatrixCursor(new String[] {Contract.Events.Projection.COUNT_ONLY}, 1);
    cursor.addRow(new Object[] {Long.valueOf(getEventCountValue())});
    return cursor;
  }

  Cursor getCompressedEventChunk(Integer limit) {
    String sLimit;
    String str = TAG;
    Log.m286d(str, "getCompressedEventChunk()");
    SQLiteDatabase db = getReadableDatabase();
    if (limit == null) {
      sLimit = null;
    } else {
      String sLimit2 = String.valueOf(limit);
      sLimit = sLimit2;
    }
    Cursor cursor = db.query("compressed_events", null, null, null, null, null, "id ASC", sLimit);
    if (cursor == null || cursor.getCount() <= 0) {
      Log.m286d(str, "getCompressedEventChunk(): There is no compressed data");
      if (cursor != null) {
        cursor.close();
        return null;
      }
      return null;
    }
    return cursor;
  }

  public long getEventCountValue() {
    int eventCount = 0;
    Cursor cursor = null;
    try {
      cursor = getEventCountCursor();
      if (cursor != null && cursor.getCount() > 0) {
        while (cursor.moveToNext()) {
          eventCount += cursor.getInt(0);
        }
        return eventCount;
      }
      long j = 0;
      if (cursor != null) {
        cursor.close();
      }
      return j;
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  public Cursor getEventCountCursor() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query("events", new String[] {"bulk"}, null, null, null, null, null, null);
    if (cursor == null || cursor.getCount() <= 0) {
      if (cursor != null) {
        cursor.close();
        return null;
      }
      return null;
    }
    return cursor;
  }

  public long getCompressedEventCountValue() {
    return DatabaseUtils.queryNumEntries(getReadableDatabase(), "compressed_events");
  }

  public int getTotalCompressedEvent(Integer limit) {
    Log.m286d(TAG, "getTotalCompressedEvent()");
    if (limit == null || limit.intValue() <= 0) {
      return 0;
    }
    Cursor cursor = getTotalCompressedEventCursor(String.valueOf(limit));
    int totalCompressedEvents = 0;
    if (cursor == null) {
      return 0;
    }
    while (cursor.moveToNext()) {
      try {
        totalCompressedEvents += cursor.getInt(0);
      } finally {
        cursor.close();
      }
    }
    return totalCompressedEvents;
  }

  public Cursor getTotalCompressedEventCursor() {
    return getTotalCompressedEventCursor(null);
  }

  public Cursor getTotalCompressedEventCursor(String limit) {
    String str = TAG;
    Log.m286d(
        str, "getTotalCompressedEventCursor(" + limit + NavigationBarInflaterView.KEY_CODE_END);
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query("compressed_events", new String[] {"bulk"}, null, null, null, null, null, limit);
    if (cursor == null || cursor.getCount() <= 0) {
      Log.m286d(str, "getTotalCompressedEventCursor(): There is no compressed events");
      if (cursor != null) {
        cursor.close();
        return null;
      }
      return null;
    }
    return cursor;
  }

  public long deleteEventChunk(long size, int type) {
    String str = TAG;
    Log.m286d(str, "deleteEventChunk(" + size + NavigationBarInflaterView.KEY_CODE_END);
    if (size < 1) {
      Log.m287e(str, "deleteEventChunk(): invalid number");
      return 0L;
    }
    String[] content = getTableAndWhereClauseFromType(type);
    if (content == null
        || content.length <= 0
        || content[0] == null
        || content[1] == null
        || content[0].isEmpty()
        || content[1].isEmpty()) {
      Log.m286d(str, "deleteEventChunk(): Wrong log type");
      return -1L;
    }
    SQLiteDatabase db = getWritableDatabase();
    return db.delete(content[0], content[1], new String[] {String.valueOf(size)});
  }

  public void deleteEventsUpToSyntheticId() {
    SQLiteDatabase db = getWritableDatabase();
    int synId = getSyntheticRowId();
    if (synId == -1) {
      Log.m286d(TAG, "deleteEventsUpToSyntheticId(): No legacy content");
      return;
    }
    int rows = db.delete("events", "id <= ?", new String[] {String.valueOf(synId)});
    if (rows > 1) {
      Log.m286d(
          TAG,
          "deleteEventsUpToSyntheticId(): "
              + rows
              + " events, up to "
              + synId
              + "have been deleted");
      ContentValues cv = new ContentValues();
      cv.put("row_id", "-1");
      db.update("synthetic_key", cv, null, null);
    }
  }

  public long deleteAllEvents() {
    Log.m286d(TAG, "deleteAllEvents()");
    SQLiteDatabase db = getWritableDatabase();
    return db.delete("events", null, null);
  }

  public long deleteUpTo(long id) {
    Log.m286d(TAG, "deleteUpTo(" + id + NavigationBarInflaterView.KEY_CODE_END);
    SQLiteDatabase db = getWritableDatabase();
    return db.delete("events", "id <= ?", new String[] {String.valueOf(id)});
  }

  public long getCurrentDatabaseSizeInBytes() {
    Log.m286d(TAG, "getCurrentDatabaseSizeInBytes()");
    File dbFile = this.mContext.getDatabasePath(DATABASE_NAME);
    return dbFile.length();
  }

  public Cursor getCurrentDatabaseSizeCursor() {
    Log.m286d(TAG, "getCurrentDatabaseSizeCursor()");
    MatrixCursor cursor = new MatrixCursor(new String[] {"databaseSize"}, 1);
    cursor.addRow(new Object[] {Long.valueOf(getCurrentDatabaseSizeInBytes())});
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  private Cursor getCurrentVersioningId() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query("version", new String[] {"id"}, null, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public int getCurrentVersioningIdInternal() {
    Cursor cursor = getCurrentVersioningId();
    int id = -1;
    if (cursor != null) {
      try {
        if (cursor.getCount() > 0) {
          id = cursor.getInt(cursor.getColumnIndex("id"));
        }
      } catch (Throwable th) {
        if (cursor != null) {
          try {
            cursor.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    }
    if (cursor != null) {
      cursor.close();
    }
    return id;
  }

  public Cursor getCleanedEventsCursor(Integer size) {
    Log.m286d(TAG, "getCleanedEventsCursor()");
    String limitParam = null;
    if (size != null) {
      limitParam = String.valueOf(size);
    }
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query(
            "cleaned_events",
            new String[] {"id", "vid", "data"},
            null,
            null,
            null,
            null,
            null,
            limitParam);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public Cursor getVersioningBlob() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.query("version", new String[] {"data", "id"}, null, null, null, null, null);
    return cursor;
  }

  public int addVersioningBlob(ContentValues contentValues) {
    String str = TAG;
    Log.m286d(str, "addVersioningBlob()");
    if (contentValues == null
        || !contentValues.containsKey("id")
        || !contentValues.containsKey("data")
        || !contentValues.containsKey(Contract.Versioning.AUX_FIELD_EVENT_ID)
        || contentValues.size() != 3) {
      Log.m287e(str, "addVersioningBlob(): wrong fields!");
      return -1;
    }
    Integer newVersioningId = contentValues.getAsInteger("id");
    if (newVersioningId == null) {
      Log.m287e(str, "addVersioningBlob(): versioning id is null!");
      return -1;
    }
    Long eventId = contentValues.getAsLong(Contract.Versioning.AUX_FIELD_EVENT_ID);
    long id = -1;
    if (eventId != null) {
      contentValues.remove(Contract.Versioning.AUX_FIELD_EVENT_ID);
      SQLiteDatabase db = getWritableDatabase();
      id = db.insert("version", null, contentValues);
    }
    if (eventId == null || id == -1) {
      Log.m287e(str, "addVersioningBlob(): error");
      return -1;
    }
    updateLastId(eventId.longValue());
    return newVersioningId.intValue();
  }

  public long deleteFromVersion(long versioningBlobId) {
    Log.m286d(TAG, "deleteFromVersion()");
    SQLiteDatabase db = getWritableDatabase();
    return db.delete("version", "id <= ?", new String[] {String.valueOf(versioningBlobId)});
  }

  public long addFeaturesBlacklist(ContentValues contentValues) {
    String str = TAG;
    Log.m287e(str, "addFeaturesBlacklist()");
    if (!contentValues.containsKey("feature")
        || TextUtils.isEmpty(contentValues.getAsString("feature"))
        || !contentValues.containsKey("event")
        || TextUtils.isEmpty(contentValues.getAsString("event"))
        || contentValues.size() != 2) {
      Log.m287e(str, "addFeaturesBlacklist(): invalid fields!");
      return -1L;
    }
    SQLiteDatabase db = getWritableDatabase();
    return db.replace("feature_blocklist", null, contentValues);
  }

  public Cursor getFeaturesBlacklist() {
    Log.m286d(TAG, "getFeaturesBlacklist()");
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query(
            "feature_blocklist", new String[] {"feature", "event"}, null, null, null, null, null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public long deleteFeaturesBlacklist() {
    Log.m286d(TAG, "deleteFeaturesBlacklist()");
    SQLiteDatabase db = getWritableDatabase();
    return db.delete("feature_blocklist", null, null);
  }

  int getSyntheticRowId() {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query("synthetic_key", new String[] {"row_id"}, null, null, null, null, null);
    if (cursor != null) {
      try {
        if (cursor.getCount() > 0) {
          cursor.moveToFirst();
          int synRowId = cursor.getInt(cursor.getColumnIndex("row_id"));
          Log.m286d(TAG, "getSyntheticRowId(): " + synRowId);
          if (cursor != null) {
            cursor.close();
          }
          return synRowId;
        }
      } catch (Throwable th) {
        if (cursor != null) {
          try {
            cursor.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    }
    Log.m286d(TAG, "getSyntheticRowId(): Key is deleted or it is not generated yet.");
    if (cursor != null) {
      cursor.close();
      return -1;
    }
    return -1;
  }

  public void setSyntheticRowId() {
    String str = TAG;
    Log.m286d(str, "setSyntheticRowId()");
    SQLiteDatabase db = getWritableDatabase();
    ContentValues cv = new ContentValues();
    Cursor cursor = getLastId();
    if (cursor != null) {
      try {
        if (cursor.getCount() > 0) {
          cv.put(
              "row_id", Integer.valueOf(cursor.getInt(cursor.getColumnIndex(LAST_EVENT_ID_FIELD))));
          long id = db.insert("synthetic_key", null, cv);
          Log.m286d(str, "setSyntheticRowId(): Marked event id = " + id);
          if (cursor != null) {
            cursor.close();
            return;
          }
          return;
        }
      } catch (Throwable th) {
        if (cursor != null) {
          try {
            cursor.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    }
    Log.m286d(str, "setSyntheticRowId(): There is no data in events table.");
    if (cursor != null) {
      cursor.close();
    }
  }

  long deleteCompressedEventChunk(long size) {
    String str = TAG;
    Log.m286d(str, "deleteCompressedEventChunk(" + size + NavigationBarInflaterView.KEY_CODE_END);
    if (size <= 0) {
      Log.m287e(str, "deleteCompressedEventChunk(): invalid number");
      return 0L;
    }
    SQLiteDatabase db = getWritableDatabase();
    return db.delete(
        "compressed_events", COMPRESSED_EVENTS_DELETE, new String[] {String.valueOf(size)});
  }

  boolean performCompressedEventsTransaction(ContentValues values) {
    boolean result;
    String str = TAG;
    Log.m286d(str, "performCompressedEventsTransaction()");
    if (!values.containsKey("content")
        || TextUtils.isEmpty(values.getAsString("content"))
        || !values.containsKey("length")
        || values.getAsInteger("length") == null
        || !values.containsKey("original_length")
        || values.getAsInteger("original_length") == null
        || !values.containsKey("plainEventsSize")
        || values.getAsInteger("plainEventsSize") == null
        || !values.containsKey("bulk")
        || values.getAsInteger("bulk") == null
        || values.size() != 5) {
      Log.m287e(str, "performCompressedEventsTransaction(): wrong fields!");
      return false;
    }
    SQLiteDatabase db = getWritableDatabase();
    int plainEventsSize = values.getAsInteger("plainEventsSize").intValue();
    values.remove("plainEventsSize");
    db.beginTransaction();
    try {
      try {
      } catch (SQLException e) {
        Log.m288e(TAG, "performCompressedEventsTransaction(): ", e);
        result = false;
      }
      if (db.insert("compressed_events", null, values) == -1) {
        throw new SQLException("Transaction Failure. Not possible to insert compressed events.");
      }
      if (db.delete("events", EVENTS_DELETE, new String[] {String.valueOf(plainEventsSize)}) == 0) {
        throw new SQLException("Transaction Failure. Not possible to delete plain-text events.");
      }
      db.setTransactionSuccessful();
      result = true;
      return result;
    } finally {
      db.endTransaction();
    }
  }

  public long addFeaturesWhitelist(ContentValues contentValues) {
    String str = TAG;
    Log.m286d(str, "addFeaturesWhitelist()");
    if (!contentValues.containsKey("feature")
        || contentValues.getAsString("feature") == null
        || contentValues.getAsString("feature").isEmpty()
        || contentValues.size() != 2) {
      Log.m287e(str, "addFeaturesWhitelist(): missing feature field!");
      return -1L;
    }
    if (!contentValues.containsKey("enable_type")
        || contentValues.getAsInteger("enable_type") == null) {
      contentValues.put("enable_type", (Integer) 1);
    }
    SQLiteDatabase db = getWritableDatabase();
    return db.replace("features_whitelist", null, contentValues);
  }

  public Cursor getFeaturesWhitelist() {
    Log.m286d(TAG, "getFeaturesWhitelist()");
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.query(
            "features_whitelist",
            new String[] {"feature", "enable_type"},
            null,
            null,
            null,
            null,
            null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public long deleteFeaturesWhitelist(String[] features) {
    Log.m286d(TAG, "deleteFeaturesWhitelist()");
    long rows = 0;
    if (features == null || features.length == 0) {
      SQLiteDatabase db = getWritableDatabase();
      long rows2 = db.delete("features_whitelist", null, null);
      return rows2;
    }
    for (String feature : features) {
      rows += deleteFeatureWhitelist(feature);
    }
    return rows;
  }

  public long deleteFeatureWhitelist(String feature) {
    Log.m286d(TAG, "deleteFeatureWhitelist()");
    SQLiteDatabase db = getWritableDatabase();
    if (feature == null || feature.isEmpty()) {
      return 0L;
    }
    return db.delete("features_whitelist", FEATURES_WHITELIST_DELETE, new String[] {feature});
  }

  public long addB2CFeatures(ContentValues contentValues) {
    Log.m287e(TAG, "addB2CFeatures()");
    if (!contentValues.containsKey("packageName")
        || !contentValues.containsKey("feature_name")
        || contentValues.getAsString("packageName") == null
        || contentValues.getAsString("feature_name") == null) {
      return -1L;
    }
    SQLiteDatabase db = getWritableDatabase();
    return db.replace("package_feature_b2c", null, contentValues);
  }

  public Cursor getB2CFeatures(String[] packageName) {
    Log.m286d(TAG, "getB2CFeatures()");
    SQLiteDatabase db = getReadableDatabase();
    String selection = null;
    if (packageName != null && packageName.length > 0) {
      selection = B2C_FEATURE_QUERY;
    }
    Cursor cursor =
        db.query(
            "package_feature_b2c",
            new String[] {"packageName", "feature_name"},
            selection,
            packageName,
            null,
            null,
            null);
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
    }
    return cursor;
  }

  public long deleteB2CFeatures(String[] packageName) {
    Log.m286d(TAG, "deleteB2CFeatures()");
    SQLiteDatabase db = getWritableDatabase();
    if (packageName == null || packageName.length == 0) {
      return 0L;
    }
    return db.delete("package_feature_b2c", B2C_FEATURE_QUERY, packageName);
  }
}
