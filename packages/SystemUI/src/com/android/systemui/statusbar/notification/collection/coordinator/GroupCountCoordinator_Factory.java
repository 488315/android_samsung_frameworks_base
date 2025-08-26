package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class GroupCountCoordinator_Factory implements Provider {

    final class InstanceHolder {
        static final GroupCountCoordinator_Factory INSTANCE = new GroupCountCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static GroupCountCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static GroupCountCoordinator newInstance() {
        return new GroupCountCoordinator();
    }

    @Override // javax.inject.Provider
    public GroupCountCoordinator get() {
        return newInstance();
    }
}
