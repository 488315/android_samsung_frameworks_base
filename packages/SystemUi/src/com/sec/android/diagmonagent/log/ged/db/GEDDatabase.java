package com.sec.android.diagmonagent.log.ged.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sec.android.diagmonagent.log.ged.db.dao.EventDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ResultDao;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class GEDDatabase {
    public static volatile GEDDatabase gedDatabase;
    public final Context context;

    /* renamed from: db */
    public final SQLiteDatabase f634db;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SQLiteHelper extends SQLiteOpenHelper {
        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("CREATE TABLE Event(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, serviceId TEXT, deviceId TEXT, serviceVersion TEXT, serviceAgreeType TEXT, sdkVersion TEXT, sdkType TEXT, serviceDefinedKey TEXT, errorCode TEXT, logPath TEXT, description TEXT, relayClientVersion TEXT, relayClientType TEXT, extension TEXT, networkMode INTEGER NOT NULL, memory TEXT, storage TEXT, status INTEGER NOT NULL, retryCount INTEGER NOT NULL, eventId TEXT, s3Path TEXT, timestamp INTEGER NOT NULL, expirationTime INTEGER NOT NULL)");
            sQLiteDatabase.execSQL("CREATE TABLE Result(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, eventId TEXT, serviceId TEXT, clientStatusCode INTEGER NOT NULL, timestamp INTEGER NOT NULL)");
        }

        private SQLiteHelper(GEDDatabase gEDDatabase, Context context) {
            super(context, "diagmon_ged.db", (SQLiteDatabase.CursorFactory) null, 1);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private GEDDatabase(Context context) {
        this.f634db = new SQLiteHelper(context).getWritableDatabase();
        this.context = context;
    }

    public static GEDDatabase get(Context context) {
        if (gedDatabase == null) {
            synchronized (DataController.class) {
                if (gedDatabase == null) {
                    gedDatabase = new GEDDatabase(context);
                }
            }
        }
        return gedDatabase;
    }

    public final EventDao getEventDao() {
        return new EventDao(this.f634db);
    }

    public final ResultDao getResultDao() {
        return new ResultDao(this.f634db);
    }
}
