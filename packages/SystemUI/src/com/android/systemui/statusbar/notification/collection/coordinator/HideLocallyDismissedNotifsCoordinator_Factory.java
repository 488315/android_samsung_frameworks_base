package com.android.systemui.statusbar.notification.collection.coordinator;

import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HideLocallyDismissedNotifsCoordinator_Factory implements Provider {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class InstanceHolder {
        static final HideLocallyDismissedNotifsCoordinator_Factory INSTANCE = new HideLocallyDismissedNotifsCoordinator_Factory();

        private InstanceHolder() {
        }
    }

    public static HideLocallyDismissedNotifsCoordinator_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static HideLocallyDismissedNotifsCoordinator newInstance() {
        return new HideLocallyDismissedNotifsCoordinator();
    }

    @Override // javax.inject.Provider
    public HideLocallyDismissedNotifsCoordinator get() {
        return newInstance();
    }
}
