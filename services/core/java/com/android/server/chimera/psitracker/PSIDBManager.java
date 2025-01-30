package com.android.server.chimera.psitracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class PSIDBManager {
  public static final Object LOCK_DATABASE = new Object();
  public static volatile WeakReference mContext;
  public static PSIDBManager mInstance;
  public String mAvailMemTable = "psi_Available_Mem";
  public SQLiteDatabase mDB;
  public PSIDBHelper mDBHelper;

  public PSIDBManager() {
    Context context;
    if (mContext == null || (context = (Context) mContext.get()) == null) {
      return;
    }
    PSIDBHelper pSIDBHelper = new PSIDBHelper(context);
    this.mDBHelper = pSIDBHelper;
    this.mDB = pSIDBHelper.getWritableDatabase();
  }

  public static void init(Context context) {
    if (context != null) {
      mContext = new WeakReference(context);
    }
  }

  public static PSIDBManager getInstance() {
    PSIDBManager pSIDBManager;
    synchronized (LOCK_DATABASE) {
      if (mInstance == null) {
        mInstance = new PSIDBManager();
      }
      pSIDBManager = mInstance;
    }
    return pSIDBManager;
  }

  public boolean isDBClosed() {
    synchronized (LOCK_DATABASE) {
      SQLiteDatabase sQLiteDatabase = this.mDB;
      boolean z = true;
      if (sQLiteDatabase == null) {
        return true;
      }
      if (sQLiteDatabase.isOpen()) {
        z = false;
      }
      return z;
    }
  }

  public Cursor rawQuery(String str, String[] strArr) {
    Cursor rawQuery;
    if (this.mDB == null) {
      return null;
    }
    synchronized (LOCK_DATABASE) {
      rawQuery = this.mDB.rawQuery(str, strArr);
    }
    return rawQuery;
  }

  public int getRecordsCount(String str) {
    int i = 0;
    try {
      Cursor rawQuery = rawQuery("select count(*) from " + str, null);
      try {
        rawQuery.moveToFirst();
        i = rawQuery.getInt(0);
        rawQuery.close();
      } finally {
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return i;
  }

  public long insert(String str, ContentValues contentValues) {
    return this.mDB.insert(str, null, contentValues);
  }

  public long update(String str, ContentValues contentValues, String str2, String[] strArr) {
    return this.mDB.update(str, contentValues, str2, strArr);
  }

  public void beginTransaction() {
    SQLiteDatabase sQLiteDatabase = this.mDB;
    if (sQLiteDatabase != null) {
      sQLiteDatabase.beginTransaction();
    }
  }

  public void setTransactionSuccessful() {
    SQLiteDatabase sQLiteDatabase = this.mDB;
    if (sQLiteDatabase != null) {
      sQLiteDatabase.setTransactionSuccessful();
    }
  }

  public void endTransaction() {
    SQLiteDatabase sQLiteDatabase = this.mDB;
    if (sQLiteDatabase != null) {
      sQLiteDatabase.endTransaction();
    }
  }
}
