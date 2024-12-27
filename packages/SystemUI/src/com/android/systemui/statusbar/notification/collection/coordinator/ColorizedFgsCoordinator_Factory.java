package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ColorizedFgsCoordinator_Factory implements Provider {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
