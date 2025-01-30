package com.samsung.android.displaysolution;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import java.util.List;

/* loaded from: classes2.dex */
public class DisplaySolutionDataBase extends SQLiteOpenHelper {
    public DisplaySolutionDataBase(Context context) {
        super(context, "displaysolution_setting.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE MSCS_APP_LIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,packagename TEXT NOT NULL,settingstate INTEGER);");
        } catch (SQLException e) {
            Log.e("DisplaySolutionDataBase", "Exception in creating table " + e);
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.d("DisplaySolutionDataBase", "Upgrading settings database from version " + i + " to " + i2);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] strArr, String str, String[] strArr2, String str2, String str3) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(str3);
        return sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, strArr2, null, null, str2);
    }

    public void insert(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str) {
        if (sQLiteDatabase == null) {
            sQLiteDatabase = getWritableDatabase();
        }
        sQLiteDatabase.insert(str, "", contentValues);
    }

    public void bulkInsert(SQLiteDatabase sQLiteDatabase, List list, String str) {
        if (sQLiteDatabase == null) {
            try {
                sQLiteDatabase = getWritableDatabase();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        sQLiteDatabase.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            sQLiteDatabase.insert(str, "", (ContentValues) list.get(i));
        }
        sQLiteDatabase.setTransactionSuccessful();
        sQLiteDatabase.endTransaction();
    }

    public int update(SQLiteDatabase sQLiteDatabase, ContentValues contentValues, String str, String[] strArr, String str2) {
        if (sQLiteDatabase == null) {
            sQLiteDatabase = getWritableDatabase();
        }
        return sQLiteDatabase.update(str2, contentValues, str, strArr);
    }
}
