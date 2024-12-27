package android.database.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.util.Objects;

/* loaded from: classes.dex */
public abstract class SQLiteOpenHelper implements AutoCloseable {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mIsInitializing;
    private final int mMinimumSupportedVersion;
    private final String mName;
    private final int mNewVersion;
    private SQLiteDatabase.OpenParams.Builder mOpenParamsBuilder;

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase);

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2);

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, (DatabaseErrorHandler) null);
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        this(context, name, factory, version, 0, errorHandler);
    }

    public SQLiteOpenHelper(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
        this(context, name, version, 0, openParams.toBuilder());
    }

    public SQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, int minimumSupportedVersion, DatabaseErrorHandler errorHandler) {
        this(context, name, version, minimumSupportedVersion, new SQLiteDatabase.OpenParams.Builder());
        this.mOpenParamsBuilder.setCursorFactory(factory);
        this.mOpenParamsBuilder.setErrorHandler(errorHandler);
    }

    private SQLiteOpenHelper(Context context, String name, int version, int minimumSupportedVersion, SQLiteDatabase.OpenParams.Builder openParamsBuilder) {
        Objects.requireNonNull(openParamsBuilder);
        if (version < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + version);
        }
        this.mContext = context;
        this.mName = name;
        this.mNewVersion = version;
        this.mMinimumSupportedVersion = Math.max(0, minimumSupportedVersion);
        setOpenParamsBuilder(openParamsBuilder);
    }

    public String getDatabaseName() {
        return this.mName;
    }

    public void setWriteAheadLoggingEnabled(boolean enabled) {
        synchronized (this) {
            if (this.mOpenParamsBuilder.isWriteAheadLoggingEnabled() != enabled) {
                if (this.mDatabase != null && this.mDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
                    if (enabled) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                }
                this.mOpenParamsBuilder.setWriteAheadLoggingEnabled(enabled);
            }
            this.mOpenParamsBuilder.removeOpenFlags(Integer.MIN_VALUE);
            if (!enabled) {
                this.mOpenParamsBuilder.addOpenFlags(1024);
            } else {
                this.mOpenParamsBuilder.removeOpenFlags(1024);
            }
        }
    }

    public void setLookasideConfig(int slotSize, int slotCount) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("Lookaside memory config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setLookasideConfig(slotSize, slotCount);
        }
    }

    public void setOpenParams(SQLiteDatabase.OpenParams openParams) {
        Objects.requireNonNull(openParams);
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("OpenParams cannot be set after opening the database");
            }
            setOpenParamsBuilder(new SQLiteDatabase.OpenParams.Builder(openParams));
        }
    }

    private void setOpenParamsBuilder(SQLiteDatabase.OpenParams.Builder openParamsBuilder) {
        this.mOpenParamsBuilder = openParamsBuilder;
        this.mOpenParamsBuilder.addOpenFlags(268435456);
    }

    @Deprecated
    public void setIdleConnectionTimeout(long idleConnectionTimeoutMs) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("Connection timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setIdleConnectionTimeout(idleConnectionTimeoutMs);
        }
    }

    public void semSetIdleConnectionShrinkTimeout(long idleConnectionShrinkTimeoutMs) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("Shrink timeout setting cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetIdleConnectionShrinkTimeout(idleConnectionShrinkTimeoutMs);
        }
    }

    public void semSetSeparateCacheModeEnabled(boolean enabled) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("Separate cache config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.semSetSeparateCacheModeEnabled(enabled);
        }
    }

    @Deprecated
    public void semSetCacheSize(int cacheSizeByte) {
        if (cacheSizeByte < 0 || cacheSizeByte > 8388608) {
            throw new IllegalArgumentException("The cache size should not be negative value. Also, it should be less than soft heap size (8M). Now: " + cacheSizeByte);
        }
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                this.mDatabase.setCacheSize(cacheSizeByte / SQLiteGlobal.getDefaultPageSize());
            }
            this.mOpenParamsBuilder.semSetCacheSize(cacheSizeByte);
        }
    }

    public void semSetUserDataRecoveryEnabled(boolean enabled) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                throw new IllegalStateException("Database Recovery config cannot be changed after opening the database");
            }
            this.mOpenParamsBuilder.setUserDataRecoveryEnabled(enabled);
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

    private SQLiteDatabase getDatabaseLocked(boolean writable) {
        if (this.mDatabase != null) {
            if (!this.mDatabase.isOpen()) {
                this.mDatabase = null;
            } else if (!writable || !this.mDatabase.isReadOnly()) {
                return this.mDatabase;
            }
        }
        if (this.mIsInitializing) {
            throw new IllegalStateException("getDatabase called recursively");
        }
        SQLiteDatabase db = this.mDatabase;
        try {
            this.mIsInitializing = true;
            if (db != null) {
                if (writable && db.isReadOnly()) {
                    db.reopenReadWrite();
                }
            } else if (this.mName == null) {
                db = SQLiteDatabase.createInMemory(this.mOpenParamsBuilder.build());
            } else {
                File filePath = this.mContext.getDatabasePath(this.mName);
                SQLiteDatabase.OpenParams params = this.mOpenParamsBuilder.build();
                try {
                    db = SQLiteDatabase.openDatabase(filePath.getPath(), params, this.mContext);
                    setFilePermissionsForDb(filePath.getPath());
                } catch (SQLException ex) {
                    if (writable) {
                        throw ex;
                    }
                    Log.e(TAG, "Couldn't open database for writing (will try read-only):", ex);
                    db = SQLiteDatabase.openDatabase(filePath.getPath(), params.toBuilder().addOpenFlags(1).build(), this.mContext);
                }
            }
            onConfigure(db);
            int version = db.getVersion();
            if (version != this.mNewVersion) {
                if (db.isReadOnly()) {
                    throw new SQLiteException("Can't upgrade read-only database from version " + db.getVersion() + " to " + this.mNewVersion + ": " + this.mName);
                }
                if (version > 0 && version < this.mMinimumSupportedVersion) {
                    File databaseFile = new File(db.getPath());
                    onBeforeDelete(db);
                    db.close();
                    if (SQLiteDatabase.deleteDatabase(databaseFile)) {
                        this.mIsInitializing = false;
                        return getDatabaseLocked(writable);
                    }
                    throw new IllegalStateException("Unable to delete obsolete database " + this.mName + " with version " + version);
                }
                db.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(db);
                    } else if (version > this.mNewVersion) {
                        Log.i(TAG, "DB version downgrading from " + version + " to " + this.mNewVersion);
                        onDowngrade(db, version, this.mNewVersion);
                    } else {
                        Log.i(TAG, "DB version upgrading from " + version + " to " + this.mNewVersion);
                        onUpgrade(db, version, this.mNewVersion);
                    }
                    db.setVersion(this.mNewVersion);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                } catch (Throwable th) {
                    db.endTransaction();
                    throw th;
                }
            }
            db.setReserveSpace();
            onOpen(db);
            this.mDatabase = db;
            this.mIsInitializing = false;
            if (db != null && db != this.mDatabase) {
                db.close();
            }
            return db;
        } finally {
            this.mIsInitializing = false;
            if (db != null && db != this.mDatabase) {
                db.close();
            }
        }
    }

    private static void setFilePermissionsForDb(String dbPath) {
        FileUtils.setPermissions(dbPath, 432, -1, -1);
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        }
        if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public void onConfigure(SQLiteDatabase db) {
    }

    public void onBeforeDelete(SQLiteDatabase db) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new SQLiteException("Can't downgrade database from version " + oldVersion + " to " + newVersion);
    }

    public void onOpen(SQLiteDatabase db) {
    }
}
