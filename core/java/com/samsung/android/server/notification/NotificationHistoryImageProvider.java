package com.samsung.android.server.notification;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.util.Slog;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class NotificationHistoryImageProvider extends ContentProvider {
  static final String AUTHORITY = "com.android.server.notification.provider";
  private static final int DATABASE_VERSION = 1;
  private static final int HISTORY_RETENTION_DAYS = 1;
  private static final int HISTORY_RETENTION_MS = 86400000;
  public static final String KEY_IMAGE = "image";
  public static final String KEY_TIME = "time";
  public static final String KEY_URI_ID = "uri_id";
  private static final String SETTINGS_TABLE = "NotiHistoryImgProvider";
  private static final String TAG = "NotificationHistoryImageProvider";
  private static final String sDatabaseName = "notihistoryimg.db";
  private static NotificationHistoryImageProvider sNotificationHistoryImageProvider;
  private DatabaseHelper mOpenHelper;
  static final String URL = "content://com.android.server.notification.provider";
  public static final Uri CONTENT_URI = Uri.parse(URL);

  public NotificationHistoryImageProvider() {
    sNotificationHistoryImageProvider = this;
  }

  public static NotificationHistoryImageProvider getInstance() {
    if (sNotificationHistoryImageProvider == null) {
      sNotificationHistoryImageProvider = new NotificationHistoryImageProvider();
    }
    return sNotificationHistoryImageProvider;
  }

  private static class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "NotiHistoryImg.DB";

    public DatabaseHelper(Context context) {
      super(
          context,
          NotificationHistoryImageProvider.sDatabaseName,
          (SQLiteDatabase.CursorFactory) null,
          1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
      try {
        Slog.m113d(TAG, "Create DB");
        db.execSQL(
            "CREATE TABLE NotiHistoryImgProvider (uri_id TEXT PRIMARY KEY, image BLOB,time"
                + " DATETIME);");
      } catch (SQLException ex) {
        Log.m97e(TAG, "Create DB Create failed", ex);
      }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.m102w(
          TAG,
          "Upgrading database from version "
              + oldVersion
              + " to "
              + newVersion
              + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS suggestions");
      onCreate(db);
    }
  }

  @Override // android.content.ContentProvider
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in delete db");
      return -1;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Failed to delete db.");
      return -1;
    }
    int count = db.delete(SETTINGS_TABLE, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  @Override // android.content.ContentProvider, android.content.ContentInterface
  public String getType(Uri uri) {
    return "";
  }

  @Override // android.content.ContentProvider
  public Uri insert(Uri uri, ContentValues values) {
    addImageToCache(values);
    return null;
  }

  @Override // android.content.ContentProvider
  public boolean onCreate() {
    this.mOpenHelper = new DatabaseHelper(getContext());
    return true;
  }

  @Override // android.content.ContentProvider
  public Cursor query(
      Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in getCachedImage");
      return null;
    }
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in getCachedImage");
      return null;
    }
    Cursor cursor =
        db.query(SETTINGS_TABLE, projection, selection, selectionArgs, null, null, sortOrder, null);
    if (cursor != null) {
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
    }
    return cursor;
  }

  @Override // android.content.ContentProvider
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public long addImageToCache(String uri, byte[] image, long postedTime) {
    if (image == null || image.length == 0) {
      Slog.m113d(TAG, "addImageToCache image is null or empty.");
      return -1L;
    }
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in addImageToCache");
      return -1L;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in addImageToCache");
      return -1L;
    }
    long rowid = -1;
    try {
      ContentValues values = new ContentValues();
      values.put(KEY_URI_ID, uri);
      values.put("image", image);
      values.put("time", Long.valueOf(postedTime));
      Slog.m113d(TAG, "uri= " + uri + ", image= " + image.length + ", postedTime= " + postedTime);
      rowid = db.insert(SETTINGS_TABLE, null, values);
      if (rowid == -1) {
        Slog.m113d(TAG, "Failed to cache image");
      }
    } catch (Exception e) {
      Slog.m115e(TAG, e.getMessage());
    }
    return rowid;
  }

  private long addImageToCache(ContentValues contentValues) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in addImageToCache");
      return -1L;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in addImageToCache");
      return -1L;
    }
    long rowid = -1;
    try {
      Slog.m113d(TAG, "Added to cache image");
      rowid = db.insert(SETTINGS_TABLE, null, contentValues);
      if (rowid == -1) {
        Slog.m113d(TAG, "Failed to cache image");
      }
    } catch (Exception e) {
      Slog.m115e(TAG, e.getMessage());
    }
    return rowid;
  }

  public boolean updatePostedTime(long time, ArrayList<String> list) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in updatePostedTime");
      return false;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in updatePostedTime");
      return false;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("time", Long.valueOf(time));
    for (String uri : list) {
      db.update(SETTINGS_TABLE, contentValues, "uri_id=?", new String[] {uri});
    }
    return true;
  }

  public boolean deleteRows(long time) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in deleteRows");
      return false;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in deleteRows");
      return false;
    }
    long deleteTime = time - 86400000;
    String whereClause = "time<=" + deleteTime;
    int deletedRows = db.delete(SETTINGS_TABLE, whereClause, null);
    Slog.m113d(TAG, "deletedRows= " + deletedRows + ", deleteTime= " + deleteTime);
    return true;
  }

  public boolean deleteRows(String uri) {
    DatabaseHelper databaseHelper = this.mOpenHelper;
    if (databaseHelper == null) {
      Slog.m113d(TAG, "Error getting mOpenHelper in deleteRows");
      return false;
    }
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    if (db == null) {
      Slog.m113d(TAG, "Error getting DB in deleteRows");
      return false;
    }
    db.delete(SETTINGS_TABLE, "uri_id=?", new String[] {uri});
    Slog.m113d(TAG, "deletedRows, uri= " + uri);
    return true;
  }
}
