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
import java.util.Iterator;

/* loaded from: classes6.dex */
public class NotificationHistoryImageProvider extends ContentProvider {
    static final String AUTHORITY = "com.android.server.notification.provider";
    private static final int DATABASE_VERSION = 1;
    private static final long HISTORY_RETENTION_DAYS = 1;
    private static final long HISTORY_RETENTION_MS = 86400000;
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
            super(context, NotificationHistoryImageProvider.sDatabaseName, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            try {
                Slog.d(TAG, "Create DB");
                sQLiteDatabase.execSQL("CREATE TABLE NotiHistoryImgProvider (uri_id TEXT PRIMARY KEY, image BLOB,time DATETIME);");
            } catch (SQLException e) {
                Log.e(TAG, "Create DB Create failed", e);
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.w(TAG, "Upgrading database from version " + i + " to " + i2 + ", which will destroy all old data");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS suggestions");
            onCreate(sQLiteDatabase);
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        DatabaseHelper databaseHelper = this.mOpenHelper;
        try {
            if (databaseHelper == null) {
                Slog.d(TAG, "Error getting mOpenHelper in delete db");
                return -1;
            }
            try {
                SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
                if (writableDatabase == null) {
                    Slog.d(TAG, "Failed to delete db.");
                    return -1;
                }
                int delete = writableDatabase.delete(SETTINGS_TABLE, str, strArr);
                getContext().getContentResolver().notifyChange(uri, null);
                return delete;
            } catch (Exception e) {
                Slog.d(TAG, "Failed to delete due to unknown reason. " + e);
                return -1;
            }
        } catch (Throwable unused) {
            return -1;
        }
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        return "";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        addImageToCache(contentValues);
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mOpenHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in getCachedImage");
            return null;
        }
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        if (readableDatabase == null) {
            Slog.d(TAG, "Error getting DB in getCachedImage");
            return null;
        }
        Cursor query = readableDatabase.query(SETTINGS_TABLE, strArr, str, strArr2, null, null, str2, null);
        if (query != null) {
            query.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return query;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public long addImageToCache(String str, byte[] bArr, long j) {
        long j2 = -1;
        if (bArr == null || bArr.length == 0) {
            Slog.d(TAG, "addImageToCache image is null or empty.");
            return -1L;
        }
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in addImageToCache");
            return -1L;
        }
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        if (writableDatabase == null) {
            Slog.d(TAG, "Error getting DB in addImageToCache");
            return -1L;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_URI_ID, str);
            contentValues.put("image", bArr);
            contentValues.put("time", Long.valueOf(j));
            Slog.d(TAG, "uri= " + str + ", image= " + bArr.length + ", postedTime= " + j);
            long insert = writableDatabase.insert(SETTINGS_TABLE, null, contentValues);
            if (insert != -1) {
                return insert;
            }
            try {
                Slog.d(TAG, "Failed to cache image");
                return insert;
            } catch (Exception e) {
                e = e;
                j2 = insert;
                Slog.e(TAG, e.getMessage());
                return j2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private long addImageToCache(ContentValues contentValues) {
        Exception e;
        long j;
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in addImageToCache");
            return -1L;
        }
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        if (writableDatabase == null) {
            Slog.d(TAG, "Error getting DB in addImageToCache");
            return -1L;
        }
        try {
            Slog.d(TAG, "Added to cache image");
            j = writableDatabase.insert(SETTINGS_TABLE, null, contentValues);
            if (j != -1) {
                return j;
            }
            try {
                Slog.d(TAG, "Failed to cache image");
                return j;
            } catch (Exception e2) {
                e = e2;
                Slog.e(TAG, e.getMessage());
                return j;
            }
        } catch (Exception e3) {
            e = e3;
            j = -1;
        }
    }

    public boolean updatePostedTime(long j, ArrayList<String> arrayList) {
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in updatePostedTime");
            return false;
        }
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        if (writableDatabase == null) {
            Slog.d(TAG, "Error getting DB in updatePostedTime");
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", Long.valueOf(j));
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            writableDatabase.update(SETTINGS_TABLE, contentValues, "uri_id=?", new String[]{it.next()});
        }
        return true;
    }

    public boolean deleteRows(long j) {
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in deleteRows");
            return false;
        }
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        if (writableDatabase == null) {
            Slog.d(TAG, "Error getting DB in deleteRows");
            return false;
        }
        long j2 = j - 86400000;
        Slog.d(TAG, "deletedRows= " + writableDatabase.delete(SETTINGS_TABLE, "time<=" + j2, null) + ", deleteTime= " + j2);
        return true;
    }

    public boolean deleteRows(String str) {
        DatabaseHelper databaseHelper = this.mOpenHelper;
        if (databaseHelper == null) {
            Slog.d(TAG, "Error getting mOpenHelper in deleteRows");
            return false;
        }
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        if (writableDatabase == null) {
            Slog.d(TAG, "Error getting DB in deleteRows");
            return false;
        }
        writableDatabase.delete(SETTINGS_TABLE, "uri_id=?", new String[]{str});
        Slog.d(TAG, "deletedRows, uri= " + str);
        return true;
    }
}
