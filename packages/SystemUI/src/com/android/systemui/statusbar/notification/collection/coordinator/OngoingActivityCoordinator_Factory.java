package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OngoingActivityCoordinator_Factory implements Provider {
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider ongoingActivityHeaderControllerProvider;
    private final javax.inject.Provider timeSortCoordnatorProvider;

    public OngoingActivityCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        this.contextProvider = provider;
        this.timeSortCoordnatorProvider = provider2;
        this.ongoingActivityHeaderControllerProvider = provider3;
    }

    public static OngoingActivityCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3) {
        return new OngoingActivityCoordinator_Factory(provider, provider2, provider3);
    }

    public static OngoingActivityCoordinator newInstance(Context context, NotifTimeSortCoordnator notifTimeSortCoordnator, NodeController nodeController) {
        return new OngoingActivityCoordinator(context, notifTimeSortCoordnator, nodeController);
    }

    @Override // javax.inject.Provider
    public OngoingActivityCoordinator get() {
        return newInstance((Context) this.contextProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordnatorProvider.get(), (NodeController) this.ongoingActivityHeaderControllerProvider.get());
    }
}
