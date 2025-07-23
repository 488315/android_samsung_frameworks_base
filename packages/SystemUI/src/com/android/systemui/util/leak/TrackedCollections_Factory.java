package com.android.systemui.util.leak;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TrackedCollections_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
