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
import java.util.List;

/* loaded from: classes5.dex */
public class ConnectivityBlobStore {
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS blob_table (owner INTEGER,name BLOB,blob BLOB,UNIQUE(owner,"
                + " name));";
    private static final String ROOT_DIR = "/data/misc/connectivityblobdb/";
    private static final String TABLENAME = "blob_table";
    private static final String TAG = ConnectivityBlobStore.class.getSimpleName();
    private final SQLiteDatabase mDb;

    public ConnectivityBlobStore(String dbName) {
        this(new File(ROOT_DIR + dbName));
    }

    public ConnectivityBlobStore(File file) {
        SQLiteDatabase.OpenParams params =
                new SQLiteDatabase.OpenParams.Builder()
                        .addOpenFlags(268435456)
                        .addOpenFlags(536870912)
                        .build();
        this.mDb = SQLiteDatabase.openDatabase(file, params);
        this.mDb.execSQL(CREATE_TABLE);
    }

    public boolean put(String name, byte[] blob) {
        int ownerUid = Binder.getCallingUid();
        ContentValues values = new ContentValues();
        values.put("owner", Integer.valueOf(ownerUid));
        values.put("name", name);
        values.put("blob", blob);
        long res = this.mDb.replace(TABLENAME, null, values);
        return res > 0;
    }

    public byte[] get(String name) {
        int ownerUid = Binder.getCallingUid();
        try {
            Cursor cursor =
                    this.mDb.query(
                            TABLENAME,
                            new String[] {"blob"},
                            "owner=? AND name=?",
                            new String[] {Integer.toString(ownerUid), name},
                            null,
                            null,
                            null);
            try {
                if (cursor.moveToFirst()) {
                    byte[] blob = cursor.getBlob(0);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return blob;
                }
                if (cursor != null) {
                    cursor.close();
                    return null;
                }
                return null;
            } finally {
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in getting " + name + ": " + e);
            return null;
        }
    }

    public boolean remove(String name) {
        int ownerUid = Binder.getCallingUid();
        try {
            int res =
                    this.mDb.delete(
                            TABLENAME,
                            "owner=? AND name=?",
                            new String[] {Integer.toString(ownerUid), name});
            return res > 0;
        } catch (SQLException e) {
            Log.e(TAG, "Error in removing " + name + ": " + e);
            return false;
        }
    }

    public String[] list(String prefix) {
        int ownerUid = Binder.getCallingUid();
        List<String> names = new ArrayList<>();
        try {
            Cursor cursor =
                    this.mDb.query(
                            TABLENAME,
                            new String[] {"name"},
                            "owner=? AND name LIKE ? ESCAPE '\\'",
                            new String[] {
                                Integer.toString(ownerUid),
                                DatabaseUtils.escapeForLike(prefix) + "%"
                            },
                            null,
                            null,
                            "name ASC");
            try {
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(0);
                        names.add(name.substring(prefix.length()));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
            } finally {
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in listing " + prefix + ": " + e);
        }
        return (String[]) names.toArray(new String[names.size()]);
    }
}
