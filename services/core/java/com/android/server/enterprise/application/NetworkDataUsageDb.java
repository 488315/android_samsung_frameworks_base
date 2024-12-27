package com.android.server.enterprise.application;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.samsung.android.knox.application.NetworkStats;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.Hashtable;

public final class NetworkDataUsageDb {
    public final Context mContext;

    public NetworkDataUsageDb(Context context) {
        this.mContext = context;
    }

    public static SQLiteDatabase getAppControlDB(Context context) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = context.openOrCreateDatabase("dmappmgr.db", 0, null);
            Log.i("NetworkDataUsageDb", "::getAppControlDB: DB is Created ");
        } catch (Exception unused) {
            Log.i("NetworkDataUsageDb", "::getAppControlDB: Exception to create DB");
        }
        if (sQLiteDatabase != null) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.execSQL("SELECT 1 FROM NetworkDataUsage WHERE 1=0");
                    Log.i("NetworkDataUsageDb", "::isTableExists: Table exists ");
                } catch (Exception unused2) {
                    Log.i("NetworkDataUsageDb", "::isTableExists:Table Does not exists ");
                }
            }
            try {
                sQLiteDatabase.execSQL(
                        "create table NetworkDataUsage (_id integer primary key ,"
                            + " mobiledatausagercv long, wifidatausagesendrcv long,"
                            + " mobiledatausagesend long, wifidatausagesend long );");
                Log.i("NetworkDataUsageDb", "::createDmAppMgrTable: Table is Created ");
            } catch (Exception unused3) {
                Log.i(
                        "NetworkDataUsageDb",
                        "::createDmAppMgrTable: Exception while table is creating ");
            }
        }
        return sQLiteDatabase;
    }

    public final Hashtable getMobileDataUsage() {
        SQLiteDatabase sQLiteDatabase;
        Hashtable hashtable;
        Cursor cursor = null;
        Hashtable hashtable2 = null;
        cursor = null;
        cursor = null;
        cursor = null;
        try {
            sQLiteDatabase = getAppControlDB(this.mContext);
            if (sQLiteDatabase == null) {
                if (sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                return null;
            }
            try {
                try {
                    Cursor query =
                            sQLiteDatabase.query(
                                    "NetworkDataUsage", null, null, null, null, null, null);
                    if (query != null) {
                        try {
                            try {
                                hashtable = new Hashtable();
                                try {
                                    if (query.moveToFirst()) {
                                        do {
                                            NetworkStats networkStats = new NetworkStats();
                                            networkStats.uid =
                                                    query.getInt(
                                                            query.getColumnIndex(
                                                                    KnoxCustomManagerService.ID));
                                            networkStats.mobileTxBytes =
                                                    query.getLong(
                                                            query.getColumnIndex(
                                                                    "mobiledatausagesend"));
                                            networkStats.mobileRxBytes =
                                                    query.getLong(
                                                            query.getColumnIndex(
                                                                    "mobiledatausagercv"));
                                            networkStats.wifiTxBytes =
                                                    query.getLong(
                                                            query.getColumnIndex(
                                                                    "wifidatausagesend"));
                                            networkStats.wifiRxBytes =
                                                    query.getLong(
                                                            query.getColumnIndex(
                                                                    "wifidatausagesendrcv"));
                                            hashtable.put(
                                                    Integer.valueOf(
                                                            query.getInt(
                                                                    query.getColumnIndex(
                                                                            KnoxCustomManagerService
                                                                                    .ID))),
                                                    networkStats);
                                        } while (query.moveToNext());
                                    }
                                    hashtable2 = hashtable;
                                } catch (Exception e) {
                                    e = e;
                                    cursor = query;
                                    Log.i("NetworkDataUsageDb", "getMobileDataUsage " + e);
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (sQLiteDatabase != null) {
                                        sQLiteDatabase.close();
                                    }
                                    return hashtable;
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = query;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (sQLiteDatabase != null) {
                                    sQLiteDatabase.close();
                                }
                                throw th;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            hashtable = null;
                        }
                    }
                    if (query != null) {
                        query.close();
                    }
                    sQLiteDatabase.close();
                    return hashtable2;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
                hashtable = null;
            }
        } catch (Exception e4) {
            e = e4;
            sQLiteDatabase = null;
            hashtable = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = null;
        }
    }
}
