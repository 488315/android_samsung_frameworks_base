package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class InsignificantCoordinator_Factory implements Provider {
    private final javax.inject.Provider commonNotifCollectionLazyProvider;
    private final javax.inject.Provider contextProvider;
    private final javax.inject.Provider errorManagerProvider;
    private final javax.inject.Provider headsUpManagerProvider;
    private final javax.inject.Provider lockscreenUserManagerProvider;
    private final javax.inject.Provider notifLiveDataStoreProvider;
    private final javax.inject.Provider notifPipelineProvider;
    private final javax.inject.Provider notificationManagerProvider;
    private final javax.inject.Provider silentHeaderControllerProvider;
    private final javax.inject.Provider silentNodeControllerProvider;
    private final javax.inject.Provider statusBarStateControllerProvider;
    private final javax.inject.Provider timeSortCoordinatorProvider;

    public InsignificantCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12) {
        this.silentHeaderControllerProvider = provider;
        this.silentNodeControllerProvider = provider2;
        this.notificationManagerProvider = provider3;
        this.timeSortCoordinatorProvider = provider4;
        this.errorManagerProvider = provider5;
        this.commonNotifCollectionLazyProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.notifLiveDataStoreProvider = provider8;
        this.headsUpManagerProvider = provider9;
        this.notifPipelineProvider = provider10;
        this.lockscreenUserManagerProvider = provider11;
        this.contextProvider = provider12;
    }

    public static InsignificantCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12) {
        return new InsignificantCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static InsignificantCoordinator newInstance(SectionHeaderController sectionHeaderController, NodeController nodeController, NotificationManager notificationManager, NotifTimeSortCoordnator notifTimeSortCoordnator, NotifInflationErrorManager notifInflationErrorManager, Lazy lazy, StatusBarStateController statusBarStateController, NotifLiveDataStoreImpl notifLiveDataStoreImpl, HeadsUpManager headsUpManager, NotifPipeline notifPipeline, NotificationLockscreenUserManager notificationLockscreenUserManager, Context context) {
        return new InsignificantCoordinator(sectionHeaderController, nodeController, notificationManager, notifTimeSortCoordnator, notifInflationErrorManager, lazy, statusBarStateController, notifLiveDataStoreImpl, headsUpManager, notifPipeline, notificationLockscreenUserManager, context);
    }

    @Override // javax.inject.Provider
    public InsignificantCoordinator get() {
        return newInstance((SectionHeaderController) this.silentHeaderControllerProvider.get(), (NodeController) this.silentNodeControllerProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordinatorProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (NotifLiveDataStoreImpl) this.notifLiveDataStoreProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (Context) this.contextProvider.get());
    }
}
