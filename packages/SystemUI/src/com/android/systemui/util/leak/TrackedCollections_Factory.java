package com.android.systemui.util.leak;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class TrackedCollections_Factory implements Provider {

    final class InstanceHolder {
        static final TrackedCollections_Factory INSTANCE = new TrackedCollections_Factory();

        private InstanceHolder() {
        }
    }

    public static TrackedCollections_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static TrackedCollections newInstance() {
        return new TrackedCollections();
    }

    @Override // javax.inject.Provider
    public TrackedCollections get() {
        return newInstance();
    }
}
