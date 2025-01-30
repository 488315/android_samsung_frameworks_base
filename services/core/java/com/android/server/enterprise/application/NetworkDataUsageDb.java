package com.android.server.enterprise.application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.samsung.android.knox.application.NetworkStats;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.util.Hashtable;

/* loaded from: classes2.dex */
public class NetworkDataUsageDb {
    public Context mContext;

    public NetworkDataUsageDb(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0146 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r20v0, types: [java.util.Hashtable] */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateDataUsage(Hashtable hashtable) {
        SQLiteDatabase sQLiteDatabase;
        boolean z;
        SQLiteDatabase appControlDB;
        SQLiteDatabase sQLiteDatabase2;
        ?? r3;
        ContentValues contentValues;
        SQLiteDatabase sQLiteDatabase3;
        SQLiteDatabase sQLiteDatabase4 = null;
        String[] strArr = null;
        try {
            appControlDB = getAppControlDB(this.mContext);
        } catch (Exception unused) {
        }
        if (appControlDB == null) {
            return false;
        }
        if (hashtable != 0) {
            try {
            } catch (Exception unused2) {
                sQLiteDatabase4 = appControlDB;
            }
            if (!hashtable.isEmpty()) {
                boolean z2 = false;
                Cursor cursor = null;
                for (Integer num : hashtable.keySet()) {
                    try {
                        try {
                            NetworkStats networkStats = (NetworkStats) hashtable.get(num);
                            cursor = appControlDB.rawQuery("SELECT * FROM NetworkDataUsage WHERE _id = '" + num + "'", strArr);
                            if (cursor.moveToFirst()) {
                                try {
                                    contentValues = new ContentValues();
                                    sQLiteDatabase3 = appControlDB;
                                } catch (Exception e) {
                                    e = e;
                                    sQLiteDatabase4 = appControlDB;
                                }
                                try {
                                    contentValues.put("mobiledatausagesend", Long.valueOf(networkStats.mobileTxBytes + cursor.getLong(cursor.getColumnIndex("mobiledatausagesend"))));
                                    contentValues.put("mobiledatausagercv", Long.valueOf(networkStats.mobileRxBytes + cursor.getLong(cursor.getColumnIndex("mobiledatausagercv"))));
                                    contentValues.put("wifidatausagesend", Long.valueOf(networkStats.wifiTxBytes + cursor.getLong(cursor.getColumnIndex("wifidatausagesend"))));
                                    contentValues.put("wifidatausagesendrcv", Long.valueOf(networkStats.wifiRxBytes + cursor.getLong(cursor.getColumnIndex("wifidatausagesendrcv"))));
                                    sQLiteDatabase4 = sQLiteDatabase3;
                                    r3 = null;
                                    try {
                                        try {
                                            if (sQLiteDatabase4.update("NetworkDataUsage", contentValues, "_id = '" + num + "'", null) > 0) {
                                                z2 = true;
                                            }
                                            r3 = null;
                                        } catch (Exception e2) {
                                            e = e2;
                                            Log.e("NetworkDataUsageDb", "exception occurred " + e.getMessage());
                                            r3 = r3;
                                            if (cursor == null) {
                                            }
                                            cursor.close();
                                            appControlDB = sQLiteDatabase4;
                                            strArr = r3;
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        throw th;
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    sQLiteDatabase4 = sQLiteDatabase3;
                                    r3 = null;
                                    Log.e("NetworkDataUsageDb", "exception occurred " + e.getMessage());
                                    r3 = r3;
                                    if (cursor == null) {
                                    }
                                    cursor.close();
                                    appControlDB = sQLiteDatabase4;
                                    strArr = r3;
                                } catch (Throwable th2) {
                                    th = th2;
                                    sQLiteDatabase4 = sQLiteDatabase3;
                                }
                            } else {
                                sQLiteDatabase4 = appControlDB;
                                try {
                                    ContentValues contentValues2 = new ContentValues();
                                    contentValues2.put(KnoxCustomManagerService.f1773ID, num);
                                    contentValues2.put("mobiledatausagesend", Long.valueOf(networkStats.mobileTxBytes));
                                    contentValues2.put("mobiledatausagercv", Long.valueOf(networkStats.mobileRxBytes));
                                    contentValues2.put("wifidatausagesend", Long.valueOf(networkStats.wifiTxBytes));
                                    contentValues2.put("wifidatausagesendrcv", Long.valueOf(networkStats.wifiRxBytes));
                                    r3 = null;
                                    if (0 < sQLiteDatabase4.insert("NetworkDataUsage", null, contentValues2)) {
                                        z2 = true;
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    r3 = null;
                                    Log.e("NetworkDataUsageDb", "exception occurred " + e.getMessage());
                                    r3 = r3;
                                    if (cursor == null) {
                                        appControlDB = sQLiteDatabase4;
                                        strArr = r3;
                                    }
                                    cursor.close();
                                    appControlDB = sQLiteDatabase4;
                                    strArr = r3;
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            sQLiteDatabase4 = appControlDB;
                        }
                    } catch (Exception e5) {
                        e = e5;
                        r3 = strArr;
                        sQLiteDatabase4 = appControlDB;
                    }
                    try {
                        cursor.close();
                        appControlDB = sQLiteDatabase4;
                        strArr = r3;
                    } catch (Exception unused3) {
                        sQLiteDatabase = sQLiteDatabase4;
                        z = false;
                        if (sQLiteDatabase != null) {
                        }
                        return z;
                    }
                }
                sQLiteDatabase2 = appControlDB;
                z = z2;
                sQLiteDatabase = sQLiteDatabase2;
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return z;
            }
        }
        sQLiteDatabase2 = appControlDB;
        z = false;
        sQLiteDatabase = sQLiteDatabase2;
        if (sQLiteDatabase != null) {
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.server.enterprise.application.NetworkDataUsageDb] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v8, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.util.Hashtable] */
    /* JADX WARN: Type inference failed for: r1v6 */
    public Hashtable getMobileDataUsage() {
        SQLiteDatabase sQLiteDatabase;
        Hashtable hashtable;
        ?? r1 = 0;
        Hashtable hashtable2 = null;
        r1 = null;
        Cursor cursor = null;
        r1 = 0;
        try {
            try {
                this = getAppControlDB(this.mContext);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
            sQLiteDatabase = null;
            hashtable = null;
        } catch (Throwable th2) {
            th = th2;
            this = 0;
        }
        if (this == 0) {
            if (this != 0) {
                this.close();
            }
            return null;
        }
        try {
            Cursor query = this.query("NetworkDataUsage", null, null, null, null, null, null);
            if (query != null) {
                try {
                    try {
                        hashtable = new Hashtable();
                    } catch (Exception e2) {
                        e = e2;
                        hashtable = null;
                    }
                    try {
                        if (query.moveToFirst()) {
                            do {
                                NetworkStats networkStats = new NetworkStats();
                                networkStats.uid = query.getInt(query.getColumnIndex(KnoxCustomManagerService.f1773ID));
                                networkStats.mobileTxBytes = query.getLong(query.getColumnIndex("mobiledatausagesend"));
                                networkStats.mobileRxBytes = query.getLong(query.getColumnIndex("mobiledatausagercv"));
                                networkStats.wifiTxBytes = query.getLong(query.getColumnIndex("wifidatausagesend"));
                                networkStats.wifiRxBytes = query.getLong(query.getColumnIndex("wifidatausagesendrcv"));
                                hashtable.put(Integer.valueOf(query.getInt(query.getColumnIndex(KnoxCustomManagerService.f1773ID))), networkStats);
                            } while (query.moveToNext());
                        }
                        hashtable2 = hashtable;
                    } catch (Exception e3) {
                        e = e3;
                        cursor = query;
                        sQLiteDatabase = this;
                        Log.i("NetworkDataUsageDb", "getMobileDataUsage " + e);
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.close();
                        }
                        r1 = hashtable;
                        this = sQLiteDatabase;
                        return r1;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    r1 = query;
                    if (r1 != 0) {
                        r1.close();
                    }
                    if (this != 0) {
                        this.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            this.close();
            r1 = hashtable2;
            this = this;
        } catch (Exception e4) {
            e = e4;
            hashtable = null;
            sQLiteDatabase = this;
        }
        return r1;
    }

    public static SQLiteDatabase getAppControlDB(Context context) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = context.openOrCreateDatabase("dmappmgr.db", 0, null);
            Log.i("NetworkDataUsageDb", "::getAppControlDB: DB is Created ");
        } catch (Exception unused) {
            Log.i("NetworkDataUsageDb", "::getAppControlDB: Exception to create DB");
        }
        if (sQLiteDatabase != null && !isTableExists(sQLiteDatabase, "NetworkDataUsage")) {
            createDmAppMgrTable(sQLiteDatabase);
        }
        return sQLiteDatabase;
    }

    public static void createDmAppMgrTable(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("create table NetworkDataUsage (_id integer primary key , mobiledatausagercv long, wifidatausagesendrcv long, mobiledatausagesend long, wifidatausagesend long );");
            Log.i("NetworkDataUsageDb", "::createDmAppMgrTable: Table is Created ");
        } catch (Exception unused) {
            Log.i("NetworkDataUsageDb", "::createDmAppMgrTable: Exception while table is creating ");
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
            Log.i("NetworkDataUsageDb", "::isTableExists: Table exists ");
            return true;
        } catch (Exception unused) {
            Log.i("NetworkDataUsageDb", "::isTableExists:Table Does not exists ");
            return false;
        }
    }
}
