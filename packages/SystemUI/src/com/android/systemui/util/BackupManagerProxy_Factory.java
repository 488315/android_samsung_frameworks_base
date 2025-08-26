package com.android.systemui.util;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class BackupManagerProxy_Factory implements Provider {

    final class InstanceHolder {
        static final BackupManagerProxy_Factory INSTANCE = new BackupManagerProxy_Factory();

        private InstanceHolder() {
        }
    }

    public static BackupManagerProxy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BackupManagerProxy newInstance() {
        return new BackupManagerProxy();
    }

    @Override // javax.inject.Provider
    public BackupManagerProxy get() {
        return newInstance();
    }
}
