package com.android.server.chimera.psitracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PSIDBManager {
    public static final Object LOCK_DATABASE = new Object();
    public static volatile WeakReference mContext;
    public static PSIDBManager mInstance;
    public final SQLiteDatabase mDB;

    public PSIDBManager() {
        Context context;
        if (mContext == null || (context = (Context) mContext.get()) == null) {
            return;
        }
        PSIDBHelper pSIDBHelper = new PSIDBHelper(context, "PSITracker.db", null, 3);
        pSIDBHelper.CREATE_AVAIL_MEM_TABLE = "create table if not exists psi_Available_Mem(id integer primary key autoincrement,availMem integer, cached integer, running integer,checkTime integer)";
        pSIDBHelper.DATABASE_UPDATE_TEAM_1 = "ALTER TABLE psi_Available_Mem ADD COLUMN running integer";
        new WeakReference(context);
        this.mDB = pSIDBHelper.getWritableDatabase();
    }

    public static PSIDBManager getInstance() {
        PSIDBManager pSIDBManager;
        synchronized (LOCK_DATABASE) {
            try {
                if (mInstance == null) {
                    mInstance = new PSIDBManager();
                }
                pSIDBManager = mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return pSIDBManager;
    }

    public final void endTransaction() {
        SQLiteDatabase sQLiteDatabase = this.mDB;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.endTransaction();
        }
    }

    public final boolean isDBClosed() {
        synchronized (LOCK_DATABASE) {
            try {
                if (this.mDB == null) {
                    return true;
                }
                return !r2.isOpen();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Cursor rawQuery(String str) {
        Cursor rawQuery;
        if (this.mDB == null) {
            return null;
        }
        synchronized (LOCK_DATABASE) {
            rawQuery = this.mDB.rawQuery(str, null);
        }
        return rawQuery;
    }
}
