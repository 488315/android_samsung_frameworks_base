package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.service.dreams.IDreamManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.shade.domain.interactor.ShadeAnimationInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class StatusBarNotificationActivityStarter_Factory implements Provider {
    public final Provider activityIntentHelperProvider;
    public final Provider activityStarterProvider;
    public final Provider activityTransitionAnimatorProvider;
    public final Provider assistManagerLazyProvider;
    public final Provider bubblesManagerOptionalProvider;
    public final Provider clickNotifierProvider;
    public final Provider commandQueueProvider;
    public final Provider contextInteractorProvider;
    public final Provider contextProvider;
    public final Provider dreamManagerProvider;
    public final Provider headsUpManagerProvider;
    public final Provider keyguardManagerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider launchFullScreenIntentProvider;
    public final Provider lockPatternUtilsProvider;
    public final Provider lockscreenUserManagerProvider;
    public final Provider loggerProvider;
    public final Provider mainThreadHandlerProvider;
    public final Provider metricsLoggerProvider;
    public final Provider notificationAnimationProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider onUserInteractionCallbackProvider;
    public final Provider panelExpansionInteractorProvider;
    public final Provider powerInteractorProvider;
    public final Provider presenterProvider;
    public final Provider remoteInputCallbackProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider shadeAnimationInteractorProvider;
    public final Provider shadeControllerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider uiBgExecutorProvider;
    public final Provider userTrackerProvider;
    public final Provider visibilityProvider;

    public StatusBarNotificationActivityStarter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34) {
        this.contextProvider = provider;
        this.contextInteractorProvider = provider2;
        this.mainThreadHandlerProvider = provider3;
        this.uiBgExecutorProvider = provider4;
        this.visibilityProvider = provider5;
        this.headsUpManagerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.commandQueueProvider = provider8;
        this.clickNotifierProvider = provider9;
        this.statusBarKeyguardViewManagerProvider = provider10;
        this.keyguardManagerProvider = provider11;
        this.dreamManagerProvider = provider12;
        this.bubblesManagerOptionalProvider = provider13;
        this.assistManagerLazyProvider = provider14;
        this.remoteInputManagerProvider = provider15;
        this.lockscreenUserManagerProvider = provider16;
        this.shadeControllerProvider = provider17;
        this.keyguardStateControllerProvider = provider18;
        this.lockPatternUtilsProvider = provider19;
        this.remoteInputCallbackProvider = provider20;
        this.activityIntentHelperProvider = provider21;
        this.metricsLoggerProvider = provider22;
        this.loggerProvider = provider23;
        this.onUserInteractionCallbackProvider = provider24;
        this.presenterProvider = provider25;
        this.panelExpansionInteractorProvider = provider26;
        this.notificationShadeWindowControllerProvider = provider27;
        this.activityTransitionAnimatorProvider = provider28;
        this.shadeAnimationInteractorProvider = provider29;
        this.notificationAnimationProvider = provider30;
        this.launchFullScreenIntentProvider = provider31;
        this.powerInteractorProvider = provider32;
        this.userTrackerProvider = provider33;
        this.statusBarStateControllerProvider = provider34;
    }

    public static StatusBarNotificationActivityStarter newInstance(Context context, ShadeDialogContextInteractor shadeDialogContextInteractor, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManager headsUpManager, ActivityStarter activityStarter, CommandQueue commandQueue, NotificationClickNotifier notificationClickNotifier, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional optional, Lazy lazy, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback, NotificationPresenter notificationPresenter, PanelExpansionInteractor panelExpansionInteractor, NotificationShadeWindowController notificationShadeWindowController, ActivityTransitionAnimator activityTransitionAnimator, ShadeAnimationInteractor shadeAnimationInteractor, NotificationLaunchAnimatorControllerProvider notificationLaunchAnimatorControllerProvider, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, PowerInteractor powerInteractor, UserTracker userTracker, SysuiStatusBarStateController sysuiStatusBarStateController) {
        return new StatusBarNotificationActivityStarter(context, shadeDialogContextInteractor, handler, executor, notificationVisibilityProvider, headsUpManager, activityStarter, commandQueue, notificationClickNotifier, statusBarKeyguardViewManager, keyguardManager, iDreamManager, optional, lazy, notificationRemoteInputManager, notificationLockscreenUserManager, shadeController, keyguardStateController, lockPatternUtils, statusBarRemoteInputCallback, activityIntentHelper, metricsLogger, statusBarNotificationActivityStarterLogger, onUserInteractionCallback, notificationPresenter, panelExpansionInteractor, notificationShadeWindowController, activityTransitionAnimator, shadeAnimationInteractor, notificationLaunchAnimatorControllerProvider, launchFullScreenIntentProvider, powerInteractor, userTracker, sysuiStatusBarStateController);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new StatusBarNotificationActivityStarter((Context) this.contextProvider.get(), (ShadeDialogContextInteractor) this.contextInteractorProvider.get(), (Handler) this.mainThreadHandlerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (Optional) this.bubblesManagerOptionalProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (StatusBarRemoteInputCallback) this.remoteInputCallbackProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarNotificationActivityStarterLogger) this.loggerProvider.get(), (OnUserInteractionCallback) this.onUserInteractionCallbackProvider.get(), (NotificationPresenter) this.presenterProvider.get(), (PanelExpansionInteractor) this.panelExpansionInteractorProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (ActivityTransitionAnimator) this.activityTransitionAnimatorProvider.get(), (ShadeAnimationInteractor) this.shadeAnimationInteractorProvider.get(), (NotificationLaunchAnimatorControllerProvider) this.notificationAnimationProvider.get(), (LaunchFullScreenIntentProvider) this.launchFullScreenIntentProvider.get(), (PowerInteractor) this.powerInteractorProvider.get(), (UserTracker) this.userTrackerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get());
    }
}
