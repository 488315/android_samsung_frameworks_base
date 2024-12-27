package com.android.server.locksettings.recoverablekeystore.storage;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RecoverableKeyStoreDbHelper extends SQLiteOpenHelper {
    public static void addColumnToTable(
            SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        Log.d(
                "RecoverableKeyStoreDbHp",
                XmlUtils$$ExternalSyntheticOutline0.m("Adding column ", str2, " to ", str, "."));
        StringBuilder sb = new StringBuilder("ALTER TABLE ");
        sb.append(str);
        sb.append(" ADD COLUMN ");
        sQLiteDatabase.execSQL(
                BootReceiver$$ExternalSyntheticOutline0.m(sb, str2, " ", str3) + ";");
    }

    public static void dropAllKnownTables(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS keys");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS user_metadata");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS recovery_service_metadata");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS root_of_trust");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(
                "CREATE TABLE keys( _id INTEGER PRIMARY KEY,user_id INTEGER,uid INTEGER,alias"
                    + " TEXT,nonce BLOB,wrapped_key BLOB,platform_key_generation_id"
                    + " INTEGER,last_synced_at INTEGER,recovery_status INTEGER,key_metadata"
                    + " BLOB,UNIQUE(uid,alias))");
        sQLiteDatabase.execSQL(
                "CREATE TABLE user_metadata( _id INTEGER PRIMARY KEY,user_id INTEGER"
                    + " UNIQUE,platform_key_generation_id INTEGER,user_serial_number INTEGER"
                    + " DEFAULT -1,bad_remote_guess_counter INTEGER DEFAULT 0)");
        sQLiteDatabase.execSQL(
                "CREATE TABLE recovery_service_metadata (_id INTEGER PRIMARY KEY,user_id"
                    + " INTEGER,uid INTEGER,snapshot_version INTEGER,should_create_snapshot"
                    + " INTEGER,active_root_of_trust TEXT,public_key BLOB,cert_path"
                    + " BLOB,cert_serial INTEGER,secret_types TEXT,counter_id INTEGER,server_params"
                    + " BLOB,UNIQUE(user_id,uid))");
        sQLiteDatabase.execSQL(
                "CREATE TABLE root_of_trust (_id INTEGER PRIMARY KEY,user_id INTEGER,uid"
                    + " INTEGER,root_alias TEXT,cert_path BLOB,cert_serial"
                    + " INTEGER,UNIQUE(user_id,uid,root_alias))");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Log.e(
                "RecoverableKeyStoreDbHp",
                "Recreating recoverablekeystore after unexpected version downgrade.");
        dropAllKnownTables(sQLiteDatabase);
        onCreate(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        try {
            if (i < 2) {
                dropAllKnownTables(sQLiteDatabase);
                onCreate(sQLiteDatabase);
                return;
            }
            if (i < 3 && i2 >= 3) {
                addColumnToTable(sQLiteDatabase, "recovery_service_metadata", "cert_path", "BLOB");
                addColumnToTable(
                        sQLiteDatabase, "recovery_service_metadata", "cert_serial", "INTEGER");
                i = 3;
            }
            if (i < 4 && i2 >= 4) {
                Log.d(
                        "RecoverableKeyStoreDbHp",
                        "Updating recoverable keystore database to version 4");
                sQLiteDatabase.execSQL(
                        "CREATE TABLE root_of_trust (_id INTEGER PRIMARY KEY,user_id INTEGER,uid"
                            + " INTEGER,root_alias TEXT,cert_path BLOB,cert_serial"
                            + " INTEGER,UNIQUE(user_id,uid,root_alias))");
                addColumnToTable(
                        sQLiteDatabase,
                        "recovery_service_metadata",
                        "active_root_of_trust",
                        "TEXT");
                i = 4;
            }
            if (i < 5 && i2 >= 5) {
                Log.d(
                        "RecoverableKeyStoreDbHp",
                        "Updating recoverable keystore database to version 5");
                addColumnToTable(sQLiteDatabase, "keys", "key_metadata", "BLOB");
                i = 5;
            }
            if (i < 6 && i2 >= 6) {
                Log.d(
                        "RecoverableKeyStoreDbHp",
                        "Updating recoverable keystore database to version 6");
                addColumnToTable(
                        sQLiteDatabase,
                        "user_metadata",
                        "user_serial_number",
                        "INTEGER DEFAULT -1");
                i = 6;
            }
            if (i < 7 && i2 >= 7) {
                try {
                    Log.d(
                            "RecoverableKeyStoreDbHp",
                            "Updating recoverable keystore database to version 7");
                    addColumnToTable(
                            sQLiteDatabase,
                            "user_metadata",
                            "bad_remote_guess_counter",
                            "INTEGER DEFAULT 0");
                } catch (SQLiteException e) {
                    Log.w(
                            "RecoverableKeyStoreDbHp",
                            "Column was added without version update - ignore error",
                            e);
                }
                i = 7;
            }
            if (i != i2) {
                Log.e(
                        "RecoverableKeyStoreDbHp",
                        "Failed to update recoverablekeystore database to the most recent version");
            }
        } catch (SQLiteException e2) {
            Log.e(
                    "RecoverableKeyStoreDbHp",
                    "Recreating recoverablekeystore after unexpected upgrade error.",
                    e2);
            dropAllKnownTables(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }
    }
}
