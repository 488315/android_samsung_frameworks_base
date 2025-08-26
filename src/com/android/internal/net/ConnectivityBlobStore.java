package com.android.internal.net;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ConnectivityBlobStore {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS blob_table (owner INTEGER,name BLOB,blob BLOB,UNIQUE(owner, name));";
    private static final String ROOT_DIR = "/data/misc/connectivityblobdb/";
    private static final String TABLENAME = "blob_table";
    private static final String TAG = "ConnectivityBlobStore";
    private final SQLiteDatabase mDb;

    public ConnectivityBlobStore(String str) {
        this(new File(ROOT_DIR + str));
    }

    public ConnectivityBlobStore(File file) throws SQLException {
        SQLiteDatabase sQLiteDatabaseOpenDatabase = SQLiteDatabase.openDatabase(file, new SQLiteDatabase.OpenParams.Builder().addOpenFlags(268435456).addOpenFlags(536870912).build());
        this.mDb = sQLiteDatabaseOpenDatabase;
        sQLiteDatabaseOpenDatabase.execSQL(CREATE_TABLE);
    }

    public boolean put(String str, byte[] bArr) {
        int callingUid = Binder.getCallingUid();
        ContentValues contentValues = new ContentValues();
        contentValues.put("owner", Integer.valueOf(callingUid));
        contentValues.put("name", str);
        contentValues.put("blob", bArr);
        return this.mDb.replace(TABLENAME, null, contentValues) > 0;
    }

    public byte[] get(String str) {
        try {
            Cursor cursorQuery = this.mDb.query(TABLENAME, new String[]{"blob"}, "owner=? AND name=?", new String[]{Integer.toString(Binder.getCallingUid()), str}, null, null, null);
            try {
                if (cursorQuery.moveToFirst()) {
                    byte[] blob = cursorQuery.getBlob(0);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return blob;
                }
                if (cursorQuery == null) {
                    return null;
                }
                cursorQuery.close();
                return null;
            } finally {
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in getting " + str + ": " + e);
            return null;
        }
    }

    public boolean remove(String str) {
        try {
            return this.mDb.delete(TABLENAME, "owner=? AND name=?", new String[]{Integer.toString(Binder.getCallingUid()), str}) > 0;
        } catch (SQLException e) {
            Log.e(TAG, "Error in removing " + str + ": " + e);
            return false;
        }
    }

    public String[] list(String str) {
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        try {
            Cursor cursorQuery = this.mDb.query(TABLENAME, new String[]{"name"}, "owner=? AND name LIKE ? ESCAPE '\\'", new String[]{Integer.toString(callingUid), DatabaseUtils.escapeForLike(str) + "%"}, null, null, "name ASC");
            try {
                if (cursorQuery.moveToFirst()) {
                    do {
                        arrayList.add(cursorQuery.getString(0).substring(str.length()));
                    } while (cursorQuery.moveToNext());
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } finally {
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in listing " + str + ": " + e);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
