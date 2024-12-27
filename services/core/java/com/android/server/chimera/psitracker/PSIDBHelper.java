package com.android.server.chimera.psitracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.secutil.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PSIDBHelper extends SQLiteOpenHelper {
    public String CREATE_AVAIL_MEM_TABLE;
    public String DATABASE_UPDATE_TEAM_1;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(this.CREATE_AVAIL_MEM_TABLE);
        } catch (SQLiteException e) {
            Log.e("PSITracker-DBHelper", "Exception caught  : " + e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS psi_Sample");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS psi_Entry_App");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS psi_Available_Mem");
            onCreate(sQLiteDatabase);
        } catch (SQLiteException e) {
            Log.e("PSITracker-DBHelper", "Exception caught  : " + e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i < 2) {
            try {
                sQLiteDatabase.execSQL(this.DATABASE_UPDATE_TEAM_1);
            } catch (SQLiteException e) {
                Log.e("PSITracker-DBHelper", "Exception caught  : " + e);
                return;
            }
        }
        if (i < 3) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS psi_Sample");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS psi_Entry_App");
        }
    }
}
