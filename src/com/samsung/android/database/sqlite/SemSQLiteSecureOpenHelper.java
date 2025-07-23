package com.samsung.android.database.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteGlobal;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.util.Objects;

/* loaded from: classes6.dex */
public abstract class SemSQLiteSecureOpenHelper implements AutoCloseable {
    private static final String TAG = "SemSQLiteSecureOpenHelper";
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mIsInitializing;
    private final int mMinimumSupportedVersion;
    private final String mName;
    private final int mNewVersion;
    private SQLiteDatabase.OpenParams.Builder mOpenParamsBuilder;

    public void onBeforeDelete(SQLiteDatabase sQLiteDatabase) {
    }

    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SemSQLiteSecureOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, (DatabaseErrorHandler) null);
    }

    public SemSQLiteSecureOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, cursorFactory, i, 0, databaseErrorHandler);
    }

    public SemSQLiteSecureOpenHelper(Context context, String str, int i, SQLiteDatabase.OpenParams openParams) {
        this(context, str, i, 0, openParams.toBuilder());
    }

    public SemSQLiteSecureOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, int i2, DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, i, i2, new SQLiteDatabase.OpenParams.Builder());
        this.mOpenParamsBuilder.setCursorFactory(cursorFactory);
        this.mOpenParamsBuilder.setErrorHandler(databaseErrorHandler);
    }

    private SemSQLiteSecureOpenHelper(Context context, String str, int i, int i2, SQLiteDatabase.OpenParams.Builder builder) {
        Objects.requireNonNull(builder);
        if (i < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + i);
        }
        this.mContext = context;
        this.mName = str;
        this.mNewVersion = i;
        this.mMinimumSupportedVersion = Math.max(0, i2);
        setOpenParamsBuilder(builder);
    }

    public String getDatabaseName() {
        return this.mName;
    }

    public void setWriteAheadLoggingEnabled(boolean z) {
        synchronized (this) {
            if (this.mOpenParamsBuilder.isWriteAheadLoggingEnabled() != z) {
                SQLiteDatabase sQLiteDatabase = this.mDatabase;
                if (sQLiteDatabase != null && sQLiteDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
                    if (z) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                }
                this.mOpenParamsBuilder.setWriteAheadLoggingEnabled(z);
            }
            this.mOpenParamsBuilder.removeOpenFlags(Integer.MIN_VALUE);
            if (!z) {
                this.mOpenParamsBuilder.addOpenFlags(1024);
            } else {
                this.mOpenParamsBuilder.removeOpenFlags(1024);
            }
        }
    }

    @Deprecated
    public void setLookasideConfig(int i, int i2) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Lookaside memory config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setLookasideConfig(i, i2);
        }
    }

    @Deprecated
    public void setOpenParams(SQLiteDatabase.OpenParams openParams) {
        Objects.requireNonNull(openParams);
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("OpenParams cannot be set after opening the database");
            }
            setOpenParamsBuilder(new SQLiteDatabase.OpenParams.Builder(openParams));
        }
    }

    private void setOpenParamsBuilder(SQLiteDatabase.OpenParams.Builder builder) {
        this.mOpenParamsBuilder = builder;
        builder.addOpenFlags(268435456);
    }

    public void setIdleConnectionShrinkTimeout(long j) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Shrink timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetIdleConnectionShrinkTimeout(j);
        }
    }

    public void setSeparateCacheModeEnabled(boolean z) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Separate cache config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetSeparateCacheModeEnabled(z);
        }
    }

    @Deprecated
    public void setCacheSize(int i) {
        if (i < 0 || i > 8388608) {
            throw new IllegalArgumentException("The cache size should not be negative value. Also, it should be less than soft heap size (8M)");
        }
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.mDatabase.setCacheSize(i / SQLiteGlobal.getDefaultPageSize());
            }
            this.mOpenParamsBuilder.semSetCacheSize(i);
        }
    }

    public void setUserDataRecoveryEnabled(boolean z) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Database Recovery config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setUserDataRecoveryEnabled(z);
        }
    }

    public SQLiteDatabase getWritableDatabase(byte[] bArr) {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(true, bArr);
        }
        return databaseLocked;
    }

    public SQLiteDatabase getReadableDatabase(byte[] bArr) {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(false, bArr);
        }
        return databaseLocked;
    }

    private SQLiteDatabase getDatabaseLocked(boolean z, byte[] bArr) {
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null) {
            if (!sQLiteDatabase.isOpen()) {
                this.mDatabase = null;
            } else if (!z || !this.mDatabase.isReadOnly()) {
                return this.mDatabase;
            }
        }
        if (this.mIsInitializing) {
            throw new IllegalStateException("getDatabase called recursively");
        }
        SQLiteDatabase sQLiteDatabase2 = this.mDatabase;
        try {
            this.mIsInitializing = true;
            if (sQLiteDatabase2 != null) {
                if (sQLiteDatabase2.isOpen()) {
                    sQLiteDatabase2.close();
                }
                sQLiteDatabase2 = null;
            }
            String str = this.mName;
            if (str == null) {
                sQLiteDatabase2 = SQLiteDatabase.createSecureDatabase(null, bArr);
            } else {
                File databasePath = this.mContext.getDatabasePath(str);
                try {
                    sQLiteDatabase2 = SQLiteDatabase.openSecureDatabase(databasePath.getPath(), this.mOpenParamsBuilder.build(), bArr, this.mContext);
                    setFilePermissionsForDb(databasePath.getPath());
                } catch (SQLException e) {
                    throw e;
                }
            }
            onConfigure(sQLiteDatabase2);
            int version = sQLiteDatabase2.getVersion();
            if (version != this.mNewVersion) {
                if (sQLiteDatabase2.isReadOnly()) {
                    throw new SQLiteException("Can't upgrade read-only database from version " + sQLiteDatabase2.getVersion() + " to " + this.mNewVersion + ": " + this.mName);
                }
                if (version > 0 && version < this.mMinimumSupportedVersion) {
                    File file = new File(sQLiteDatabase2.getPath());
                    onBeforeDelete(sQLiteDatabase2);
                    sQLiteDatabase2.close();
                    if (SQLiteDatabase.deleteDatabase(file)) {
                        this.mIsInitializing = false;
                        return getDatabaseLocked(z, bArr);
                    }
                    throw new IllegalStateException("Unable to delete obsolete database " + this.mName + " with version " + version);
                }
                sQLiteDatabase2.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(sQLiteDatabase2);
                    } else if (version > this.mNewVersion) {
                        Log.i(TAG, "DB version downgrading from " + version + " to " + this.mNewVersion);
                        onDowngrade(sQLiteDatabase2, version, this.mNewVersion);
                    } else {
                        Log.i(TAG, "DB version upgrading from " + version + " to " + this.mNewVersion);
                        onUpgrade(sQLiteDatabase2, version, this.mNewVersion);
                    }
                    sQLiteDatabase2.setVersion(this.mNewVersion);
                    sQLiteDatabase2.setTransactionSuccessful();
                    sQLiteDatabase2.endTransaction();
                } catch (Throwable th) {
                    sQLiteDatabase2.endTransaction();
                    throw th;
                }
            }
            onOpen(sQLiteDatabase2);
            if (sQLiteDatabase2.isReadOnly()) {
                Log.w(TAG, "Opened " + this.mName + " in read-only mode");
            }
            this.mDatabase = sQLiteDatabase2;
            this.mIsInitializing = false;
            return sQLiteDatabase2;
        } finally {
            this.mIsInitializing = false;
            if (sQLiteDatabase2 != null && sQLiteDatabase2 != this.mDatabase) {
                sQLiteDatabase2.close();
            }
        }
    }

    private static void setFilePermissionsForDb(String str) {
        FileUtils.setPermissions(str, 432, -1, -1);
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        }
        SQLiteDatabase sQLiteDatabase = this.mDatabase;
        if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        throw new SQLiteException("Can't downgrade database from version " + i + " to " + i2);
    }

    public static final void convertToPlainDatabase(File file, File file2, byte[] bArr) throws Exception {
        SQLiteDatabase.convertToPlainDatabase(file, file2, bArr);
    }

    public static final void convertToSecureDatabase(File file, File file2, byte[] bArr) throws Exception {
        SQLiteDatabase.convertToSecureDatabase(file, file2, bArr);
    }

    public static final int changeDatabasePassword(SQLiteDatabase sQLiteDatabase, byte[] bArr) {
        return sQLiteDatabase.changeDBPassword(bArr);
    }
}
