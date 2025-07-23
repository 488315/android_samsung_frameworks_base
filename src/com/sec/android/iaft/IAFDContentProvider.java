package com.sec.android.iaft;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/* loaded from: classes6.dex */
public class IAFDContentProvider extends ContentProvider {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        this.mContext = getContext();
        this.mDatabase = SQLiteDatabase.create(null);
        return true;
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        this.mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        this.mContext.getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
