package android.database.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* loaded from: classes.dex */
public abstract class SQLiteOpenHelper implements AutoCloseable {
    private static final String TAG = "SQLiteOpenHelper";
    private static final ConcurrentHashMap<String, Object> sDbLock = new ConcurrentHashMap<>();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mIsInitializing;
    private final Object mLock;
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

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        this(context, str, cursorFactory, i, (DatabaseErrorHandler) null);
    }

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, cursorFactory, i, 0, databaseErrorHandler);
    }

    public SQLiteOpenHelper(Context context, String str, int i, SQLiteDatabase.OpenParams openParams) {
        this(context, str, i, 0, openParams.toBuilder());
    }

    public SQLiteOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i, int i2, DatabaseErrorHandler databaseErrorHandler) {
        this(context, str, i, i2, new SQLiteDatabase.OpenParams.Builder());
        this.mOpenParamsBuilder.setCursorFactory(cursorFactory);
        this.mOpenParamsBuilder.setErrorHandler(databaseErrorHandler);
    }

    private SQLiteOpenHelper(Context context, String str, int i, int i2, SQLiteDatabase.OpenParams.Builder builder) {
        Object obj;
        Objects.requireNonNull(builder);
        if (i < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + i);
        }
        this.mContext = context;
        this.mName = str;
        this.mNewVersion = i;
        this.mMinimumSupportedVersion = Math.max(0, i2);
        setOpenParamsBuilder(builder);
        if (!Flags.concurrentOpenHelper() || str == null) {
            obj = new Object();
        } else {
            obj = sDbLock.computeIfAbsent(str, new Function() { // from class: android.database.sqlite.SQLiteOpenHelper$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return SQLiteOpenHelper.lambda$new$0((String) obj2);
                }
            });
        }
        this.mLock = obj;
    }

    static /* synthetic */ Object lambda$new$0(String str) {
        return new Object();
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

    public void setLookasideConfig(int i, int i2) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Lookaside memory config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setLookasideConfig(i, i2);
        }
    }

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

    @Deprecated
    public void setIdleConnectionTimeout(long j) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Connection timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setIdleConnectionTimeout(j);
        }
    }

    public void semSetIdleConnectionShrinkTimeout(long j) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Shrink timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetIdleConnectionShrinkTimeout(j);
        }
    }

    public void semSetSeparateCacheModeEnabled(boolean z) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Separate cache config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetSeparateCacheModeEnabled(z);
        }
    }

    @Deprecated
    public void semSetCacheSize(int i) {
        if (i < 0 || i > 8388608) {
            throw new IllegalArgumentException("The cache size should not be negative value. Also, it should be less than soft heap size (8M). Now: " + i);
        }
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.mDatabase.setCacheSize(i / SQLiteGlobal.getDefaultPageSize());
            }
            this.mOpenParamsBuilder.semSetCacheSize(i);
        }
    }

    public void semSetUserDataRecoveryEnabled(boolean z) {
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                throw new IllegalStateException("Database Recovery config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setUserDataRecoveryEnabled(z);
        }
    }

    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(true);
        }
        return databaseLocked;
    }

    public SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase databaseLocked;
        synchronized (this) {
            databaseLocked = getDatabaseLocked(false);
        }
        return databaseLocked;
    }

    private SQLiteDatabase getDatabaseLocked(boolean z) {
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
            synchronized (this.mLock) {
                this.mIsInitializing = true;
                if (sQLiteDatabase2 != null) {
                    if (z && sQLiteDatabase2.isReadOnly()) {
                        sQLiteDatabase2.reopenReadWrite();
                    }
                } else {
                    String str = this.mName;
                    if (str == null) {
                        sQLiteDatabase2 = SQLiteDatabase.createInMemory(this.mOpenParamsBuilder.build());
                    } else {
                        File databasePath = this.mContext.getDatabasePath(str);
                        SQLiteDatabase.OpenParams build = this.mOpenParamsBuilder.build();
                        try {
                            sQLiteDatabase2 = SQLiteDatabase.openDatabase(databasePath.getPath(), build, this.mContext);
                            setFilePermissionsForDb(databasePath.getPath());
                        } catch (SQLException e) {
                            if (z) {
                                throw e;
                            }
                            Log.e(TAG, "Couldn't open database for writing (will try read-only):", e);
                            sQLiteDatabase2 = SQLiteDatabase.openDatabase(databasePath.getPath(), build.toBuilder().addOpenFlags(1).build(), this.mContext);
                        }
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
                            return getDatabaseLocked(z);
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
                sQLiteDatabase2.setReserveSpace();
                onOpen(sQLiteDatabase2);
                this.mDatabase = sQLiteDatabase2;
                this.mIsInitializing = false;
                return sQLiteDatabase2;
            }
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
}
