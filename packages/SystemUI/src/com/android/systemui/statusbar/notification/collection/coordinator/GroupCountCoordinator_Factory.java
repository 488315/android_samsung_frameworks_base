package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GroupCountCoordinator_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
