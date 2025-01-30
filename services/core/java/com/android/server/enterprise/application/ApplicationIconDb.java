package com.android.server.enterprise.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class ApplicationIconDb extends SQLiteOpenHelper {
  public final Context mContext;

  public ApplicationIconDb(Context context) {
    super(context, "dmappmgr.db", (SQLiteDatabase.CursorFactory) null, 3);
    this.mContext = context;
  }

  public boolean updateApplicationIcon(String str, byte[] bArr, int i) {
    int userId = UserHandle.getUserId(i);
    SQLiteDatabase writableDatabase = getWritableDatabase();
    if (writableDatabase == null) {
      Log.e("ApplicationIconDb", "updateApplicationIcon(): null db");
      return false;
    }
    try {
      Cursor query =
          writableDatabase.query(
              "ApplicationIcon",
              new String[] {KnoxCustomManagerService.f1773ID, "nameowner"},
              "pkgname = ? AND userid= ?",
              new String[] {str, String.valueOf(userId)},
              null,
              null,
              null,
              "1");
      try {
        if (query == null) {
          Log.e("ApplicationIconDb", "updateApplicationIcon(): null cursor");
          if (query != null) {
            query.close();
          }
          return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagedata", bArr);
        contentValues.put("nameowner", Integer.valueOf(i));
        if (query.getCount() == 0) {
          contentValues.put("userid", Integer.valueOf(userId));
          contentValues.put("pkgname", str);
          boolean z = writableDatabase.insert("ApplicationIcon", null, contentValues) > 0;
          query.close();
          return z;
        }
        if (!query.moveToFirst()) {
          Log.e("ApplicationIconDb", "updateApplicationIcon(): moveToFirst error!");
          query.close();
          return false;
        }
        int i2 = query.getInt(query.getColumnIndex(KnoxCustomManagerService.f1773ID));
        int i3 = query.getInt(query.getColumnIndex("nameowner"));
        if (i3 == 0 || i3 == i) {
          boolean z2 =
              ((long)
                      writableDatabase.update(
                          "ApplicationIcon",
                          contentValues,
                          "_id = ?",
                          new String[] {String.valueOf(i2)}))
                  > 0;
          query.close();
          return z2;
        }
        Log.e("ApplicationIconDb", "updateApplicationIcon(): invalid ownerUid");
        query.close();
        return false;
      } finally {
      }
    } catch (SQLException e) {
      Log.e("ApplicationIconDb", "updateApplicationIcon(): SQLException - ", e);
      return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:20:0x00be, code lost:

     if (r11.update("ApplicationIcon", r13, "pkgname = '" + r12 + "' AND " + r7 + " = " + r8, null) > 0) goto L29;
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x00c0, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x00e1, code lost:

     if (r11.delete("ApplicationIcon", "pkgname = '" + r12 + "' AND " + r7 + " = " + r8, null) > 0) goto L29;
  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0095 A[Catch: all -> 0x003d, Exception -> 0x0041, TRY_ENTER, TryCatch #3 {all -> 0x003d, blocks: (B:31:0x0033, B:11:0x0070, B:13:0x0076, B:16:0x0082, B:19:0x0095, B:22:0x00c2, B:27:0x0047, B:10:0x004a), top: B:30:0x0033 }] */
  /* JADX WARN: Removed duplicated region for block: B:22:0x00c2 A[Catch: all -> 0x003d, Exception -> 0x0041, TRY_LEAVE, TryCatch #3 {all -> 0x003d, blocks: (B:31:0x0033, B:11:0x0070, B:13:0x0076, B:16:0x0082, B:19:0x0095, B:22:0x00c2, B:27:0x0047, B:10:0x004a), top: B:30:0x0033 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean deleteApplicationIcon(String str, int i) {
    SQLiteDatabase writableDatabase;
    int i2;
    String str2;
    int i3;
    boolean z = false;
    Cursor cursor = null;
    try {
      try {
        writableDatabase = getWritableDatabase();
      } catch (Exception e) {
        e = e;
      }
      if (writableDatabase == null) {
        return false;
      }
      Cursor rawQuery =
          writableDatabase.rawQuery(
              "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND nameowner = " + i,
              null);
      if (rawQuery != null) {
        try {
          try {
          } catch (Throwable th) {
            th = th;
            cursor = rawQuery;
            if (cursor != null) {
              cursor.close();
            }
            throw th;
          }
        } catch (Exception e2) {
          e = e2;
          cursor = rawQuery;
          e.printStackTrace();
          if (cursor != null) {
            cursor.close();
          }
          return z;
        }
        if (rawQuery.getCount() != 0) {
          i2 = i;
          str2 = "nameowner";
          if (rawQuery.moveToFirst()
              && ((i3 = rawQuery.getInt(rawQuery.getColumnIndex("nameowner"))) == 0 || i3 == i)) {
            if (rawQuery.getString(rawQuery.getColumnIndex("newname")) == null) {
              ContentValues contentValues = new ContentValues();
              contentValues.putNull("imagedata");
            }
            return z;
          }
          rawQuery.close();
          return z;
        }
      }
      if (rawQuery != null) {
        try {
          rawQuery.close();
        } catch (Exception unused) {
        }
      }
      str2 = "userid";
      i2 = UserHandle.getUserId(i);
      rawQuery =
          writableDatabase.rawQuery(
              "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND userid = " + i2,
              null);
      if (rawQuery.moveToFirst()) {
        if (rawQuery.getString(rawQuery.getColumnIndex("newname")) == null) {}
        return z;
      }
      rawQuery.close();
      return z;
    } catch (Throwable th2) {
      th = th2;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:19:0x0081, code lost:

     if (r12.update("ApplicationIcon", r7, "pkgname = '" + r13 + "' AND userid = " + r3, null) > 0) goto L15;
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x0083, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x00a9, code lost:

     if (0 < r12.insert("ApplicationIcon", null, r0)) goto L15;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean updateApplicationName(String str, String str2, int i) {
    SQLiteDatabase writableDatabase;
    int userId = UserHandle.getUserId(i);
    boolean z = false;
    Cursor cursor = null;
    try {
      try {
        writableDatabase = getWritableDatabase();
      } catch (SQLiteException e) {
        e = e;
      }
      if (writableDatabase == null) {
        return false;
      }
      Cursor rawQuery =
          writableDatabase.rawQuery(
              "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND userid = " + userId,
              null);
      try {
      } catch (SQLiteException e2) {
        e = e2;
        cursor = rawQuery;
        e.printStackTrace();
        if (cursor != null) {
          cursor.close();
        }
        return z;
      } catch (Throwable th) {
        th = th;
        cursor = rawQuery;
        if (cursor != null) {
          cursor.close();
        }
        throw th;
      }
      if (rawQuery.moveToFirst()) {
        int i2 = rawQuery.getInt(rawQuery.getColumnIndex("nameowner"));
        if (i2 == 0 || i2 == i) {
          ContentValues contentValues = new ContentValues();
          contentValues.put("newname", str2);
          contentValues.put("nameowner", Integer.valueOf(i));
        }
        rawQuery.close();
        return z;
      }
      ContentValues contentValues2 = new ContentValues();
      contentValues2.put("pkgname", str);
      contentValues2.put("newname", str2);
      contentValues2.put("userid", Integer.valueOf(userId));
      contentValues2.put("nameowner", Integer.valueOf(i));
    } catch (Throwable th2) {
      th = th2;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:16:0x0073, code lost:

     if (r11.update("ApplicationIcon", r6, "pkgname = '" + r12 + "' AND nameowner = " + r13, null) > 0) goto L14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:17:0x0075, code lost:

     r3 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:19:0x0096, code lost:

     if (r11.delete("ApplicationIcon", "pkgname = '" + r12 + "' AND nameowner = " + r13, null) > 0) goto L14;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean deleteApplicationName(String str, int i) {
    SQLiteDatabase writableDatabase;
    boolean z = false;
    Cursor cursor = null;
    try {
      try {
        writableDatabase = getWritableDatabase();
      } catch (Throwable th) {
        th = th;
      }
    } catch (SQLiteException e) {
      e = e;
    }
    if (writableDatabase == null) {
      return false;
    }
    Cursor rawQuery =
        writableDatabase.rawQuery(
            "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND nameowner = " + i,
            null);
    try {
    } catch (SQLiteException e2) {
      e = e2;
      cursor = rawQuery;
      e.printStackTrace();
      if (cursor != null) {
        cursor.close();
      }
      return z;
    } catch (Throwable th2) {
      th = th2;
      cursor = rawQuery;
      if (cursor != null) {
        cursor.close();
      }
      throw th;
    }
    if (rawQuery.moveToFirst()) {
      if (rawQuery.getBlob(rawQuery.getColumnIndex("imagedata")) != null) {
        ContentValues contentValues = new ContentValues();
        contentValues.putNull("newname");
      }
      return z;
    }
    rawQuery.close();
    return z;
  }

  /* JADX WARN: Code restructure failed: missing block: B:20:0x0045, code lost:

     if (r1 == null) goto L24;
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x0048, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x003b, code lost:

     r1.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x0039, code lost:

     if (r1 != null) goto L17;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ArrayList clearApplicationData() {
    ArrayList arrayList = new ArrayList();
    Cursor cursor = null;
    try {
      try {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase != null) {
          Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM ApplicationIcon", null);
          try {
            int columnIndex = rawQuery.getColumnIndex("pkgname");
            if (rawQuery.moveToFirst()) {
              do {
                arrayList.add(rawQuery.getString(columnIndex));
              } while (rawQuery.moveToNext());
            }
            writableDatabase.delete("ApplicationIcon", null, null);
            cursor = rawQuery;
          } catch (SQLiteException e) {
            e = e;
            cursor = rawQuery;
            e.printStackTrace();
          } catch (Throwable th) {
            th = th;
            cursor = rawQuery;
            if (cursor != null) {
              cursor.close();
            }
            throw th;
          }
        }
      } catch (SQLiteException e2) {
        e = e2;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:20:0x0066, code lost:

     if (r1 == null) goto L24;
  */
  /* JADX WARN: Code restructure failed: missing block: B:21:0x0069, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x005c, code lost:

     r1.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x005a, code lost:

     if (r1 != null) goto L17;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public ArrayList clearApplicationDataForUid(int i) {
    ArrayList arrayList = new ArrayList();
    Cursor cursor = null;
    try {
      try {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        if (writableDatabase != null) {
          Cursor rawQuery =
              writableDatabase.rawQuery(
                  "SELECT * FROM ApplicationIcon WHERE nameowner = " + i, null);
          try {
            int columnIndex = rawQuery.getColumnIndex("pkgname");
            if (rawQuery.moveToFirst()) {
              do {
                arrayList.add(rawQuery.getString(columnIndex));
              } while (rawQuery.moveToNext());
            }
            writableDatabase.delete("ApplicationIcon", "nameowner = " + i, null);
            cursor = rawQuery;
          } catch (SQLiteException e) {
            e = e;
            cursor = rawQuery;
            e.printStackTrace();
          } catch (Throwable th) {
            th = th;
            cursor = rawQuery;
            if (cursor != null) {
              cursor.close();
            }
            throw th;
          }
        }
      } catch (Throwable th2) {
        th = th2;
      }
    } catch (SQLiteException e2) {
      e = e2;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:27:0x008d, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x008a, code lost:

     if (r1 == null) goto L27;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public HashMap getApplicationIconChangedMap() {
    HashMap hashMap = new HashMap();
    Cursor cursor = null;
    try {
      try {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        if (readableDatabase == null) {
          return hashMap;
        }
        cursor =
            readableDatabase.rawQuery(
                "SELECT * FROM ApplicationIcon WHERE imagedata IS NOT NULL", null);
        if (cursor != null && cursor.getCount() > 0) {
          while (cursor.moveToNext()) {
            if (cursor.getBlob(cursor.getColumnIndex("imagedata")) != null) {
              int i = cursor.getInt(cursor.getColumnIndex("userid"));
              if (hashMap.get(Integer.valueOf(i)) == null) {
                hashMap.put(Integer.valueOf(i), new ArrayList());
              }
              ((List) hashMap.get(Integer.valueOf(i)))
                  .add(cursor.getString(cursor.getColumnIndex("pkgname")));
            }
          }
        }
      } catch (Exception e) {
        Log.i("ApplicationIconDb", "getApplicationIconChangedMap  : Exception :" + e.getMessage());
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:27:0x008e, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:30:0x008b, code lost:

     if (r1 == null) goto L27;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public HashMap getApplicationNameChangedMap() {
    HashMap hashMap = new HashMap();
    Cursor cursor = null;
    try {
      try {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        if (readableDatabase == null) {
          return hashMap;
        }
        cursor =
            readableDatabase.rawQuery(
                "SELECT * FROM ApplicationIcon WHERE newname IS NOT NULL", null);
        if (cursor != null && cursor.getCount() > 0) {
          while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("newname")) != null) {
              int i = cursor.getInt(cursor.getColumnIndex("userid"));
              if (hashMap.get(Integer.valueOf(i)) == null) {
                hashMap.put(Integer.valueOf(i), new ArrayList());
              }
              ((List) hashMap.get(Integer.valueOf(i)))
                  .add(cursor.getString(cursor.getColumnIndex("pkgname")));
            }
          }
        }
      } catch (Exception e) {
        Log.i("ApplicationIconDb", "getApplicationNameChangedMap  : Exception :" + e.getMessage());
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x0048, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:11:0x006d, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x006a, code lost:

     if (r3 == null) goto L23;
  */
  /* JADX WARN: Code restructure failed: missing block: B:9:0x0046, code lost:

     if (r3 != null) goto L15;
  */
  /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public byte[] getApplicationIcon(String str, int i) {
    Cursor cursor;
    Cursor cursor2 = null;
    r0 = null;
    r0 = null;
    r0 = null;
    byte[] bArr = null;
    try {
      SQLiteDatabase readableDatabase = getReadableDatabase();
      if (readableDatabase == null) {
        return null;
      }
      cursor =
          readableDatabase.rawQuery(
              "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND userid = " + i,
              null);
      if (cursor != null) {
        try {
          try {
            if (cursor.moveToFirst()) {
              bArr = cursor.getBlob(cursor.getColumnIndex("imagedata"));
            }
          } catch (Exception e) {
            e = e;
            Log.i("ApplicationIconDb", "getApplicationIcon  : Exception :" + e.getMessage());
          }
        } catch (Throwable th) {
          th = th;
          cursor2 = cursor;
          if (cursor2 != null) {
            cursor2.close();
          }
          throw th;
        }
      }
    } catch (Exception e2) {
      e = e2;
      cursor = null;
    } catch (Throwable th2) {
      th = th2;
      if (cursor2 != null) {}
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:10:0x0049, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:11:0x006e, code lost:

     return r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:

     if (r3 == null) goto L23;
  */
  /* JADX WARN: Code restructure failed: missing block: B:9:0x0047, code lost:

     if (r3 != null) goto L15;
  */
  /* JADX WARN: Removed duplicated region for block: B:25:0x0073  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public String getApplicationName(String str, int i) {
    Cursor cursor;
    Cursor cursor2 = null;
    r0 = null;
    r0 = null;
    r0 = null;
    String str2 = null;
    try {
      SQLiteDatabase readableDatabase = getReadableDatabase();
      if (readableDatabase == null) {
        return null;
      }
      cursor =
          readableDatabase.rawQuery(
              "SELECT * FROM ApplicationIcon WHERE pkgname = '" + str + "' AND userid = " + i,
              null);
      if (cursor != null) {
        try {
          try {
            if (cursor.moveToFirst()) {
              str2 = cursor.getString(cursor.getColumnIndex("newname"));
            }
          } catch (Exception e) {
            e = e;
            Log.i("ApplicationIconDb", "getApplicationName  : Exception :" + e.getMessage());
          }
        } catch (Throwable th) {
          th = th;
          cursor2 = cursor;
          if (cursor2 != null) {
            cursor2.close();
          }
          throw th;
        }
      }
    } catch (Exception e2) {
      e = e2;
      cursor = null;
    } catch (Throwable th2) {
      th = th2;
      if (cursor2 != null) {}
      throw th;
    }
  }

  public static boolean isTableExists(SQLiteDatabase sQLiteDatabase, String str) {
    if (sQLiteDatabase == null || str == null) {
      return false;
    }
    String trim = str.trim();
    if (trim.length() <= 0) {
      return false;
    }
    try {
      sQLiteDatabase.execSQL("SELECT 1 FROM " + trim + " WHERE 1=0");
      return true;
    } catch (Exception unused) {
      Log.i("ApplicationIconDb", "::isTableExists:Table Does not exists ");
      return false;
    }
  }

  @Override // android.database.sqlite.SQLiteOpenHelper
  public void onCreate(SQLiteDatabase sQLiteDatabase) {
    if (!isTableExists(sQLiteDatabase, "ApplicationIcon")) {
      try {
        sQLiteDatabase.execSQL(
            "create table ApplicationIcon (_id integer primary key autoincrement, pkgname text,"
                + " imagedata BLOB, newname text, userid int, nameowner int);");
        Log.i("ApplicationIconDb", "::onCreate: Table is Created ");
        return;
      } catch (SQLException e) {
        Log.i("ApplicationIconDb", "::onCreate: Exception while table is creating ");
        e.printStackTrace();
        return;
      }
    }
    insertNewColumns(sQLiteDatabase);
  }

  @Override // android.database.sqlite.SQLiteOpenHelper
  public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    if (i2 == 3) {
      updateSecureFolderCustomizedInfo(sQLiteDatabase);
    }
  }

  public final void updateSecureFolderCustomizedInfo(SQLiteDatabase sQLiteDatabase) {
    try {
      int packageUid =
          this.mContext.getPackageManager().getPackageUid("com.samsung.knox.securefolder", 0);
      sQLiteDatabase.execSQL(
          "update ApplicationIcon set nameowner="
              + packageUid
              + " where pkgname='com.samsung.knox.securefolder' and nameowner=1000");
      StringBuilder sb = new StringBuilder();
      sb.append("securefolder customizedinfo owner updated to ");
      sb.append(packageUid);
      Log.d("ApplicationIconDb", sb.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final void insertNewColumns(SQLiteDatabase sQLiteDatabase) {
    if (isTableExists(sQLiteDatabase, "ApplicationIcon")) {
      try {
        sQLiteDatabase.execSQL(
            String.format(
                "ALTER TABLE %s ADD COLUMN %s;",
                "ApplicationIcon", String.format("%s %s", "newname", "TEXT")));
        sQLiteDatabase.execSQL(
            String.format(
                "ALTER TABLE %s ADD COLUMN %s;",
                "ApplicationIcon",
                String.format("%s DEFAULT %s", String.format("%s %s", "userid", "INT"), 0)));
        sQLiteDatabase.execSQL(
            String.format(
                "ALTER TABLE %s ADD COLUMN %s;",
                "ApplicationIcon", String.format("%s %s", "nameowner", "INT")));
      } catch (SQLException e) {
        Log.i("ApplicationIconDb", "::insertNewColumns: Exception while table is upgrading ");
        e.printStackTrace();
      }
    }
  }
}
