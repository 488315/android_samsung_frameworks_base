package com.android.server.knox.dar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.server.knox.dar.sdp.SDPLog;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class DarDatabaseCache {
  public final HashMap mCache =
      new LinkedHashMap(
          10, 0.75f, true) { // from class: com.android.server.knox.dar.DarDatabaseCache.1
        private static final long serialVersionUID = 6754995611664672888L;

        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry entry) {
          return size() >= 30;
        }
      };
  public final DatabaseHelper mDatabaseHelper;

  public static void LogD(String str) {}

  public DarDatabaseCache(Context context) {
    this.mDatabaseHelper = new DatabaseHelper(context);
  }

  public void putInt(int i, String str, int i2) {
    putInternal(i, str, String.valueOf(i2));
  }

  public void putLong(int i, String str, long j) {
    putInternal(i, str, String.valueOf(j));
  }

  public void putString(int i, String str, String str2) {
    putInternal(i, str, str2);
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x0066  */
  /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void putInternal(int i, String str, String str2) {
    SQLiteDatabase writableDatabase;
    if (str == null || str2 == null) {
      return;
    }
    ContentValues contentValues = new ContentValues();
    contentValues.put("name", str);
    contentValues.put("user", Integer.valueOf(i));
    contentValues.put("value", str2);
    SQLiteDatabase sQLiteDatabase = null;
    boolean z = false;
    try {
      try {
        writableDatabase = this.mDatabaseHelper.getWritableDatabase();
      } catch (Throwable th) {
        th = th;
      }
    } catch (Exception e) {
      e = e;
    }
    try {
      writableDatabase.beginTransaction();
      writableDatabase.delete(
          "dar_info", "name=? AND user=?", new String[] {str, Integer.toString(i)});
      writableDatabase.insert("dar_info", null, contentValues);
      writableDatabase.setTransactionSuccessful();
      writableDatabase.endTransaction();
      writableDatabase.close();
      z = true;
    } catch (Exception e2) {
      e = e2;
      sQLiteDatabase = writableDatabase;
      reportError("put", e);
      if (sQLiteDatabase != null) {
        sQLiteDatabase.endTransaction();
        sQLiteDatabase.close();
      }
      if (z) {}
    } catch (Throwable th2) {
      th = th2;
      sQLiteDatabase = writableDatabase;
      if (sQLiteDatabase != null) {
        sQLiteDatabase.endTransaction();
        sQLiteDatabase.close();
      }
      throw th;
    }
    if (z) {
      return;
    }
    cache(i, str, str2);
  }

  public int getInt(int i, String str, int i2) {
    try {
      String internal = getInternal(i, str);
      return internal != null ? Integer.parseInt(internal) : i2;
    } catch (NumberFormatException unused) {
      return i2;
    }
  }

  public long getLong(int i, String str, long j) {
    try {
      String internal = getInternal(i, str);
      return internal != null ? Long.parseLong(internal) : j;
    } catch (NumberFormatException unused) {
      return j;
    }
  }

  public String getString(int i, String str, String str2) {
    String internal = getInternal(i, str);
    return internal != null ? internal : str2;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x004e, code lost:

     if (r1 == false) goto L23;
  */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:

     cache(r13, r14, r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:19:0x0053, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:26:0x004b, code lost:

     if (r2 == null) goto L21;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final String getInternal(int i, String str) {
    String hitOrNull = hitOrNull(i, str);
    if (hitOrNull != null) {
      return hitOrNull;
    }
    boolean z = false;
    Cursor cursor = null;
    try {
      try {
        cursor =
            this.mDatabaseHelper
                .getReadableDatabase()
                .query(
                    "dar_info",
                    new String[] {"value"},
                    "name=? AND user=?",
                    new String[] {str, Integer.toString(i)},
                    null,
                    null,
                    null);
        if (cursor != null && cursor.moveToFirst()) {
          hitOrNull = cursor.getString(0);
          if (hitOrNull != null) {
            z = true;
          }
        }
      } catch (Exception e) {
        reportError("get", e);
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  public void delete(int i, String str) {
    boolean z = false;
    SQLiteDatabase sQLiteDatabase = null;
    try {
      try {
        sQLiteDatabase = this.mDatabaseHelper.getWritableDatabase();
        sQLiteDatabase.beginTransaction();
        sQLiteDatabase.delete(
            "dar_info", "name=? AND user=?", new String[] {str, Integer.toString(i)});
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
        sQLiteDatabase.close();
        z = true;
      } catch (Exception e) {
        reportError("del", e);
        if (sQLiteDatabase != null) {
          sQLiteDatabase.endTransaction();
          sQLiteDatabase.close();
        }
      }
      if (z) {
        decache(i, str);
      }
    } catch (Throwable th) {
      if (sQLiteDatabase != null) {
        sQLiteDatabase.endTransaction();
        sQLiteDatabase.close();
      }
      throw th;
    }
  }

  public final String hitOrNull(int i, String str) {
    String makeTag = makeTag(i, str);
    synchronized (this.mCache) {
      if (!this.mCache.containsKey(makeTag)) {
        return null;
      }
      LogD("hit - [ Tag : " + makeTag + " ]");
      return (String) this.mCache.get(makeTag);
    }
  }

  public final void cache(int i, String str, String str2) {
    String makeTag = makeTag(i, str);
    synchronized (this.mCache) {
      LogD("cache - [ Tag : " + makeTag + ", Val : " + str2 + " ]");
      this.mCache.put(makeTag, str2);
    }
  }

  public final void decache(int i, String str) {
    String makeTag = makeTag(i, str);
    synchronized (this.mCache) {
      if (this.mCache.containsKey(makeTag)) {
        LogD("decache - [ Tag : " + makeTag + " ]");
        this.mCache.remove(makeTag);
      }
    }
  }

  public static String makeTag(int i, String str) {
    return i + "_" + str;
  }

  public static void reportError(String str, Exception exc) {
    SDPLog.m39d("DarDatabaseCache", "Error occurred in " + str);
    SDPLog.m40e(exc);
  }

  public static void LogI(String str) {
    Log.i("DarDatabaseCache", str);
  }

  public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
      super(context, "dar.db", (SQLiteDatabase.CursorFactory) null, 1);
      setWriteAheadLoggingEnabled(true);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
      DarDatabaseCache.LogI("DB created! : " + sQLiteDatabase.getPath());
      sQLiteDatabase.execSQL(
          "CREATE TABLE dar_info (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,user"
              + " INTEGER,value TEXT);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
      DarDatabaseCache.LogI("DB upgraded! : " + i + " to " + i2);
    }
  }
}
