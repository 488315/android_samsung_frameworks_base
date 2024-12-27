package com.android.server.accounts;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AccountsDb implements AutoCloseable {
    public static final String[] ACCOUNT_TYPE_COUNT_PROJECTION = {"type", "count(type)"};
    public static final String[] COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN = {"type", "authtoken"};
    public static final String[] COLUMNS_EXTRAS_KEY_AND_VALUE = {"key", "value"};
    public final Context mContext;
    public final DeDatabaseHelper mDeDatabase;
    public volatile SQLiteStatement mDebugStatementForLogging;
    public final File mPreNDatabaseFile;
    public final Object mDebugStatementLock = new Object();
    public volatile long mDebugDbInsertionPoint = -1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CeDatabaseHelper extends SQLiteOpenHelper {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.i("AccountsDb", "Creating CE database " + getDatabaseName());
            sQLiteDatabase.execSQL(
                    "CREATE TABLE accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT"
                        + " NULL, type TEXT NOT NULL, password TEXT, UNIQUE(name,type))");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE authtokens (  _id INTEGER PRIMARY KEY AUTOINCREMENT,  accounts_id"
                        + " INTEGER NOT NULL, type TEXT NOT NULL,  authtoken TEXT,  UNIQUE"
                        + " (accounts_id,type))");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE extras ( _id INTEGER PRIMARY KEY AUTOINCREMENT, accounts_id"
                        + " INTEGER, key TEXT NOT NULL, value TEXT, UNIQUE(accounts_id,key))");
            sQLiteDatabase.execSQL(
                    " CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM"
                        + " authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras    "
                        + " WHERE accounts_id=OLD._id ; END");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.e("AccountsDb", "onDowngrade: recreate accounts CE table");
            AccountsDb.m143$$Nest$smresetDatabase(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (Log.isLoggable("AccountsDb", 2)) {
                Log.v("AccountsDb", "opened database accounts_ce.db");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, i2, "Upgrade CE from version ", " to version ", "AccountsDb");
            if (i == 9) {
                if (Log.isLoggable("AccountsDb", 2)) {
                    Log.v("AccountsDb", "onUpgrade upgrading to v10");
                }
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS meta");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS shared_accounts");
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS accountsDelete");
                sQLiteDatabase.execSQL(
                        " CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM"
                            + " authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras    "
                            + " WHERE accounts_id=OLD._id ; END");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS grants");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS debug_table");
                i++;
            }
            if (i != i2) {
                Log.e("AccountsDb", "failed to upgrade version " + i + " to version " + i2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeDatabaseHelper extends SQLiteOpenHelper {
        public volatile boolean mCeAttached;
        public final int mUserId;

        public DeDatabaseHelper(Context context, String str, int i) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 3);
            this.mUserId = i;
        }

        public final SQLiteDatabase getReadableDatabaseUserIsUnlocked() {
            if (!this.mCeAttached) {
                Log.wtf(
                        "AccountsDb",
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.mUserId,
                                new StringBuilder(
                                        "getReadableDatabaseUserIsUnlocked called while user "),
                                " is still locked. CE database is not yet available."),
                        new Throwable());
            }
            return getReadableDatabase();
        }

        public final SQLiteDatabase getWritableDatabaseUserIsUnlocked() {
            if (!this.mCeAttached) {
                Log.wtf(
                        "AccountsDb",
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.mUserId,
                                new StringBuilder(
                                        "getWritableDatabaseUserIsUnlocked called while user "),
                                " is still locked. CE database is not yet available."),
                        new Throwable());
            }
            return getWritableDatabase();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.i("AccountsDb", "Creating DE database for user " + this.mUserId);
            sQLiteDatabase.execSQL(
                    "CREATE TABLE accounts ( _id INTEGER PRIMARY KEY, name TEXT NOT NULL, type TEXT"
                        + " NOT NULL, previous_name TEXT, last_password_entry_time_millis_epoch"
                        + " INTEGER DEFAULT 0, UNIQUE(name,type))");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE meta ( key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type STRING"
                        + " NOT NULL,  uid INTEGER NOT NULL,  UNIQUE"
                        + " (accounts_id,auth_token_type,uid))");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE shared_accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name"
                        + " TEXT NOT NULL, type TEXT NOT NULL, UNIQUE(name,type))");
            sQLiteDatabase.execSQL(
                    " CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM grants  "
                        + "   WHERE accounts_id=OLD._id ; END");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE debug_table ( _id INTEGER,action_type TEXT NOT NULL, time"
                        + " DATETIME,caller_uid INTEGER NOT NULL,table_name TEXT NOT"
                        + " NULL,primary_key INTEGER PRIMARY KEY)");
            sQLiteDatabase.execSQL("CREATE INDEX timestamp_index ON debug_table (time)");
            sQLiteDatabase.execSQL(
                    "CREATE TABLE visibility ( accounts_id INTEGER NOT NULL, _package TEXT NOT"
                        + " NULL, value INTEGER, PRIMARY KEY(accounts_id,_package))");
            sQLiteDatabase.execSQL(
                    " CREATE TRIGGER accountsDeleteVisibility DELETE ON accounts BEGIN   DELETE"
                        + " FROM visibility     WHERE accounts_id=OLD._id ; END");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.e("AccountsDb", "onDowngrade: recreate accounts DE table");
            AccountsDb.m143$$Nest$smresetDatabase(sQLiteDatabase);
            onCreate(sQLiteDatabase);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (Log.isLoggable("AccountsDb", 2)) {
                Log.v("AccountsDb", "opened database accounts_de.db");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            AccountsDb$CeDatabaseHelper$$ExternalSyntheticOutline0.m(
                    i, i2, "upgrade from version ", " to version ", "AccountsDb");
            if (i == 1) {
                sQLiteDatabase.execSQL(
                        "CREATE TABLE visibility ( accounts_id INTEGER NOT NULL, _package TEXT NOT"
                            + " NULL, value INTEGER, PRIMARY KEY(accounts_id,_package))");
                sQLiteDatabase.execSQL(
                        " CREATE TRIGGER accountsDeleteVisibility DELETE ON accounts BEGIN   DELETE"
                            + " FROM visibility     WHERE accounts_id=OLD._id ; END");
                i = 3;
            }
            if (i == 2) {
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS accountsDeleteVisibility");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS visibility");
                sQLiteDatabase.execSQL(
                        "CREATE TABLE visibility ( accounts_id INTEGER NOT NULL, _package TEXT NOT"
                            + " NULL, value INTEGER, PRIMARY KEY(accounts_id,_package))");
                sQLiteDatabase.execSQL(
                        " CREATE TRIGGER accountsDeleteVisibility DELETE ON accounts BEGIN   DELETE"
                            + " FROM visibility     WHERE accounts_id=OLD._id ; END");
                i++;
            }
            if (i != i2) {
                Log.e("AccountsDb", "failed to upgrade version " + i + " to version " + i2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PreNDatabaseHelper extends SQLiteOpenHelper {
        public final Context mContext;
        public final int mUserId;

        public PreNDatabaseHelper(Context context, String str, int i) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 9);
            this.mContext = context;
            this.mUserId = i;
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onCreate(SQLiteDatabase sQLiteDatabase) {
            throw new IllegalStateException("Legacy database cannot be created - only upgraded!");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (Log.isLoggable("AccountsDb", 2)) {
                Log.v("AccountsDb", "opened database accounts.db");
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.e("AccountsDb", "upgrade from version " + i + " to version " + i2);
            if (i == 1) {
                i++;
            }
            if (i == 2) {
                sQLiteDatabase.execSQL(
                        "CREATE TABLE grants (  accounts_id INTEGER NOT NULL, auth_token_type"
                            + " STRING NOT NULL,  uid INTEGER NOT NULL,  UNIQUE"
                            + " (accounts_id,auth_token_type,uid))");
                sQLiteDatabase.execSQL("DROP TRIGGER accountsDelete");
                sQLiteDatabase.execSQL(
                        " CREATE TRIGGER accountsDelete DELETE ON accounts BEGIN   DELETE FROM"
                            + " authtokens     WHERE accounts_id=OLD._id ;   DELETE FROM extras    "
                            + " WHERE accounts_id=OLD._id ;   DELETE FROM grants     WHERE"
                            + " accounts_id=OLD._id ; END");
                i++;
            }
            if (i == 3) {
                sQLiteDatabase.execSQL(
                        "UPDATE accounts SET type = 'com.google' WHERE type == 'com.google.GAIA'");
                i++;
            }
            if (i == 4) {
                sQLiteDatabase.execSQL(
                        "CREATE TABLE shared_accounts ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name"
                            + " TEXT NOT NULL, type TEXT NOT NULL, UNIQUE(name,type))");
                i++;
            }
            if (i == 5) {
                sQLiteDatabase.execSQL("ALTER TABLE accounts ADD COLUMN previous_name");
                i++;
            }
            if (i == 6) {
                sQLiteDatabase.execSQL(
                        "ALTER TABLE accounts ADD COLUMN last_password_entry_time_millis_epoch"
                            + " DEFAULT 0");
                i++;
            }
            if (i == 7) {
                sQLiteDatabase.execSQL(
                        "CREATE TABLE debug_table ( _id INTEGER,action_type TEXT NOT NULL, time"
                            + " DATETIME,caller_uid INTEGER NOT NULL,table_name TEXT NOT"
                            + " NULL,primary_key INTEGER PRIMARY KEY)");
                sQLiteDatabase.execSQL("CREATE INDEX timestamp_index ON debug_table (time)");
                i++;
            }
            if (i == 8) {
                Context context = this.mContext;
                int i3 = this.mUserId;
                Intent intent = AccountManagerService.ACCOUNTS_CHANGED_INTENT;
                for (Map.Entry entry :
                        ((LinkedHashMap)
                                        AccountManagerService.getAuthenticatorTypeAndUIDForUser(
                                                new AccountAuthenticatorCache(context), i3))
                                .entrySet()) {
                    String str = (String) entry.getKey();
                    Integer num = (Integer) entry.getValue();
                    num.getClass();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("key", "auth_uid_for_type:" + str);
                    contentValues.put("value", num);
                    sQLiteDatabase.insert("meta", null, contentValues);
                }
                i++;
            }
            if (i != i2) {
                Log.e("AccountsDb", "failed to upgrade version " + i + " to version " + i2);
            }
        }
    }

    /* renamed from: -$$Nest$smresetDatabase, reason: not valid java name */
    public static void m143$$Nest$smresetDatabase(SQLiteDatabase sQLiteDatabase) {
        Cursor rawQuery =
                sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
        while (rawQuery.moveToNext()) {
            try {
                String string = rawQuery.getString(0);
                if (!"android_metadata".equals(string) && !"sqlite_sequence".equals(string)) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + string);
                }
            } finally {
            }
        }
        rawQuery.close();
        rawQuery =
                sQLiteDatabase.rawQuery(
                        "SELECT name FROM sqlite_master WHERE type ='trigger'", null);
        while (rawQuery.moveToNext()) {
            try {
                sQLiteDatabase.execSQL("DROP TRIGGER IF EXISTS " + rawQuery.getString(0));
            } finally {
            }
        }
        rawQuery.close();
    }

    public AccountsDb(DeDatabaseHelper deDatabaseHelper, Context context, File file) {
        this.mDeDatabase = deDatabaseHelper;
        this.mContext = context;
        this.mPreNDatabaseFile = file;
    }

    public static AccountsDb create(Context context, int i, File file, File file2) {
        boolean exists = file2.exists();
        DeDatabaseHelper deDatabaseHelper = new DeDatabaseHelper(context, file2.getPath(), i);
        if (!exists && file.exists()) {
            PreNDatabaseHelper preNDatabaseHelper =
                    new PreNDatabaseHelper(context, file.getPath(), i);
            preNDatabaseHelper.getWritableDatabase();
            preNDatabaseHelper.close();
            Log.i("AccountsDb", "Migrate pre-N database to DE preNDbFile=" + file);
            SQLiteDatabase writableDatabase = deDatabaseHelper.getWritableDatabase();
            writableDatabase.execSQL("ATTACH DATABASE '" + file.getPath() + "' AS preNDb");
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "INSERT INTO accounts(_id,name,type, previous_name,"
                        + " last_password_entry_time_millis_epoch) SELECT _id,name,type,"
                        + " previous_name, last_password_entry_time_millis_epoch FROM"
                        + " preNDb.accounts");
            writableDatabase.execSQL(
                    "INSERT INTO shared_accounts(_id,name,type) SELECT _id,name,type FROM"
                        + " preNDb.shared_accounts");
            writableDatabase.execSQL(
                    "INSERT INTO"
                        + " debug_table(_id,action_type,time,caller_uid,table_name,primary_key)"
                        + " SELECT _id,action_type,time,caller_uid,table_name,primary_key FROM"
                        + " preNDb.debug_table");
            writableDatabase.execSQL(
                    "INSERT INTO grants(accounts_id,auth_token_type,uid) SELECT"
                        + " accounts_id,auth_token_type,uid FROM preNDb.grants");
            writableDatabase.execSQL(
                    "INSERT INTO meta(key,value) SELECT key,value FROM preNDb.meta");
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
            writableDatabase.execSQL("DETACH DATABASE preNDb");
        }
        return new AccountsDb(deDatabaseHelper, context, file);
    }

    public final void attachCeDatabase(File file) {
        Context context = this.mContext;
        File file2 = this.mPreNDatabaseFile;
        int i = CeDatabaseHelper.$r8$clinit;
        boolean exists = file.exists();
        if (Log.isLoggable("AccountsDb", 2)) {
            Log.v(
                    "AccountsDb",
                    "CeDatabaseHelper.create ceDatabaseFile="
                            + file
                            + " oldDbExists="
                            + file2.exists()
                            + " newDbExists="
                            + exists);
        }
        boolean z = false;
        if (!exists && file2.exists()) {
            Slog.i("AccountsDb", "Moving pre-N DB " + file2 + " to CE " + file);
            try {
                FileUtils.copyFileOrThrow(file2, file);
                z = true;
            } catch (IOException e) {
                Slog.e("AccountsDb", "Cannot copy file to " + file + " from " + file2, e);
                if (!SQLiteDatabase.deleteDatabase(file)) {
                    Log.w("AccountsDb", "Database at " + file + " was not deleted successfully");
                }
            }
        }
        CeDatabaseHelper ceDatabaseHelper = new CeDatabaseHelper(context, file.getPath(), null, 10);
        ceDatabaseHelper.getWritableDatabase();
        ceDatabaseHelper.close();
        if (z) {
            Slog.i("AccountsDb", "Migration complete - removing pre-N db " + file2);
            if (!SQLiteDatabase.deleteDatabase(file2)) {
                Slog.e("AccountsDb", "Cannot remove pre-N db " + file2);
            }
        }
        this.mDeDatabase
                .getWritableDatabase()
                .execSQL("ATTACH DATABASE '" + file.getPath() + "' AS ceDb");
        this.mDeDatabase.mCeAttached = true;
    }

    public final void beginTransaction() {
        this.mDeDatabase.getWritableDatabase().beginTransaction();
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.mDeDatabase.close();
    }

    public final void deleteCeAccount(long j) {
        this.mDeDatabase
                .getWritableDatabaseUserIsUnlocked()
                .delete(
                        "ceDb.accounts",
                        DeviceIdleController$$ExternalSyntheticOutline0.m(j, "_id="),
                        null);
    }

    public final boolean deleteDeAccount(long j) {
        return this.mDeDatabase
                        .getWritableDatabase()
                        .delete(
                                "accounts",
                                DeviceIdleController$$ExternalSyntheticOutline0.m(j, "_id="),
                                null)
                > 0;
    }

    public final void deleteMetaByAuthTypeAndUid(int i, String str) {
        this.mDeDatabase
                .getWritableDatabase()
                .delete(
                        "meta",
                        "key=? AND value=?",
                        new String[] {
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                    "auth_uid_for_type:", str),
                            String.valueOf(i)
                        });
    }

    public final void dumpDeAccountsTable(PrintWriter printWriter) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                "accounts",
                                ACCOUNT_TYPE_COUNT_PROJECTION,
                                null,
                                null,
                                "type",
                                null,
                                null);
        while (query.moveToNext()) {
            try {
                printWriter.println(query.getString(0) + "," + query.getString(1));
            } catch (Throwable th) {
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
        query.close();
    }

    public final void dumpDebugTable(PrintWriter printWriter) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query("debug_table", null, null, null, null, null, "time");
        printWriter.println("AccountId, Action_Type, timestamp, UID, TableName, Key");
        printWriter.println("Accounts History");
        while (query.moveToNext()) {
            try {
                printWriter.println(
                        query.getString(0)
                                + ","
                                + query.getString(1)
                                + ","
                                + query.getString(2)
                                + ","
                                + query.getString(3)
                                + ","
                                + query.getString(4)
                                + ","
                                + query.getString(5));
            } finally {
                query.close();
            }
        }
    }

    public final void endTransaction() {
        this.mDeDatabase.getWritableDatabase().endTransaction();
    }

    public final String findAccountPasswordByNameAndType(String str, String str2) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabaseUserIsUnlocked()
                        .query(
                                "ceDb.accounts",
                                new String[] {"password"},
                                "name=? AND type=?",
                                new String[] {str, str2},
                                null,
                                null,
                                null);
        try {
            if (!query.moveToNext()) {
                query.close();
                return null;
            }
            String string = query.getString(0);
            query.close();
            return string;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final Integer findAccountVisibility(Account account, String str) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                LauncherConfigurationInternal
                                        .KEY_SUPPLEMENT_SERVICE_PAGE_VISIBILITY_BOOLEAN,
                                new String[] {"value"},
                                "accounts_id=(select _id FROM accounts WHERE name=? AND type=?) AND"
                                    + " _package=? ",
                                new String[] {account.name, account.type, str},
                                null,
                                null,
                                null);
        try {
            if (query.moveToNext()) {
                return Integer.valueOf(query.getInt(0));
            }
            query.close();
            return null;
        } finally {
            query.close();
        }
    }

    public final List findAllAccountGrants() {
        Cursor rawQuery =
                this.mDeDatabase
                        .getReadableDatabase()
                        .rawQuery(
                                "SELECT name, uid FROM accounts, grants WHERE accounts_id=_id",
                                null);
        if (rawQuery != null) {
            try {
                if (rawQuery.moveToFirst()) {
                    ArrayList arrayList = new ArrayList();
                    do {
                        arrayList.add(
                                Pair.create(
                                        rawQuery.getString(0),
                                        Integer.valueOf(rawQuery.getInt(1))));
                    } while (rawQuery.moveToNext());
                    rawQuery.close();
                    return arrayList;
                }
            } catch (Throwable th) {
                if (rawQuery != null) {
                    try {
                        rawQuery.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        List emptyList = Collections.emptyList();
        if (rawQuery != null) {
            rawQuery.close();
        }
        return emptyList;
    }

    public final Map findAllDeAccounts() {
        SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Cursor query =
                readableDatabase.query(
                        "accounts",
                        new String[] {KnoxCustomManagerService.ID, "type", "name"},
                        null,
                        null,
                        null,
                        null,
                        KnoxCustomManagerService.ID);
        while (query.moveToNext()) {
            try {
                long j = query.getLong(0);
                linkedHashMap.put(
                        Long.valueOf(j), new Account(query.getString(2), query.getString(1)));
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return linkedHashMap;
    }

    public final List findAllUidGrants() {
        SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor query =
                readableDatabase.query(
                        "grants", new String[] {"uid"}, null, null, "uid", null, null);
        while (query.moveToNext()) {
            try {
                arrayList.add(Integer.valueOf(query.getInt(0)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    public final Map findAllVisibilityValues() {
        SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        HashMap hashMap = new HashMap();
        Cursor rawQuery =
                readableDatabase.rawQuery(
                        "SELECT visibility._package, visibility.value, accounts.name, accounts.type"
                            + " FROM visibility JOIN accounts ON accounts._id ="
                            + " visibility.accounts_id",
                        null);
        while (rawQuery.moveToNext()) {
            try {
                String string = rawQuery.getString(0);
                Integer valueOf = Integer.valueOf(rawQuery.getInt(1));
                Account account = new Account(rawQuery.getString(2), rawQuery.getString(3));
                Map map = (Map) hashMap.get(account);
                if (map == null) {
                    map = new HashMap();
                    hashMap.put(account, map);
                }
                map.put(string, valueOf);
            } catch (Throwable th) {
                rawQuery.close();
                throw th;
            }
        }
        rawQuery.close();
        return hashMap;
    }

    public final Map findAuthTokensByAccount(Account account) {
        SQLiteDatabase readableDatabaseUserIsUnlocked =
                this.mDeDatabase.getReadableDatabaseUserIsUnlocked();
        HashMap hashMap = new HashMap();
        Cursor query =
                readableDatabaseUserIsUnlocked.query(
                        "ceDb.authtokens",
                        COLUMNS_AUTHTOKENS_TYPE_AND_AUTHTOKEN,
                        "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)",
                        new String[] {account.name, account.type},
                        null,
                        null,
                        null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } finally {
                query.close();
            }
        }
        return hashMap;
    }

    public final long findCeAccountId(Account account) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabaseUserIsUnlocked()
                        .query(
                                "ceDb.accounts",
                                new String[] {KnoxCustomManagerService.ID},
                                "name=? AND type=?",
                                new String[] {account.name, account.type},
                                null,
                                null,
                                null);
        try {
            if (!query.moveToNext()) {
                query.close();
                return -1L;
            }
            long j = query.getLong(0);
            query.close();
            return j;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final long findDeAccountId(Account account) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                "accounts",
                                new String[] {KnoxCustomManagerService.ID},
                                "name=? AND type=?",
                                new String[] {account.name, account.type},
                                null,
                                null,
                                null);
        try {
            if (!query.moveToNext()) {
                query.close();
                return -1L;
            }
            long j = query.getLong(0);
            query.close();
            return j;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final String findDeAccountPreviousName(Account account) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                "accounts",
                                new String[] {"previous_name"},
                                "name=? AND type=?",
                                new String[] {account.name, account.type},
                                null,
                                null,
                                null);
        try {
            if (!query.moveToNext()) {
                query.close();
                return null;
            }
            String string = query.getString(0);
            query.close();
            return string;
        } catch (Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final long findExtrasIdByAccountId(long j, String str) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabaseUserIsUnlocked()
                        .query(
                                "ceDb.extras",
                                new String[] {KnoxCustomManagerService.ID},
                                "accounts_id=" + j + " AND key=?",
                                new String[] {str},
                                null,
                                null,
                                null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            query.close();
            return -1L;
        } finally {
            query.close();
        }
    }

    public final Map findMetaAuthUid() {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                "meta",
                                new String[] {"key", "value"},
                                "key LIKE ?",
                                new String[] {"auth_uid_for_type:%"},
                                null,
                                null,
                                "key");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (query.moveToNext()) {
            try {
                String str = TextUtils.split(query.getString(0), ":")[1];
                String string = query.getString(1);
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(string)) {
                    linkedHashMap.put(str, Integer.valueOf(Integer.parseInt(query.getString(1))));
                }
                Slog.e(
                        "AccountsDb",
                        "Auth type empty: "
                                + TextUtils.isEmpty(str)
                                + ", uid empty: "
                                + TextUtils.isEmpty(string));
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        query.close();
        return linkedHashMap;
    }

    public final long findSharedAccountId(Account account) {
        Cursor query =
                this.mDeDatabase
                        .getReadableDatabase()
                        .query(
                                "shared_accounts",
                                new String[] {KnoxCustomManagerService.ID},
                                "name=? AND type=?",
                                new String[] {account.name, account.type},
                                null,
                                null,
                                null);
        try {
            if (query.moveToNext()) {
                return query.getLong(0);
            }
            query.close();
            return -1L;
        } finally {
            query.close();
        }
    }

    public final Map findUserExtrasForAccount(Account account) {
        SQLiteDatabase readableDatabaseUserIsUnlocked =
                this.mDeDatabase.getReadableDatabaseUserIsUnlocked();
        HashMap hashMap = new HashMap();
        Cursor query =
                readableDatabaseUserIsUnlocked.query(
                        "ceDb.extras",
                        COLUMNS_EXTRAS_KEY_AND_VALUE,
                        "accounts_id=(select _id FROM accounts WHERE name=? AND type=?)",
                        new String[] {account.name, account.type},
                        null,
                        null,
                        null);
        while (query.moveToNext()) {
            try {
                hashMap.put(query.getString(0), query.getString(1));
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        query.close();
        return hashMap;
    }

    public final List getSharedAccounts() {
        SQLiteDatabase readableDatabase = this.mDeDatabase.getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            cursor =
                    readableDatabase.query(
                            "shared_accounts",
                            new String[] {"name", "type"},
                            null,
                            null,
                            null,
                            null,
                            null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("name");
                int columnIndex2 = cursor.getColumnIndex("type");
                do {
                    arrayList.add(
                            new Account(
                                    cursor.getString(columnIndex), cursor.getString(columnIndex2)));
                } while (cursor.moveToNext());
            }
            return arrayList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final SQLiteStatement getStatementForLogging() {
        if (this.mDebugStatementForLogging != null) {
            return this.mDebugStatementForLogging;
        }
        try {
            this.mDebugStatementForLogging =
                    this.mDeDatabase
                            .getWritableDatabase()
                            .compileStatement(
                                    "INSERT OR REPLACE INTO debug_table VALUES (?,?,?,?,?,?)");
            return this.mDebugStatementForLogging;
        } catch (SQLiteException e) {
            Log.e("AccountsDb", "Failed to open debug table" + e);
            return null;
        }
    }

    public final long insertDeAccount(Account account, long j) {
        SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KnoxCustomManagerService.ID, Long.valueOf(j));
        contentValues.put("name", account.name);
        contentValues.put("type", account.type);
        contentValues.put(
                "last_password_entry_time_millis_epoch", Long.valueOf(System.currentTimeMillis()));
        return writableDatabase.insert("accounts", "name", contentValues);
    }

    public final long insertExtra(String str, long j, String str2) {
        SQLiteDatabase writableDatabaseUserIsUnlocked =
                this.mDeDatabase.getWritableDatabaseUserIsUnlocked();
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("key", str);
        m.put("accounts_id", Long.valueOf(j));
        m.put("value", str2);
        return writableDatabaseUserIsUnlocked.insert("ceDb.extras", "key", m);
    }

    public final void insertOrReplaceMetaAuthTypeAndUid(int i, String str) {
        SQLiteDatabase writableDatabase = this.mDeDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", "auth_uid_for_type:" + str);
        contentValues.put("value", Integer.valueOf(i));
        writableDatabase.insertWithOnConflict("meta", null, contentValues, 5);
    }

    public final void setTransactionSuccessful() {
        this.mDeDatabase.getWritableDatabase().setTransactionSuccessful();
    }
}
