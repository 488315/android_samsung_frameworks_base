package com.android.server.net.watchlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Slog;
import com.android.internal.util.HexDump;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchlistReportDbHelper extends SQLiteOpenHelper {
    public static final String[] DIGEST_DOMAIN_PROJECTION = {"app_digest", "cnc_domain"};
    public static WatchlistReportDbHelper sInstance;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AggregatedResult {
        public final HashMap appDigestCNCList;
        public final Set appDigestList;

        public AggregatedResult(Set set, HashMap hashMap) {
            this.appDigestList = set;
            this.appDigestCNCList = hashMap;
        }
    }

    public final void cleanup(long j) {
        try {
            getWritableDatabase().delete("records", DeviceIdleController$$ExternalSyntheticOutline0.m(j, "timestamp< "), null);
        } catch (SQLiteException e) {
            Slog.e("WatchlistReportDbHelper", "Error opening the database to cleanup", e);
        }
    }

    public final AggregatedResult getAggregatedRecords(long j) {
        Cursor cursor = null;
        try {
            try {
                Cursor query = getReadableDatabase().query(true, "records", DIGEST_DOMAIN_PROJECTION, "timestamp < ?", new String[]{Long.toString(j)}, null, null, null, null);
                if (query == null) {
                    if (query != null) {
                        query.close();
                    }
                    return null;
                }
                try {
                    HashSet hashSet = new HashSet();
                    HashMap hashMap = new HashMap();
                    while (query.moveToNext()) {
                        String hexString = HexDump.toHexString(query.getBlob(0));
                        String string = query.getString(1);
                        hashSet.add(hexString);
                        if (cursor != null) {
                            cursor = string;
                        }
                        hashMap.put(hexString, string);
                    }
                    AggregatedResult aggregatedResult = new AggregatedResult(hashSet, hashMap);
                    query.close();
                    return aggregatedResult;
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SQLiteException e) {
            Slog.e("WatchlistReportDbHelper", "Error opening the database", e);
            return null;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE records(app_digest BLOB,cnc_domain TEXT,timestamp INTEGER DEFAULT 0 )");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS records");
        sQLiteDatabase.execSQL("CREATE TABLE records(app_digest BLOB,cnc_domain TEXT,timestamp INTEGER DEFAULT 0 )");
    }
}
