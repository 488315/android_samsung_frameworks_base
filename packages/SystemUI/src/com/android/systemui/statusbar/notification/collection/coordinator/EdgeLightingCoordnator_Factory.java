package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class EdgeLightingCoordnator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    final class InstanceHolder {
        private static final EdgeLightingCoordnator_Factory INSTANCE = new EdgeLightingCoordnator_Factory();

        private InstanceHolder() {
        }
    }

    public static EdgeLightingCoordnator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static EdgeLightingCoordnator newInstance() {
        return new EdgeLightingCoordnator();
    }

    @Override // javax.inject.Provider
    public EdgeLightingCoordnator get() {
        return newInstance();
    }
}
