package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import dagger.internal.Provider;

public final class NotifCounterCoordinator_Factory implements Provider {
    private final javax.inject.Provider contextProvider;

    public NotifCounterCoordinator_Factory(javax.inject.Provider provider) {
        this.contextProvider = provider;
    }

    public static NotifCounterCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotifCounterCoordinator_Factory(provider);
    }

    public static NotifCounterCoordinator newInstance(Context context) {
        return new NotifCounterCoordinator(context);
    }

    @Override // javax.inject.Provider
    public NotifCounterCoordinator get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
