package com.android.systemui.statusbar.phone;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.service.dreams.IDreamManager;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarNotificationActivityStarter_Factory implements Provider {
    public final Provider activityIntentHelperProvider;
    public final Provider activityLaunchAnimatorProvider;
    public final Provider activityStarterProvider;
    public final Provider assistManagerLazyProvider;
    public final Provider bubblesManagerOptionalProvider;
    public final Provider centralSurfacesProvider;
    public final Provider clickNotifierProvider;
    public final Provider contextProvider;
    public final Provider dreamManagerProvider;
    public final Provider featureFlagsProvider;
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
    public final Provider notificationInterruptStateProvider;
    public final Provider onUserInteractionCallbackProvider;
    public final Provider panelProvider;
    public final Provider presenterProvider;
    public final Provider remoteInputCallbackProvider;
    public final Provider remoteInputManagerProvider;
    public final Provider shadeControllerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider uiBgExecutorProvider;
    public final Provider userTrackerProvider;
    public final Provider visibilityProvider;

    public StatusBarNotificationActivityStarter_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31) {
        this.contextProvider = provider;
        this.mainThreadHandlerProvider = provider2;
        this.uiBgExecutorProvider = provider3;
        this.visibilityProvider = provider4;
        this.headsUpManagerProvider = provider5;
        this.activityStarterProvider = provider6;
        this.clickNotifierProvider = provider7;
        this.statusBarKeyguardViewManagerProvider = provider8;
        this.keyguardManagerProvider = provider9;
        this.dreamManagerProvider = provider10;
        this.bubblesManagerOptionalProvider = provider11;
        this.assistManagerLazyProvider = provider12;
        this.remoteInputManagerProvider = provider13;
        this.lockscreenUserManagerProvider = provider14;
        this.shadeControllerProvider = provider15;
        this.keyguardStateControllerProvider = provider16;
        this.notificationInterruptStateProvider = provider17;
        this.lockPatternUtilsProvider = provider18;
        this.remoteInputCallbackProvider = provider19;
        this.activityIntentHelperProvider = provider20;
        this.metricsLoggerProvider = provider21;
        this.loggerProvider = provider22;
        this.onUserInteractionCallbackProvider = provider23;
        this.centralSurfacesProvider = provider24;
        this.presenterProvider = provider25;
        this.panelProvider = provider26;
        this.activityLaunchAnimatorProvider = provider27;
        this.notificationAnimationProvider = provider28;
        this.launchFullScreenIntentProvider = provider29;
        this.featureFlagsProvider = provider30;
        this.userTrackerProvider = provider31;
    }

    public static StatusBarNotificationActivityStarter newInstance(Context context, Handler handler, Executor executor, NotificationVisibilityProvider notificationVisibilityProvider, HeadsUpManagerPhone headsUpManagerPhone, ActivityStarter activityStarter, NotificationClickNotifier notificationClickNotifier, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardManager keyguardManager, IDreamManager iDreamManager, Optional optional, Lazy lazy, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, ShadeController shadeController, KeyguardStateController keyguardStateController, NotificationInterruptStateProvider notificationInterruptStateProvider, LockPatternUtils lockPatternUtils, StatusBarRemoteInputCallback statusBarRemoteInputCallback, ActivityIntentHelper activityIntentHelper, MetricsLogger metricsLogger, StatusBarNotificationActivityStarterLogger statusBarNotificationActivityStarterLogger, OnUserInteractionCallback onUserInteractionCallback, CentralSurfaces centralSurfaces, NotificationPresenter notificationPresenter, ShadeViewController shadeViewController, ActivityLaunchAnimator activityLaunchAnimator, NotificationLaunchAnimatorControllerProvider notificationLaunchAnimatorControllerProvider, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, FeatureFlags featureFlags, UserTracker userTracker) {
        return new StatusBarNotificationActivityStarter(context, handler, executor, notificationVisibilityProvider, headsUpManagerPhone, activityStarter, notificationClickNotifier, statusBarKeyguardViewManager, keyguardManager, iDreamManager, optional, lazy, notificationRemoteInputManager, notificationLockscreenUserManager, shadeController, keyguardStateController, notificationInterruptStateProvider, lockPatternUtils, statusBarRemoteInputCallback, activityIntentHelper, metricsLogger, statusBarNotificationActivityStarterLogger, onUserInteractionCallback, centralSurfaces, notificationPresenter, shadeViewController, activityLaunchAnimator, notificationLaunchAnimatorControllerProvider, launchFullScreenIntentProvider, featureFlags, userTracker);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.mainThreadHandlerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (HeadsUpManagerPhone) this.headsUpManagerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (NotificationClickNotifier) this.clickNotifierProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (Optional) this.bubblesManagerOptionalProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NotificationInterruptStateProvider) this.notificationInterruptStateProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (StatusBarRemoteInputCallback) this.remoteInputCallbackProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarNotificationActivityStarterLogger) this.loggerProvider.get(), (OnUserInteractionCallback) this.onUserInteractionCallbackProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (NotificationPresenter) this.presenterProvider.get(), (ShadeViewController) this.panelProvider.get(), (ActivityLaunchAnimator) this.activityLaunchAnimatorProvider.get(), (NotificationLaunchAnimatorControllerProvider) this.notificationAnimationProvider.get(), (LaunchFullScreenIntentProvider) this.launchFullScreenIntentProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
