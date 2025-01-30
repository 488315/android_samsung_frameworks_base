package com.samsung.android.wifi.p047ap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class SemWifiApContentProvider extends ContentProvider {
  static final String CREATE_DB_TABLE =
      " CREATE TABLE SemWifiApContentProvider (_id INTEGER PRIMARY KEY AUTOINCREMENT,  name TEXT"
          + " NOT NULL,  value TEXT NOT NULL);";
  static final String DATABASE_NAME = "SemWifiApContentProvider.db";
  static final int DATABASE_VERSION = 1;
  static final String NAME = "name";
  static final String PROVIDER_NAME = "com.samsung.android.wifi.softap";
  static final int SOFTAPINFO = 1;
  static final int SOFTAPINFO_ID = 2;
  private static HashMap<String, String> SOFTAPINFO_PROJECTION_MAP = null;
  static final String SOFTAPINFO_TABLE_NAME = "SemWifiApContentProvider";
  private static final String TAG = "SemWifiApContentProvider";
  static final String VALUE = "value";
  static final String _ID = "_id";
  static final UriMatcher uriMatcher;

  /* renamed from: db */
  private SQLiteDatabase f3094db;
  static final String URL = "content://com.samsung.android.wifi.softap/softapInfo";
  static final Uri CONTENT_URI = Uri.parse(URL);

  static {
    UriMatcher uriMatcher2 = new UriMatcher(-1);
    uriMatcher = uriMatcher2;
    uriMatcher2.addURI(PROVIDER_NAME, "softapInfo", 1);
    uriMatcher2.addURI(PROVIDER_NAME, "softapInfo/#", 2);
  }

  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
      super(
          context, SemWifiApContentProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(SemWifiApContentProvider.CREATE_DB_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS SemWifiApContentProvider");
      onCreate(db);
    }
  }

  @Override // android.content.ContentProvider, android.content.ContentInterface
  public String getType(Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override // android.content.ContentProvider
  public Uri insert(Uri uri, ContentValues values) {
    long rowID = this.f3094db.insert("SemWifiApContentProvider", "", values);
    if (rowID > 0) {
      Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
      Log.m98i("SemWifiApContentProvider", "inserted" + _uri);
      return _uri;
    }
    Log.m96e("SemWifiApContentProvider", "Could not add" + uri);
    return null;
  }

  @Override // android.content.ContentProvider
  public boolean onCreate() {
    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
    Log.m94d("SemWifiApContentProvider", "OnCreate");
    SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
    this.f3094db = writableDatabase;
    return writableDatabase != null;
  }

  @Override // android.content.ContentProvider
  public Cursor query(
      Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    qb.setTables("SemWifiApContentProvider");
    switch (uriMatcher.match(uri)) {
      case 1:
        qb.setProjectionMap(SOFTAPINFO_PROJECTION_MAP);
        break;
      case 2:
        qb.appendWhere("_id=" + uri.getPathSegments().get(1));
        break;
    }
    Cursor c = qb.query(this.f3094db, projection, selection, selectionArgs, null, null, sortOrder);
    return c;
  }

  @Override // android.content.ContentProvider
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    switch (uriMatcher.match(uri)) {
      case 1:
        int count = this.f3094db.delete("SemWifiApContentProvider", selection, selectionArgs);
        return count;
      case 2:
        String id = uri.getPathSegments().get(1);
        int count2 =
            this.f3094db.delete(
                "SemWifiApContentProvider",
                "_id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                selectionArgs);
        return count2;
      default:
        Log.m94d("SemWifiApContentProvider", "delete Unknown URI " + uri);
        return 0;
    }
  }

  @Override // android.content.ContentProvider
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    int count;
    switch (uriMatcher.match(uri)) {
      case 1:
        count = this.f3094db.update("SemWifiApContentProvider", values, selection, selectionArgs);
        break;
      case 2:
        count =
            this.f3094db.update(
                "SemWifiApContentProvider",
                values,
                "_id = "
                    + uri.getPathSegments().get(1)
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
                selectionArgs);
        break;
      default:
        Log.m96e("SemWifiApContentProvider", "Could not update" + uri);
        return 0;
    }
    Log.m98i("SemWifiApContentProvider", "updated:" + uri);
    return count;
  }

  public static void insert(Context mContext, String key, String val) {
    ContentValues values = new ContentValues();
    if (val == null) {
      val = "";
    }
    values.put("name", key);
    values.put("value", val);
    if (isKeypresent(mContext, key)) {
      mContext.getContentResolver().update(CONTENT_URI, values, null, null);
    } else {
      mContext.getContentResolver().insert(CONTENT_URI, values);
    }
  }

  public static String get(Context mContext, String key) {
    String[] selectionArgs = {key};
    Cursor c =
        mContext
            .getContentResolver()
            .query(
                Uri.parse("content://com.samsung.android.wifi.softap"),
                null,
                "name = ?",
                selectionArgs,
                null);
    String returnValue = "";
    if (c != null) {
      try {
        if (c.moveToFirst()) {
          returnValue = c.getString(c.getColumnIndex("value"));
        }
      } finally {
        c.close();
      }
    }
    return returnValue;
  }

  private static boolean isKeypresent(Context mContext, String key) {
    String[] selectionArgs = {key};
    Cursor c =
        mContext
            .getContentResolver()
            .query(
                Uri.parse("content://com.samsung.android.wifi.softap"),
                null,
                "name = ?",
                selectionArgs,
                null);
    if (c == null) {
      return false;
    }
    try {
      boolean ret = c.moveToFirst();
      return ret;
    } finally {
      c.close();
    }
  }
}
