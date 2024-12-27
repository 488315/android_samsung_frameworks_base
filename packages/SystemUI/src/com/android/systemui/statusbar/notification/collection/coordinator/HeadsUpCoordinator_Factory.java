package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpCoordinator_Factory implements Provider {
    private final javax.inject.Provider mExecutorProvider;
    private final javax.inject.Provider mFlagsProvider;
    private final javax.inject.Provider mHeadsUpManagerProvider;
    private final javax.inject.Provider mHeadsUpViewBinderProvider;
    private final javax.inject.Provider mIncomingHeaderControllerProvider;
    private final javax.inject.Provider mLaunchFullScreenIntentProvider;
    private final javax.inject.Provider mLoggerProvider;
    private final javax.inject.Provider mRemoteInputManagerProvider;
    private final javax.inject.Provider mSystemClockProvider;
    private final javax.inject.Provider mVisualInterruptionDecisionProvider;

    public HeadsUpCoordinator_Factory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        this.mLoggerProvider = provider;
        this.mSystemClockProvider = provider2;
        this.mHeadsUpManagerProvider = provider3;
        this.mHeadsUpViewBinderProvider = provider4;
        this.mVisualInterruptionDecisionProvider = provider5;
        this.mRemoteInputManagerProvider = provider6;
        this.mLaunchFullScreenIntentProvider = provider7;
        this.mFlagsProvider = provider8;
        this.mIncomingHeaderControllerProvider = provider9;
        this.mExecutorProvider = provider10;
    }

    public static HeadsUpCoordinator_Factory create(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9, javax.inject.Provider provider10) {
        return new HeadsUpCoordinator_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static HeadsUpCoordinator newInstance(HeadsUpCoordinatorLogger headsUpCoordinatorLogger, SystemClock systemClock, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, NodeController nodeController, DelayableExecutor delayableExecutor) {
        return new HeadsUpCoordinator(headsUpCoordinatorLogger, systemClock, headsUpManager, headsUpViewBinder, visualInterruptionDecisionProvider, notificationRemoteInputManager, launchFullScreenIntentProvider, notifPipelineFlags, nodeController, delayableExecutor);
    }

    @Override // javax.inject.Provider
    public HeadsUpCoordinator get() {
        return newInstance((HeadsUpCoordinatorLogger) this.mLoggerProvider.get(), (SystemClock) this.mSystemClockProvider.get(), (HeadsUpManager) this.mHeadsUpManagerProvider.get(), (HeadsUpViewBinder) this.mHeadsUpViewBinderProvider.get(), (VisualInterruptionDecisionProvider) this.mVisualInterruptionDecisionProvider.get(), (NotificationRemoteInputManager) this.mRemoteInputManagerProvider.get(), (LaunchFullScreenIntentProvider) this.mLaunchFullScreenIntentProvider.get(), (NotifPipelineFlags) this.mFlagsProvider.get(), (NodeController) this.mIncomingHeaderControllerProvider.get(), (DelayableExecutor) this.mExecutorProvider.get());
    }
}
