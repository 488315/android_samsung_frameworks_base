package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class BackupManagerProxy_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final BackupManagerProxy_Factory INSTANCE = new BackupManagerProxy_Factory();

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
