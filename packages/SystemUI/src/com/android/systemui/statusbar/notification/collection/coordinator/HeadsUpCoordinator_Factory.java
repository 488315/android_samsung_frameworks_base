package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor;
import com.android.systemui.statusbar.chips.uievents.StatusBarChipsUiEventLogger;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionLogger;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.NotificationActionClickManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;
import dagger.internal.Providers;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HeadsUpCoordinator_Factory implements Provider {
    private final Provider applicationScopeProvider;
    private final Provider mExecutorProvider;
    private final Provider mFlagsProvider;
    private final Provider mHeadsUpManagerProvider;
    private final Provider mHeadsUpViewBinderProvider;
    private final Provider mIncomingHeaderControllerProvider;
    private final Provider mInterruptLoggerProvider;
    private final Provider mLaunchFullScreenIntentProvider;
    private final Provider mLoggerProvider;
    private final Provider mRemoteInputManagerProvider;
    private final Provider mSystemClockProvider;
    private final Provider mVisualInterruptionDecisionProvider;
    private final Provider notifCollectionProvider;
    private final Provider notificationActionClickManagerProvider;
    private final Provider statusBarChipsUiEventLoggerProvider;
    private final Provider statusBarNotificationChipsInteractorProvider;

    public HeadsUpCoordinator_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        this.applicationScopeProvider = provider;
        this.mLoggerProvider = provider2;
        this.mInterruptLoggerProvider = provider3;
        this.mSystemClockProvider = provider4;
        this.notifCollectionProvider = provider5;
        this.mHeadsUpManagerProvider = provider6;
        this.mHeadsUpViewBinderProvider = provider7;
        this.mVisualInterruptionDecisionProvider = provider8;
        this.mRemoteInputManagerProvider = provider9;
        this.notificationActionClickManagerProvider = provider10;
        this.mLaunchFullScreenIntentProvider = provider11;
        this.mFlagsProvider = provider12;
        this.statusBarNotificationChipsInteractorProvider = provider13;
        this.statusBarChipsUiEventLoggerProvider = provider14;
        this.mIncomingHeaderControllerProvider = provider15;
        this.mExecutorProvider = provider16;
    }

    public static HeadsUpCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10, javax.inject.Provider provider11, javax.inject.Provider provider12, javax.inject.Provider provider13, javax.inject.Provider provider14, javax.inject.Provider provider15, javax.inject.Provider provider16) {
        return new HeadsUpCoordinator_Factory(Providers.asDaggerProvider(provider), Providers.asDaggerProvider(provider2), Providers.asDaggerProvider(provider3), Providers.asDaggerProvider(provider4), Providers.asDaggerProvider(provider5), Providers.asDaggerProvider(provider6), Providers.asDaggerProvider(provider7), Providers.asDaggerProvider(provider8), Providers.asDaggerProvider(provider9), Providers.asDaggerProvider(provider10), Providers.asDaggerProvider(provider11), Providers.asDaggerProvider(provider12), Providers.asDaggerProvider(provider13), Providers.asDaggerProvider(provider14), Providers.asDaggerProvider(provider15), Providers.asDaggerProvider(provider16));
    }

    public static HeadsUpCoordinator newInstance(CoroutineScope coroutineScope, HeadsUpCoordinatorLogger headsUpCoordinatorLogger, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, SystemClock systemClock, NotifCollection notifCollection, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, NotificationActionClickManager notificationActionClickManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor, StatusBarChipsUiEventLogger statusBarChipsUiEventLogger, NodeController nodeController, DelayableExecutor delayableExecutor) {
        return new HeadsUpCoordinator(coroutineScope, headsUpCoordinatorLogger, visualInterruptionDecisionLogger, systemClock, notifCollection, headsUpManager, headsUpViewBinder, visualInterruptionDecisionProvider, notificationRemoteInputManager, notificationActionClickManager, launchFullScreenIntentProvider, notifPipelineFlags, statusBarNotificationChipsInteractor, statusBarChipsUiEventLogger, nodeController, delayableExecutor);
    }

    public static HeadsUpCoordinator_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16) {
        return new HeadsUpCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16);
    }

    @Override // javax.inject.Provider
    public HeadsUpCoordinator get() {
        return newInstance((CoroutineScope) this.applicationScopeProvider.get(), (HeadsUpCoordinatorLogger) this.mLoggerProvider.get(), (VisualInterruptionDecisionLogger) this.mInterruptLoggerProvider.get(), (SystemClock) this.mSystemClockProvider.get(), (NotifCollection) this.notifCollectionProvider.get(), (HeadsUpManager) this.mHeadsUpManagerProvider.get(), (HeadsUpViewBinder) this.mHeadsUpViewBinderProvider.get(), (VisualInterruptionDecisionProvider) this.mVisualInterruptionDecisionProvider.get(), (NotificationRemoteInputManager) this.mRemoteInputManagerProvider.get(), (NotificationActionClickManager) this.notificationActionClickManagerProvider.get(), (LaunchFullScreenIntentProvider) this.mLaunchFullScreenIntentProvider.get(), (NotifPipelineFlags) this.mFlagsProvider.get(), (StatusBarNotificationChipsInteractor) this.statusBarNotificationChipsInteractorProvider.get(), (StatusBarChipsUiEventLogger) this.statusBarChipsUiEventLoggerProvider.get(), (NodeController) this.mIncomingHeaderControllerProvider.get(), (DelayableExecutor) this.mExecutorProvider.get());
    }
}
