package com.samsung.android.displaysolution;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplaySolutionDataBase extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL(
                    "CREATE TABLE MSCS_APP_LIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,packagename"
                        + " TEXT NOT NULL,settingstate INTEGER);");
        } catch (SQLException e) {
            Log.e("DisplaySolutionDataBase", "Exception in creating table " + e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                i,
                i2,
                "Upgrading settings database from version ",
                " to ",
                "DisplaySolutionDataBase");
    }
}
