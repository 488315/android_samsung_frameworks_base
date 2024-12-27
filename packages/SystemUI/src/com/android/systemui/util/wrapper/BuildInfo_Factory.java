package com.android.systemui.util.wrapper;

import dagger.internal.Provider;

public final class BuildInfo_Factory implements Provider {

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
