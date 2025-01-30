package com.android.server.knox.dar.sdp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class SdpDatabaseCache {
    public final HashMap mCache = new LinkedHashMap(10, 0.75f, true) { // from class: com.android.server.knox.dar.sdp.SdpDatabaseCache.1
        private static final long serialVersionUID = -6538574977717884266L;

        @Override // java.util.LinkedHashMap
        public boolean removeEldestEntry(Map.Entry entry) {
            return size() >= 30;
        }
    };
    public final DatabaseHelper mDatabaseHelper;
    public SQLiteDatabase mDbReadable;
    public SQLiteDatabase mDbWritable;

    public static void LogD(String str) {
    }

    public SdpDatabaseCache(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.mDatabaseHelper = databaseHelper;
        this.mDbReadable = databaseHelper.getReadableDatabase();
        this.mDbWritable = databaseHelper.getWritableDatabase();
    }

    public void putBoolean(int i, String str, boolean z) {
        putInternal(i, str, z ? "1" : "0");
    }

    public void putInt(int i, String str, int i2) {
        putInternal(i, str, String.valueOf(i2));
    }

    public final void putInternal(int i, String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str);
        contentValues.put("user", Integer.valueOf(i));
        contentValues.put("value", str2);
        boolean z = false;
        try {
            try {
                this.mDbWritable.beginTransaction();
                this.mDbWritable.delete("sdp_info", "name=? AND user=?", new String[]{str, Integer.toString(i)});
                this.mDbWritable.insert("sdp_info", null, contentValues);
                this.mDbWritable.setTransactionSuccessful();
                this.mDbWritable.endTransaction();
                z = true;
            } catch (Exception e) {
                reportError("put", e);
                this.mDbWritable.endTransaction();
            }
            if (z) {
                cache(i, str, str2);
            }
        } catch (Throwable th) {
            this.mDbWritable.endTransaction();
            throw th;
        }
    }

    public boolean getBoolean(int i, String str, boolean z) {
        String internal = getInternal(i, str);
        return "1".equals(internal) || (!"0".equals(internal) && z);
    }

    public int getInt(int i, String str, int i2) {
        try {
            String internal = getInternal(i, str);
            return internal != null ? Integer.parseInt(internal) : i2;
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004b, code lost:
    
        if (r1 == false) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004d, code lost:
    
        cache(r13, r14, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0050, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:
    
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
                cursor = this.mDbReadable.query("sdp_info", new String[]{"value"}, "name=? AND user=?", new String[]{str, Integer.toString(i)}, null, null, null);
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

    public void preload(int i) {
        Cursor cursor = null;
        try {
            try {
                cursor = this.mDbReadable.query("sdp_info", new String[]{"name", "value"}, "user=?", new String[]{Integer.toString(i)}, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        putInternal(i, cursor.getString(0), cursor.getString(1));
                    }
                }
                if (cursor == null) {
                    return;
                }
            } catch (Exception e) {
                reportError("preload", e);
                if (cursor == null) {
                    return;
                }
            }
            cursor.close();
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public void destroy(int i) {
        boolean z;
        try {
            try {
                this.mDbWritable.beginTransaction();
                this.mDbWritable.delete("sdp_info", "user='" + i + "'", null);
                this.mDbWritable.setTransactionSuccessful();
                this.mDbWritable.endTransaction();
                z = true;
            } catch (Exception e) {
                reportError("remove", e);
                this.mDbWritable.endTransaction();
                z = false;
            }
            if (z) {
                synchronized (this.mCache) {
                    Iterator it = this.mCache.keySet().iterator();
                    String valueOf = String.valueOf(i);
                    while (it.hasNext()) {
                        String str = (String) it.next();
                        if (str.startsWith(valueOf)) {
                            it.remove();
                            LogD("remove - Val of key [ " + str + " ]");
                        }
                    }
                }
            }
        } catch (Throwable th) {
            this.mDbWritable.endTransaction();
            throw th;
        }
    }

    public static String makeTag(int i, String str) {
        return i + "_" + str;
    }

    public static void reportError(String str, Exception exc) {
        SDPLog.m39d("SdpDatabaseCache", "Error occurred in " + str);
        SDPLog.m40e(exc);
    }

    public static void LogI(String str) {
        Log.i("SdpDatabaseCache", str);
    }

    public class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, "sdp.db", (SQLiteDatabase.CursorFactory) null, 1);
            setWriteAheadLoggingEnabled(true);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            SdpDatabaseCache.LogI("DB created! : " + sQLiteDatabase.getPath());
            sQLiteDatabase.execSQL("CREATE TABLE sdp_info (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,user INTEGER,value TEXT);");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            SdpDatabaseCache.LogI("DB upgraded! : " + i + " to " + i2);
        }
    }
}
