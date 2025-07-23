package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SemPriorityCoordinator_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final SemPriorityCoordinator_Factory INSTANCE = new SemPriorityCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static SemPriorityCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SemPriorityCoordinator newInstance() {
        return new SemPriorityCoordinator();
    }

    @Override // javax.inject.Provider
    public SemPriorityCoordinator get() {
        return newInstance();
    }
}
