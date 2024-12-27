package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
