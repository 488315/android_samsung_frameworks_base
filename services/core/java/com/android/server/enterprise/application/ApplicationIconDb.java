package com.android.server.enterprise.application;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public final class ApplicationIconDb extends SQLiteOpenHelper {
    public final Context mContext;

    public ApplicationIconDb(Context context) {
        super(context, "dmappmgr.db", (SQLiteDatabase.CursorFactory) null, 3);
        this.mContext = context;
    }

    public static boolean isTableExists(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase == null) {
            return false;
        }
        try {
            sQLiteDatabase.execSQL("SELECT 1 FROM ApplicationIcon WHERE 1=0");
            return true;
        } catch (Exception unused) {
            Log.i("ApplicationIconDb", "::isTableExists:Table Does not exists ");
            return false;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        if (!isTableExists(sQLiteDatabase)) {
            try {
                sQLiteDatabase.execSQL(
                        "create table ApplicationIcon (_id integer primary key autoincrement,"
                            + " pkgname text, imagedata BLOB, newname text, userid int, nameowner"
                            + " int);");
                Log.i("ApplicationIconDb", "::onCreate: Table is Created ");
                return;
            } catch (SQLException e) {
                Log.i("ApplicationIconDb", "::onCreate: Exception while table is creating ");
                e.printStackTrace();
                return;
            }
        }
        if (isTableExists(sQLiteDatabase)) {
            try {
                sQLiteDatabase.execSQL("ALTER TABLE ApplicationIcon ADD COLUMN newname TEXT;");
                sQLiteDatabase.execSQL(
                        "ALTER TABLE ApplicationIcon ADD COLUMN "
                                + "userid INT".concat(" DEFAULT 0")
                                + ";");
                sQLiteDatabase.execSQL("ALTER TABLE ApplicationIcon ADD COLUMN nameowner INT;");
            } catch (SQLException e2) {
                Log.i(
                        "ApplicationIconDb",
                        "::insertNewColumns: Exception while table is upgrading ");
                e2.printStackTrace();
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i2 == 3) {
            try {
                int packageUid =
                        this.mContext
                                .getPackageManager()
                                .getPackageUid("com.samsung.knox.securefolder", 0);
                sQLiteDatabase.execSQL(
                        "update ApplicationIcon set nameowner="
                                + packageUid
                                + " where pkgname='com.samsung.knox.securefolder' and"
                                + " nameowner=1000");
                StringBuilder sb =
                        new StringBuilder("securefolder customizedinfo owner updated to ");
                sb.append(packageUid);
                Log.d("ApplicationIconDb", sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
