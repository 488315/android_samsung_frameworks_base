package com.samsung.android.content.clipboard.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.mms.ContentType;
import java.io.FileNotFoundException;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class SemImageClipDataProvider extends ContentProvider {
    static final String AUTHORITY = "com.samsung.android.content.clipboard";
    static final String CREATE_TABLE = " CREATE TABLE ClipboardImageTable (id INTEGER PRIMARY KEY AUTOINCREMENT,  _data TEXT NOT NULL);";
    public static final String DATA = "_data";
    static final String DATABASE_NAME = "clipboardimage.db";
    static final int DATABASE_VERSION = 1;
    static final int FILEPATH = 1;
    static final int FILEPATH_ID = 2;
    public static final String ID = "id";
    private static HashMap<String, String> ImageMap = null;
    static final String TABLE_NAME = "ClipboardImageTable";
    private static final String TAG = "SemImageClipDataProvider";
    static final UriMatcher uriMatcher;
    private SQLiteDatabase database;
    DBHelper dbHelper;
    static final String URL = "content://com.samsung.android.content.clipboard/images";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    static {
        UriMatcher uriMatcher2 = new UriMatcher(-1);
        uriMatcher = uriMatcher2;
        uriMatcher2.addURI(AUTHORITY, "images", 1);
        uriMatcher2.addURI(AUTHORITY, "images/#", 2);
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, SemImageClipDataProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
            sQLiteDatabase.execSQL(SemImageClipDataProvider.CREATE_TABLE);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws SQLException {
            Log.w(DBHelper.class.getName(), "Upgrading database from version " + i + " to " + i2 + ". Old data will be destroyed");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ClipboardImageTable");
            onCreate(sQLiteDatabase);
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        DBHelper dBHelper = new DBHelper(getContext());
        this.dbHelper = dBHelper;
        SQLiteDatabase writableDatabase = dBHelper.getWritableDatabase();
        this.database = writableDatabase;
        return writableDatabase != null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(TABLE_NAME);
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            sQLiteQueryBuilder.setProjectionMap(ImageMap);
        } else if (iMatch == 2) {
            sQLiteQueryBuilder.appendWhere("id=" + uri.getLastPathSegment());
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursorQuery = sQLiteQueryBuilder.query(this.database, strArr, str, strArr2, null, null, "_data");
        if (cursorQuery != null) {
            cursorQuery.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursorQuery;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when insert in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return null;
        }
        long jReplace = this.database.replace(TABLE_NAME, "", contentValues);
        if (jReplace > 0) {
            Uri uriWithAppendedId = ContentUris.withAppendedId(CONTENT_URI, jReplace);
            getContext().getContentResolver().notifyChange(uriWithAppendedId, null);
            return uriWithAppendedId;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int iUpdate;
        String str2;
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when update in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return 0;
        }
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            iUpdate = this.database.update(TABLE_NAME, contentValues, str, strArr);
        } else if (iMatch == 2) {
            SQLiteDatabase sQLiteDatabase = this.database;
            StringBuilder sb = new StringBuilder("id = ");
            sb.append(uri.getLastPathSegment());
            if (TextUtils.isEmpty(str)) {
                str2 = "";
            } else {
                str2 = " AND (" + str + ')';
            }
            sb.append(str2);
            iUpdate = sQLiteDatabase.update(TABLE_NAME, contentValues, sb.toString(), strArr);
        } else {
            throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return iUpdate;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        int iDelete;
        String str2;
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when delete in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return 0;
        }
        int iMatch = uriMatcher.match(uri);
        if (iMatch == 1) {
            iDelete = this.database.delete(TABLE_NAME, str, strArr);
        } else if (iMatch == 2) {
            String lastPathSegment = uri.getLastPathSegment();
            SQLiteDatabase sQLiteDatabase = this.database;
            StringBuilder sb = new StringBuilder("id = ");
            sb.append(lastPathSegment);
            if (TextUtils.isEmpty(str)) {
                str2 = "";
            } else {
                str2 = " AND (" + str + ')';
            }
            sb.append(str2);
            iDelete = sQLiteDatabase.delete(TABLE_NAME, sb.toString(), strArr);
        } else {
            throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return iDelete;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        return ContentType.IMAGE_JPEG;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        return openFileHelper(uri, str);
    }
}
