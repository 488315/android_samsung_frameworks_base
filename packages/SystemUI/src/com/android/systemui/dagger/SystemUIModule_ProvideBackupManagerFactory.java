package com.android.systemui.dagger;

import android.app.backup.BackupManager;
import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
