package com.android.systemui.util;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class ActivityTaskManagerProxy_Factory implements Provider {

    final class InstanceHolder {
        static final ActivityTaskManagerProxy_Factory INSTANCE = new ActivityTaskManagerProxy_Factory();

        private InstanceHolder() {
        }
    }

    public static ActivityTaskManagerProxy_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActivityTaskManagerProxy newInstance() {
        return new ActivityTaskManagerProxy();
    }

    @Override // javax.inject.Provider
    public ActivityTaskManagerProxy get() {
        return newInstance();
    }
}
