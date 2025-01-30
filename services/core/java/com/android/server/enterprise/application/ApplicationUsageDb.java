package com.android.server.enterprise.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.samsung.android.knox.application.AppInfoLastUsage;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class ApplicationUsageDb {
    public Context mContext;

    public final long calculateLastUsageTime(long j, long j2, long j3, long j4) {
        if (j3 != 0 && j4 != 0) {
            if (j != 0) {
                if (j2 > j4) {
                    if (j >= j3) {
                        return j2 - j3;
                    }
                } else if (j < j3) {
                    return j4 - j;
                }
            }
            return j4 - j3;
        }
        if (j2 == 0) {
            return 0L;
        }
        return j2 - j;
    }

    public ApplicationUsageDb(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0075, code lost:
    
        if (r4.update("ApplicationControl", r12, "pkgname = '" + r14 + "'", null) > 0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0077, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a4, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a1, code lost:
    
        if (0 < r4.insert("ApplicationControl", null, r1)) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateForeGroundUsageDetails(String str, int i, long j, long j2) {
        SQLiteDatabase sQLiteDatabase;
        Cursor rawQuery;
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                sQLiteDatabase = getAppControlDB(this.mContext);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase == null) {
            return false;
        }
        try {
            rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM ApplicationControl WHERE pkgname = '" + str + "'", null);
        } catch (Exception e2) {
            e = e2;
        }
        try {
            if (rawQuery.moveToFirst()) {
                int i2 = rawQuery.getInt(rawQuery.getColumnIndex("launchcount")) + i;
                ContentValues contentValues = new ContentValues();
                contentValues.put("launchcount", Integer.valueOf(i2));
                contentValues.put("lastlaunchtime", Long.valueOf(j));
                contentValues.put("lastpausetime", Long.valueOf(j2));
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("launchcount", Integer.valueOf(i));
                contentValues2.put("pkgname", str);
                contentValues2.put("lastlaunchtime", Long.valueOf(j));
                contentValues2.put("lastpausetime", Long.valueOf(j2));
            }
        } catch (Exception e3) {
            e = e3;
            cursor = rawQuery;
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabase != null) {
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
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x005e, code lost:
    
        if (r9.update("ApplicationControl", r4, "pkgname = '" + r10 + "'", null) > 0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0060, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0086, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0083, code lost:
    
        if (0 < r9.insert("ApplicationControl", null, r0)) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateBackGroundUsageDetails(String str, long j, long j2) {
        SQLiteDatabase sQLiteDatabase;
        Cursor rawQuery;
        boolean z = false;
        Cursor cursor = null;
        try {
            try {
                sQLiteDatabase = getAppControlDB(this.mContext);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase == null) {
            return false;
        }
        try {
            rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM ApplicationControl WHERE pkgname = '" + str + "'", null);
        } catch (Exception e2) {
            e = e2;
        }
        try {
            if (rawQuery.moveToFirst()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("applastservicestarttime", Long.valueOf(j));
                contentValues.put("applastservicestoptime", Long.valueOf(j2));
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("pkgname", str);
                contentValues2.put("applastservicestarttime", Long.valueOf(j));
                contentValues2.put("applastservicestoptime", Long.valueOf(j2));
            }
        } catch (Exception e3) {
            e = e3;
            cursor = rawQuery;
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            if (sQLiteDatabase != null) {
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
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        return z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x007c, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0079, code lost:
    
        if (r10 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0061, code lost:
    
        if (r10 != null) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0063, code lost:
    
        r10.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public HashMap getLaunchCountOfAllApplication() {
        Cursor cursor;
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        HashMap hashMap;
        Exception e;
        Cursor cursor2 = null;
        try {
            sQLiteDatabase = getAppControlDB(this.mContext);
            if (sQLiteDatabase != null) {
                try {
                    cursor = sQLiteDatabase.query("ApplicationControl", null, null, null, null, null, null);
                    if (cursor != null) {
                        try {
                            try {
                                hashMap = new HashMap();
                            } catch (Exception e2) {
                                hashMap = null;
                                e = e2;
                            }
                            try {
                                if (cursor.moveToFirst()) {
                                    do {
                                        hashMap.put(cursor.getString(cursor.getColumnIndex("pkgname")), Integer.valueOf(cursor.getInt(cursor.getColumnIndex("launchcount"))));
                                    } while (cursor.moveToNext());
                                }
                            } catch (Exception e3) {
                                e = e3;
                                e.printStackTrace();
                                if (cursor != null) {
                                    cursor.close();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                            throw th;
                        }
                    } else {
                        hashMap = null;
                    }
                    cursor2 = cursor;
                } catch (Exception e4) {
                    hashMap = null;
                    e = e4;
                    cursor = null;
                } catch (Throwable th3) {
                    cursor = null;
                    th = th3;
                    if (cursor != null) {
                    }
                    if (sQLiteDatabase != null) {
                    }
                    throw th;
                }
            } else {
                hashMap = null;
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception e5) {
            cursor = null;
            hashMap = null;
            e = e5;
            sQLiteDatabase = null;
        } catch (Throwable th4) {
            cursor = null;
            th = th4;
            sQLiteDatabase = null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v15 */
    /* JADX WARN: Type inference failed for: r13v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [java.util.HashMap] */
    /* JADX WARN: Type inference failed for: r13v6 */
    public HashMap getAppUsageData() {
        SQLiteDatabase sQLiteDatabase;
        HashMap hashMap;
        String[] strArr = {"pkgname", "lastlaunchtime", "lastpausetime", "applastservicestarttime", "applastservicestoptime"};
        ?? r13 = 0;
        r13 = null;
        HashMap hashMap2 = null;
        r13 = null;
        Cursor cursor = null;
        r13 = 0;
        try {
            try {
                sQLiteDatabase = getAppControlDB(this.mContext);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
            hashMap = null;
            sQLiteDatabase = null;
        } catch (Throwable th2) {
            th = th2;
            sQLiteDatabase = null;
        }
        if (sQLiteDatabase == null) {
            if (sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
            return null;
        }
        try {
            Cursor query = sQLiteDatabase.query("ApplicationControl", strArr, null, null, null, null, null);
            if (query != null) {
                try {
                    try {
                        if (query.moveToFirst()) {
                            hashMap = new HashMap();
                            do {
                                try {
                                    String string = query.getString(query.getColumnIndex("pkgname"));
                                    long j = query.getLong(query.getColumnIndex("lastlaunchtime"));
                                    long j2 = query.getLong(query.getColumnIndex("lastpausetime"));
                                    long j3 = query.getLong(query.getColumnIndex("applastservicestarttime"));
                                    long calculateLastUsageTime = calculateLastUsageTime(j, j2, j3, query.getLong(query.getColumnIndex("applastservicestoptime")));
                                    if (calculateLastUsageTime != 0) {
                                        AppInfoLastUsage appInfoLastUsage = new AppInfoLastUsage();
                                        appInfoLastUsage.packageName = string;
                                        appInfoLastUsage.lastAppUsage = calculateLastUsageTime;
                                        if (j != 0) {
                                            appInfoLastUsage.lastLaunchTime = j;
                                        } else {
                                            appInfoLastUsage.lastLaunchTime = j3;
                                        }
                                        hashMap.put(string, appInfoLastUsage);
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    cursor = query;
                                    e.printStackTrace();
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (sQLiteDatabase != null) {
                                        sQLiteDatabase.close();
                                    }
                                    r13 = hashMap;
                                    return r13;
                                }
                            } while (query.moveToNext());
                            hashMap2 = hashMap;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        hashMap = null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    r13 = query;
                    if (r13 != 0) {
                        r13.close();
                    }
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            sQLiteDatabase.close();
            r13 = hashMap2;
        } catch (Exception e4) {
            e = e4;
            hashMap = null;
        }
        return r13;
    }

    public static SQLiteDatabase getAppControlDB(Context context) {
        SQLiteDatabase sQLiteDatabase = null;
        if (context != null) {
            try {
                sQLiteDatabase = context.openOrCreateDatabase("dmappmgr.db", 0, null);
            } catch (Exception e) {
                Log.i("ApplicationUsageDb", "::getAppControlDB: Exception to create DB");
                e.printStackTrace();
            }
            if (sQLiteDatabase != null && !isTableExists(sQLiteDatabase, "ApplicationControl")) {
                createDmAppMgrTable(sQLiteDatabase);
            }
        }
        return sQLiteDatabase;
    }

    public static void createDmAppMgrTable(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("create table ApplicationControl (_id integer primary key autoincrement, pkgname text, lastpausetime long, applastservicestarttime long, applastservicestoptime long, totalusagetime long, launchcount integer, lastlaunchtime long );");
            Log.i("ApplicationUsageDb", "::createDmAppMgrTable: Table is Created ");
        } catch (Exception e) {
            Log.i("ApplicationUsageDb", "::createDmAppMgrTable: Exception while table is creating ");
            e.printStackTrace();
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
            Log.i("ApplicationUsageDb", "::isTableExists:Table Does not exists ");
            return false;
        }
    }
}
