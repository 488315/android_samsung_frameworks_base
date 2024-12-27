package com.android.systemui.util;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class CoverUtil_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final CoverUtil_Factory INSTANCE = new CoverUtil_Factory();

        private InstanceHolder() {
        }
    }

    public static CoverUtil_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CoverUtil newInstance() {
        return new CoverUtil();
    }

    @Override // javax.inject.Provider
    public CoverUtil get() {
        return newInstance();
    }
}
