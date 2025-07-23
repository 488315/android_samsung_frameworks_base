package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RowAlertTimeCoordinator_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final RowAlertTimeCoordinator_Factory INSTANCE = new RowAlertTimeCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static RowAlertTimeCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static RowAlertTimeCoordinator newInstance() {
        return new RowAlertTimeCoordinator();
    }

    @Override // javax.inject.Provider
    public RowAlertTimeCoordinator get() {
        return newInstance();
    }
}
