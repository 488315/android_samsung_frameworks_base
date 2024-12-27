package com.android.systemui.util.io;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class Files_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final Files_Factory INSTANCE = new Files_Factory();

        private InstanceHolder() {
        }
    }

    public static Files_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Files newInstance() {
        return new Files();
    }

    @Override // javax.inject.Provider
    public Files get() {
        return newInstance();
    }
}
