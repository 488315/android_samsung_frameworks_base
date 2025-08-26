package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* loaded from: classes3.dex */
public final class NotifCounterCoordinator_Factory implements Provider {
    private final Provider contextProvider;

    public NotifCounterCoordinator_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static NotifCounterCoordinator_Factory create(javax.inject.Provider provider) {
        return new NotifCounterCoordinator_Factory(Providers.asDaggerProvider(provider));
    }

    public static NotifCounterCoordinator newInstance(Context context) {
        return new NotifCounterCoordinator(context);
    }

    public static NotifCounterCoordinator_Factory create(Provider provider) {
        return new NotifCounterCoordinator_Factory(provider);
    }

    @Override // javax.inject.Provider
    public NotifCounterCoordinator get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
