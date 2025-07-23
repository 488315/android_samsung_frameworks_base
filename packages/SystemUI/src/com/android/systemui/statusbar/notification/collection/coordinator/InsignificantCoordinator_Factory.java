package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationManager;
import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import dagger.internal.Providers;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class InsignificantCoordinator_Factory implements Provider {
    private final Provider bubbleCoordinatorProvider;
    private final Provider commonNotifCollectionLazyProvider;
    private final Provider contextProvider;
    private final Provider debugModeFilterProvider;
    private final Provider errorManagerProvider;
    private final Provider headsUpManagerProvider;
    private final Provider keyguardNotificationVisibilityProvider;
    private final Provider keyguardUpdateMonitorProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider notifLiveDataStoreProvider;
    private final Provider notifPipelineProvider;
    private final Provider notificationManagerProvider;
    private final Provider silentHeaderControllerProvider;
    private final Provider silentNodeControllerProvider;
    private final Provider statusBarStateControllerProvider;
    private final Provider subscreenControllerProvider;
    private final Provider timeSortCoordinatorProvider;

    public InsignificantCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
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
        this.keyguardUpdateMonitorProvider = provider12;
        this.keyguardNotificationVisibilityProvider = provider13;
        this.debugModeFilterProvider = provider14;
        this.subscreenControllerProvider = provider15;
        this.bubbleCoordinatorProvider = provider16;
        this.contextProvider = provider17;
    }

    public static InsignificantCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16, javax.inject.Provider provider17) {
        return new InsignificantCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11), Providers.asDaggerProvider(provider12), Providers.asDaggerProvider(provider13), Providers.asDaggerProvider(provider14), Providers.asDaggerProvider(provider15), Providers.asDaggerProvider(provider16), Providers.asDaggerProvider(provider17));
    }

    public static InsignificantCoordinator newInstance(SectionHeaderController sectionHeaderController, NodeController nodeController, NotificationManager notificationManager, NotifTimeSortCoordnator notifTimeSortCoordnator, NotifInflationErrorManager notifInflationErrorManager, Lazy lazy, StatusBarStateController statusBarStateController, NotifLiveDataStoreImpl notifLiveDataStoreImpl, HeadsUpManager headsUpManager, NotifPipeline notifPipeline, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, DebugModeFilterProvider debugModeFilterProvider, SubscreenNotificationController subscreenNotificationController, BubbleCoordinator bubbleCoordinator, Context context) {
        return new InsignificantCoordinator(sectionHeaderController, nodeController, notificationManager, notifTimeSortCoordnator, notifInflationErrorManager, lazy, statusBarStateController, notifLiveDataStoreImpl, headsUpManager, notifPipeline, notificationLockscreenUserManager, keyguardUpdateMonitor, keyguardNotificationVisibilityProvider, debugModeFilterProvider, subscreenNotificationController, bubbleCoordinator, context);
    }

    public static InsignificantCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        return new InsignificantCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    @Override // javax.inject.Provider
    public InsignificantCoordinator get() {
        return newInstance((SectionHeaderController) this.silentHeaderControllerProvider.get(), (NodeController) this.silentNodeControllerProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (NotifTimeSortCoordnator) this.timeSortCoordinatorProvider.get(), (NotifInflationErrorManager) this.errorManagerProvider.get(), DoubleCheck.lazy(this.commonNotifCollectionLazyProvider), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (NotifLiveDataStoreImpl) this.notifLiveDataStoreProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardNotificationVisibilityProvider) this.keyguardNotificationVisibilityProvider.get(), (DebugModeFilterProvider) this.debugModeFilterProvider.get(), (SubscreenNotificationController) this.subscreenControllerProvider.get(), (BubbleCoordinator) this.bubbleCoordinatorProvider.get(), (Context) this.contextProvider.get());
    }
}
