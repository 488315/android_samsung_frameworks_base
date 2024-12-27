package com.android.systemui.util.wrapper;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BuildInfo_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final BuildInfo_Factory INSTANCE = new BuildInfo_Factory();

        private InstanceHolder() {
        }
    }

    public static BuildInfo_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BuildInfo newInstance() {
        return new BuildInfo();
    }

    @Override // javax.inject.Provider
    public BuildInfo get() {
        return newInstance();
    }
}
