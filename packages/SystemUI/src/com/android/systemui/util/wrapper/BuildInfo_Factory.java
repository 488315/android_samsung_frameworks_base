package com.android.systemui.util.wrapper;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BuildInfo_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final BuildInfo_Factory INSTANCE = new BuildInfo_Factory();

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
