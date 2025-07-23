package com.google.android.mms.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes6.dex */
public final class SqliteWrapper {
    private static final String TAG = "SqliteWrapper";

    private SqliteWrapper() {
    }

    public static Cursor query(Context context, ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        try {
            return contentResolver.query(uri, strArr, str, strArr2, str2);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when query: ", e);
            return null;
        }
    }

    public static int update(Context context, ContentResolver contentResolver, Uri uri, ContentValues contentValues, String str, String[] strArr) {
        try {
            return contentResolver.update(uri, contentValues, str, strArr);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when update: ", e);
            return -1;
        }
    }

    public static int delete(Context context, ContentResolver contentResolver, Uri uri, String str, String[] strArr) {
        try {
            return contentResolver.delete(uri, str, strArr);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when delete: ", e);
            return -1;
        }
    }

    public static Uri insert(Context context, ContentResolver contentResolver, Uri uri, ContentValues contentValues) {
        try {
            return contentResolver.insert(uri, contentValues);
        } catch (SQLiteException e) {
            Log.e(TAG, "Catch a SQLiteException when insert: ", e);
            return null;
        } catch (IllegalStateException e2) {
            Log.e(TAG, "Catch a IllegalStateException when insert: ", e2);
            return null;
        }
    }
}
