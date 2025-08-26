package com.android.settingslib.datastore;

import android.app.Application;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class BackupRestoreStorageManager {
    public final Executor executor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
