package com.android.systemui.dagger;

import android.app.backup.BackupManager;
import android.content.Context;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class SystemUIModule_ProvideBackupManagerFactory implements Provider {
    public final Provider contextProvider;

    public SystemUIModule_ProvideBackupManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static BackupManager provideBackupManager(Context context) {
        return new BackupManager(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new BackupManager((Context) this.contextProvider.get());
    }
}
