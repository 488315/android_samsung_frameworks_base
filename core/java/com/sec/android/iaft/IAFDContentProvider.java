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
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override // android.content.ContentProvider, android.content.ContentInterface
  public String getType(Uri uri) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override // android.content.ContentProvider
  public Uri insert(Uri uri, ContentValues values) {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override // android.content.ContentProvider
  public Cursor query(
      Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    this.mContext.getContentResolver().notifyChange(uri, null);
    return null;
  }

  @Override // android.content.ContentProvider
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    this.mContext.getContentResolver().notifyChange(uri, null);
    return 0;
  }
}
