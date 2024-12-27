package com.android.server.knox.dar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0;
import com.android.server.enterprise.container.KnoxMUMContainerPolicy$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.sdp.SDPLog;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DarDatabaseCache {
    public final HashMap mCache = new LinkedHashMap() { // from class: com.android.server.knox.dar.DarDatabaseCache.1
        private static final long serialVersionUID = 6754995611664672888L;

        @Override // java.util.LinkedHashMap
        public final boolean removeEldestEntry(Map.Entry entry) {
            return size() >= 30;
        }
    };
    public final DatabaseHelper mDatabaseHelper;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DatabaseHelper extends SQLiteOpenHelper {
        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.i("DarDatabaseCache", "DB created! : " + sQLiteDatabase.getPath());
            sQLiteDatabase.execSQL("CREATE TABLE dar_info (_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,user INTEGER,value TEXT);");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(i, i2, "DB upgraded! : ", " to ", "DarDatabaseCache");
        }
    }

    public DarDatabaseCache(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, "dar.db", null, 1);
        databaseHelper.setWriteAheadLoggingEnabled(true);
        this.mDatabaseHelper = databaseHelper;
    }

    public static String makeTag(int i, String str) {
        return i + "_" + str;
    }

    public static void reportError(Exception exc, String str) {
        SDPLog.d("DarDatabaseCache", "Error occurred in ".concat(str));
        SDPLog.e(exc, null, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0067, code lost:
    
        if (r1 == false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:
    
        r13 = makeTag(r13, r14);
        r14 = r12.mCache;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
    
        monitor-enter(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0070, code lost:
    
        r12.mCache.put(r13, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0075, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x007a, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0064, code lost:
    
        if (r3 == null) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getInternal(int r13, java.lang.String r14) {
        /*
            r12 = this;
            java.lang.String r0 = makeTag(r13, r14)
            java.util.HashMap r1 = r12.mCache
            monitor-enter(r1)
            java.util.HashMap r2 = r12.mCache     // Catch: java.lang.Throwable -> L1a
            boolean r2 = r2.containsKey(r0)     // Catch: java.lang.Throwable -> L1a
            r3 = 0
            if (r2 == 0) goto L1c
            java.util.HashMap r2 = r12.mCache     // Catch: java.lang.Throwable -> L1a
            java.lang.Object r0 = r2.get(r0)     // Catch: java.lang.Throwable -> L1a
            java.lang.String r0 = (java.lang.String) r0     // Catch: java.lang.Throwable -> L1a
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1a
            goto L1e
        L1a:
            r12 = move-exception
            goto L81
        L1c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1a
            r0 = r3
        L1e:
            if (r0 == 0) goto L21
            return r0
        L21:
            r1 = 0
            com.android.server.knox.dar.DarDatabaseCache$DatabaseHelper r2 = r12.mDatabaseHelper     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            android.database.sqlite.SQLiteDatabase r4 = r2.getReadableDatabase()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r5 = "dar_info"
            java.lang.String r2 = "value"
            java.lang.String[] r6 = new java.lang.String[]{r2}     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String r7 = "name=? AND user=?"
            java.lang.String r2 = java.lang.Integer.toString(r13)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            java.lang.String[] r8 = new java.lang.String[]{r14, r2}     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            r11 = 0
            r9 = 0
            r10 = 0
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            if (r3 == 0) goto L58
            boolean r2 = r3.moveToFirst()     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            if (r2 == 0) goto L58
            java.lang.String r0 = r3.getString(r1)     // Catch: java.lang.Throwable -> L54 java.lang.Exception -> L56
            if (r0 == 0) goto L58
            r1 = 1
            goto L58
        L54:
            r12 = move-exception
            goto L7b
        L56:
            r2 = move-exception
            goto L5e
        L58:
            if (r3 == 0) goto L67
        L5a:
            r3.close()
            goto L67
        L5e:
            java.lang.String r4 = "get"
            reportError(r2, r4)     // Catch: java.lang.Throwable -> L54
            if (r3 == 0) goto L67
            goto L5a
        L67:
            if (r1 == 0) goto L7a
            java.lang.String r13 = makeTag(r13, r14)
            java.util.HashMap r14 = r12.mCache
            monitor-enter(r14)
            java.util.HashMap r12 = r12.mCache     // Catch: java.lang.Throwable -> L77
            r12.put(r13, r0)     // Catch: java.lang.Throwable -> L77
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L77
            goto L7a
        L77:
            r12 = move-exception
            monitor-exit(r14)     // Catch: java.lang.Throwable -> L77
            throw r12
        L7a:
            return r0
        L7b:
            if (r3 == 0) goto L80
            r3.close()
        L80:
            throw r12
        L81:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1a
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.DarDatabaseCache.getInternal(int, java.lang.String):java.lang.String");
    }

    public final long getLong(int i) {
        try {
            String internal = getInternal(i, "vl.rst.token.handle");
            if (internal != null) {
                return Long.parseLong(internal);
            }
            return 0L;
        } catch (NumberFormatException unused) {
            return 0L;
        }
    }

    public final void putInternal(int i, String str, String str2) {
        if (str2 == null) {
            return;
        }
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("name", str);
        KnoxMUMContainerPolicy$$ExternalSyntheticOutline0.m(i, m, "user", "value", str2);
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                SQLiteDatabase writableDatabase = this.mDatabaseHelper.getWritableDatabase();
                try {
                    writableDatabase.beginTransaction();
                    writableDatabase.delete("dar_info", "name=? AND user=?", new String[]{str, Integer.toString(i)});
                    writableDatabase.insert("dar_info", null, m);
                    writableDatabase.setTransactionSuccessful();
                    writableDatabase.endTransaction();
                    writableDatabase.close();
                    String makeTag = makeTag(i, str);
                    synchronized (this.mCache) {
                        this.mCache.put(makeTag, str2);
                    }
                } catch (Exception e) {
                    e = e;
                    sQLiteDatabase = writableDatabase;
                    reportError(e, "put");
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.endTransaction();
                        sQLiteDatabase.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabase = writableDatabase;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.endTransaction();
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
