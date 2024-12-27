package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

public final class ColorizedFgsCoordinator_Factory implements Provider {

    final class InstanceHolder {
        private static final ColorizedFgsCoordinator_Factory INSTANCE = new ColorizedFgsCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static ColorizedFgsCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ColorizedFgsCoordinator newInstance() {
        return new ColorizedFgsCoordinator();
    }

    @Override // javax.inject.Provider
    public ColorizedFgsCoordinator get() {
        return newInstance();
    }
}
