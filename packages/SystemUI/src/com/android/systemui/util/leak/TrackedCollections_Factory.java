package com.android.systemui.util.leak;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class TrackedCollections_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final TrackedCollections_Factory INSTANCE = new TrackedCollections_Factory();

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
