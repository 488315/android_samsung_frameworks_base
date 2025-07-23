package com.android.settingslib.datastore;

import android.app.Application;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackupRestoreStorageManager {
    public final Executor executor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class StorageWrapper {
        public StorageWrapper(BackupRestoreStorageManager backupRestoreStorageManager, BackupRestoreStorage backupRestoreStorage) {
            throw new IllegalArgumentException(this + " does not implement either KeyedObservable or Observable");
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ BackupRestoreStorageManager(Application application, DefaultConstructorMarker defaultConstructorMarker) {
        this(application);
    }

    private BackupRestoreStorageManager(Application application) {
        new ConcurrentHashMap();
        this.executor = MoreExecutors.directExecutor();
    }

    public static /* synthetic */ void getStorageWrappers$frameworks__base__packages__SettingsLib__DataStore__android_common__SeslSettingsLibDataStore$annotations() {
    }
}
