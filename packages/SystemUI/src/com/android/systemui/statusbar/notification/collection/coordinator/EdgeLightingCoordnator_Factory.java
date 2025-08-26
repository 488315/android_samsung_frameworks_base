package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class EdgeLightingCoordnator_Factory implements Provider {

    final class InstanceHolder {
        static final EdgeLightingCoordnator_Factory INSTANCE = new EdgeLightingCoordnator_Factory();

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
