package androidx.room.support;

import android.content.Context;
import android.util.Log;
import androidx.room.DatabaseConfiguration;
import androidx.room.DelegatingOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.MigrationUtil;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.sqlite.util.ProcessLock;
import com.samsung.android.knox.lockscreen.LSOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Callable;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PrePackagedCopyOpenHelper implements SupportSQLiteOpenHelper, DelegatingOpenHelper {
    public final Context context;
    public final String copyFromAssetPath;
    public final File copyFromFile;
    public final Callable copyFromInputStream;
    public DatabaseConfiguration databaseConfiguration;
    public final int databaseVersion;
    public final SupportSQLiteOpenHelper delegate;
    public boolean verified;

    public PrePackagedCopyOpenHelper(Context context, String str, File file, Callable<InputStream> callable, int i, SupportSQLiteOpenHelper supportSQLiteOpenHelper) {
        this.context = context;
        this.copyFromAssetPath = str;
        this.copyFromFile = file;
        this.copyFromInputStream = callable;
        this.databaseVersion = i;
        this.delegate = supportSQLiteOpenHelper;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final synchronized void close() {
        this.delegate.close();
        this.verified = false;
    }

    public final void copyDatabaseFile(File file, boolean z) {
        ReadableByteChannel newChannel;
        if (this.copyFromAssetPath != null) {
            newChannel = Channels.newChannel(this.context.getAssets().open(this.copyFromAssetPath));
        } else if (this.copyFromFile != null) {
            newChannel = new FileInputStream(this.copyFromFile).getChannel();
        } else {
            Callable callable = this.copyFromInputStream;
            if (callable == null) {
                throw new IllegalStateException("copyFromAssetPath, copyFromFile and copyFromInputStream are all null!");
            }
            try {
                newChannel = Channels.newChannel((InputStream) callable.call());
            } catch (Exception e) {
                throw new IOException("inputStreamCallable exception on call", e);
            }
        }
        ReadableByteChannel readableByteChannel = newChannel;
        File createTempFile = File.createTempFile("room-copy-helper", LSOUtils.TEMP_DIR, this.context.getCacheDir());
        createTempFile.deleteOnExit();
        FileChannel channel = new FileOutputStream(createTempFile).getChannel();
        channel.getClass();
        try {
            channel.transferFrom(readableByteChannel, 0L, Long.MAX_VALUE);
            channel.force(false);
            readableByteChannel.close();
            channel.close();
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !parentFile.mkdirs()) {
                throw new IOException("Failed to create directories for " + file.getAbsolutePath());
            }
            DatabaseConfiguration databaseConfiguration = this.databaseConfiguration;
            DatabaseConfiguration databaseConfiguration2 = null;
            if (databaseConfiguration == null) {
                databaseConfiguration = null;
            }
            if (databaseConfiguration.prepackagedDatabaseCallback != null) {
                try {
                    final int readVersion = DBUtil.readVersion(createTempFile);
                    FrameworkSQLiteOpenHelperFactory frameworkSQLiteOpenHelperFactory = new FrameworkSQLiteOpenHelperFactory();
                    SupportSQLiteOpenHelper.Configuration.Companion companion = SupportSQLiteOpenHelper.Configuration.Companion;
                    Context context = this.context;
                    companion.getClass();
                    SupportSQLiteOpenHelper.Configuration.Builder builder = new SupportSQLiteOpenHelper.Configuration.Builder(context);
                    builder.name = createTempFile.getAbsolutePath();
                    final int i = readVersion >= 1 ? readVersion : 1;
                    SupportSQLiteOpenHelper.Callback callback = new SupportSQLiteOpenHelper.Callback(i) { // from class: androidx.room.support.PrePackagedCopyOpenHelper$createFrameworkOpenHelper$configuration$1
                        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                        public final void onOpen(FrameworkSQLiteDatabase frameworkSQLiteDatabase) {
                            int i2 = readVersion;
                            if (i2 < 1) {
                                frameworkSQLiteDatabase.delegate.setVersion(i2);
                            }
                        }

                        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                        public final void onCreate(FrameworkSQLiteDatabase frameworkSQLiteDatabase) {
                        }

                        @Override // androidx.sqlite.db.SupportSQLiteOpenHelper.Callback
                        public final void onUpgrade(FrameworkSQLiteDatabase frameworkSQLiteDatabase, int i2, int i3) {
                        }
                    };
                    builder.callback = callback;
                    SupportSQLiteOpenHelper create = frameworkSQLiteOpenHelperFactory.create(new SupportSQLiteOpenHelper.Configuration(builder.context, builder.name, callback, false, false));
                    try {
                        if (z) {
                            ((FrameworkSQLiteOpenHelper) create).getWritableDatabase();
                        } else {
                            ((FrameworkSQLiteOpenHelper.OpenHelper) ((FrameworkSQLiteOpenHelper) create).lazyDelegate.getValue()).getSupportDatabase(false);
                        }
                        DatabaseConfiguration databaseConfiguration3 = this.databaseConfiguration;
                        if (databaseConfiguration3 != null) {
                            databaseConfiguration2 = databaseConfiguration3;
                        }
                        databaseConfiguration2.prepackagedDatabaseCallback.getClass();
                        Unit unit = Unit.INSTANCE;
                        ((FrameworkSQLiteOpenHelper) create).close();
                    } finally {
                    }
                } catch (IOException e2) {
                    throw new RuntimeException("Malformed database file, unable to read version.", e2);
                }
            }
            if (createTempFile.renameTo(file)) {
                return;
            }
            throw new IOException("Failed to move intermediate file (" + createTempFile.getAbsolutePath() + ") to destination (" + file.getAbsolutePath() + ").");
        } catch (Throwable th) {
            readableByteChannel.close();
            channel.close();
            throw th;
        }
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final String getDatabaseName() {
        return this.delegate.getDatabaseName();
    }

    @Override // androidx.room.DelegatingOpenHelper
    public final SupportSQLiteOpenHelper getDelegate() {
        return this.delegate;
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final SupportSQLiteDatabase getWritableDatabase() {
        if (!this.verified) {
            String databaseName = this.delegate.getDatabaseName();
            if (databaseName == null) {
                throw new IllegalStateException("Required value was null.");
            }
            File databasePath = this.context.getDatabasePath(databaseName);
            DatabaseConfiguration databaseConfiguration = this.databaseConfiguration;
            DatabaseConfiguration databaseConfiguration2 = null;
            if (databaseConfiguration == null) {
                databaseConfiguration = null;
            }
            ProcessLock processLock = new ProcessLock(databaseName, this.context.getFilesDir(), databaseConfiguration.multiInstanceInvalidation);
            try {
                processLock.lock(processLock.processLock);
                if (databasePath.exists()) {
                    try {
                        int readVersion = DBUtil.readVersion(databasePath);
                        int i = this.databaseVersion;
                        if (readVersion != i) {
                            DatabaseConfiguration databaseConfiguration3 = this.databaseConfiguration;
                            if (databaseConfiguration3 != null) {
                                databaseConfiguration2 = databaseConfiguration3;
                            }
                            databaseConfiguration2.getClass();
                            if (!MigrationUtil.isMigrationRequired(databaseConfiguration2, readVersion, i)) {
                                if (this.context.deleteDatabase(databaseName)) {
                                    try {
                                        copyDatabaseFile(databasePath, true);
                                        Unit unit = Unit.INSTANCE;
                                    } catch (IOException e) {
                                        Log.w("ROOM", "Unable to copy database file.", e);
                                    }
                                } else {
                                    Log.w("ROOM", "Failed to delete database file (" + databaseName + ") for a copy destructive migration.");
                                }
                            }
                        }
                    } catch (IOException e2) {
                        Log.w("ROOM", "Unable to read database version.", e2);
                    }
                    this.verified = true;
                } else {
                    try {
                        copyDatabaseFile(databasePath, true);
                        this.verified = true;
                    } catch (IOException e3) {
                        throw new RuntimeException("Unable to copy database file.", e3);
                    }
                }
            } finally {
            }
            processLock.unlock();
        }
        return this.delegate.getWritableDatabase();
    }

    @Override // androidx.sqlite.db.SupportSQLiteOpenHelper
    public final void setWriteAheadLoggingEnabled(boolean z) {
        this.delegate.setWriteAheadLoggingEnabled(z);
    }
}
