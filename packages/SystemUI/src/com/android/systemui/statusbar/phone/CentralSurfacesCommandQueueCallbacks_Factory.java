package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.os.PowerManager;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.emergency.EmergencyGestureModule;
import com.android.systemui.emergency.EmergencyGestureModule$emergencyGestureIntentFactory$1;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class CentralSurfacesCommandQueueCallbacks_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider assistManagerProvider;
    public final Provider cameraLauncherLazyProvider;
    public final Provider centralSurfacesProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider coverHostProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider displayIdProvider;
    public final Provider dozeServiceHostProvider;
    public final Provider emergencyGestureIntentFactoryProvider;
    public final Provider headsUpManagerProvider;
    public final Provider keyguardInteractorProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider metricsLoggerProvider;
    public final Provider notificationStackScrollLayoutControllerProvider;
    public final Provider panelExpansionInteractorProvider;
    public final Provider powerManagerProvider;
    public final Provider qsHostProvider;
    public final Provider quickSettingsControllerProvider;
    public final Provider remoteInputQuickSettingsDisablerProvider;
    public final Provider resourcesProvider;
    public final Provider screenPinningRequestProvider;
    public final Provider searcleManagerProvider;
    public final Provider shadeControllerProvider;
    public final Provider shadeHeaderControllerProvider;
    public final Provider shadeInteractorLazyProvider;
    public final Provider statusBarHideIconsForBouncerManagerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider subScreenManagerLazyProvider;
    public final Provider userTrackerProvider;
    public final Provider vibratorOptionalProvider;
    public final Provider wakefulnessLifecycleProvider;
    public final Provider walletControllerProvider;

    public CentralSurfacesCommandQueueCallbacks_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31, Provider provider32, Provider provider33, Provider provider34, Provider provider35) {
        this.centralSurfacesProvider = provider;
        this.quickSettingsControllerProvider = provider2;
        this.contextProvider = provider3;
        this.resourcesProvider = provider4;
        this.screenPinningRequestProvider = provider5;
        this.shadeControllerProvider = provider6;
        this.commandQueueProvider = provider7;
        this.panelExpansionInteractorProvider = provider8;
        this.shadeInteractorLazyProvider = provider9;
        this.shadeHeaderControllerProvider = provider10;
        this.remoteInputQuickSettingsDisablerProvider = provider11;
        this.metricsLoggerProvider = provider12;
        this.keyguardUpdateMonitorProvider = provider13;
        this.keyguardStateControllerProvider = provider14;
        this.headsUpManagerProvider = provider15;
        this.wakefulnessLifecycleProvider = provider16;
        this.deviceProvisionedControllerProvider = provider17;
        this.statusBarKeyguardViewManagerProvider = provider18;
        this.assistManagerProvider = provider19;
        this.dozeServiceHostProvider = provider20;
        this.notificationStackScrollLayoutControllerProvider = provider21;
        this.statusBarHideIconsForBouncerManagerProvider = provider22;
        this.powerManagerProvider = provider23;
        this.vibratorOptionalProvider = provider24;
        this.displayIdProvider = provider25;
        this.cameraLauncherLazyProvider = provider26;
        this.userTrackerProvider = provider27;
        this.qsHostProvider = provider28;
        this.activityStarterProvider = provider29;
        this.keyguardInteractorProvider = provider30;
        this.emergencyGestureIntentFactoryProvider = provider31;
        this.walletControllerProvider = provider32;
        this.searcleManagerProvider = provider33;
        this.coverHostProvider = provider34;
        this.subScreenManagerLazyProvider = provider35;
    }

    public static CentralSurfacesCommandQueueCallbacks newInstance(CentralSurfaces centralSurfaces, QuickSettingsController quickSettingsController, Context context, Resources resources, ScreenPinningRequest screenPinningRequest, ShadeController shadeController, CommandQueue commandQueue, PanelExpansionInteractor panelExpansionInteractor, Lazy lazy, ShadeHeaderController shadeHeaderController, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, MetricsLogger metricsLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, WakefulnessLifecycle wakefulnessLifecycle, DeviceProvisionedController deviceProvisionedController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AssistManager assistManager, DozeServiceHost dozeServiceHost, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, PowerManager powerManager, Optional optional, int i, Lazy lazy2, UserTracker userTracker, QSHost qSHost, ActivityStarter activityStarter, KeyguardInteractor keyguardInteractor, EmergencyGestureModule$emergencyGestureIntentFactory$1 emergencyGestureModule$emergencyGestureIntentFactory$1, QuickAccessWalletController quickAccessWalletController, SearcleManager searcleManager, CoverHost coverHost, Lazy lazy3) {
        return new CentralSurfacesCommandQueueCallbacks(centralSurfaces, quickSettingsController, context, resources, screenPinningRequest, shadeController, commandQueue, panelExpansionInteractor, lazy, shadeHeaderController, remoteInputQuickSettingsDisabler, metricsLogger, keyguardUpdateMonitor, keyguardStateController, headsUpManager, wakefulnessLifecycle, deviceProvisionedController, statusBarKeyguardViewManager, assistManager, dozeServiceHost, notificationStackScrollLayoutController, statusBarHideIconsForBouncerManager, powerManager, optional, i, lazy2, userTracker, qSHost, activityStarter, keyguardInteractor, emergencyGestureModule$emergencyGestureIntentFactory$1, quickAccessWalletController, searcleManager, coverHost, lazy3);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new CentralSurfacesCommandQueueCallbacks((CentralSurfaces) this.centralSurfacesProvider.get(), (QuickSettingsController) this.quickSettingsControllerProvider.get(), (Context) this.contextProvider.get(), (Resources) this.resourcesProvider.get(), (ScreenPinningRequest) this.screenPinningRequestProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (PanelExpansionInteractor) this.panelExpansionInteractorProvider.get(), DoubleCheck.lazy(this.shadeInteractorLazyProvider), (ShadeHeaderController) this.shadeHeaderControllerProvider.get(), (RemoteInputQuickSettingsDisabler) this.remoteInputQuickSettingsDisablerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (AssistManager) this.assistManagerProvider.get(), (DozeServiceHost) this.dozeServiceHostProvider.get(), (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get(), (StatusBarHideIconsForBouncerManager) this.statusBarHideIconsForBouncerManagerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (Optional) this.vibratorOptionalProvider.get(), ((Integer) this.displayIdProvider.get()).intValue(), DoubleCheck.lazy(this.cameraLauncherLazyProvider), (UserTracker) this.userTrackerProvider.get(), (QSHost) this.qsHostProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (EmergencyGestureModule.EmergencyGestureIntentFactory) this.emergencyGestureIntentFactoryProvider.get(), (QuickAccessWalletController) this.walletControllerProvider.get(), (SearcleManager) this.searcleManagerProvider.get(), (CoverHost) this.coverHostProvider.get(), DoubleCheck.lazy(this.subScreenManagerLazyProvider));
    }
}
