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

/* loaded from: classes5.dex */
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
    private SQLiteDatabase database;
    DBHelper dbHelper;
    static final String URL = "content://com.samsung.android.content.clipboard/images";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final UriMatcher uriMatcher = new UriMatcher(-1);

    static {
        uriMatcher.addURI(AUTHORITY, "images", 1);
        uriMatcher.addURI(AUTHORITY, "images/#", 2);
    }

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, SemImageClipDataProvider.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SemImageClipDataProvider.CREATE_TABLE);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS ClipboardImageTable");
            onCreate(db);
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        this.dbHelper = new DBHelper(context);
        this.database = this.dbHelper.getWritableDatabase();
        return this.database != null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case 1:
                queryBuilder.setProjectionMap(ImageMap);
                break;
            case 2:
                queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(this.database, projection, selection, selectionArgs, null, null, "_data");
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when insert in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return null;
        }
        long row = this.database.replace(TABLE_NAME, "", values);
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when update in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return 0;
        }
        switch (uriMatcher.match(uri)) {
            case 1:
                count = this.database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case 2:
                count = this.database.update(TABLE_NAME, values, "id = " + uri.getLastPathSegment() + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;
        if (Binder.getCallingUid() != 1000) {
            Log.e(TAG, "SecurityException when delete in SemClipboardProvider. blocked package : " + getContext().getPackageManager().getNameForUid(Binder.getCallingUid()));
            return 0;
        }
        switch (uriMatcher.match(uri)) {
            case 1:
                count = this.database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case 2:
                String id = uri.getLastPathSegment();
                count = this.database.delete(TABLE_NAME, "id = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        return ContentType.IMAGE_JPEG;
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        return openFileHelper(uri, mode);
    }
}
