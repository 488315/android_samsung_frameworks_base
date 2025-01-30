package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.os.PowerManager;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.cover.CoverHost;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.qs.QSHost;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.searcle.SearcleManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.QuickSettingsController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.disableflags.DisableFlagsLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import java.util.Optional;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CentralSurfacesCommandQueueCallbacks_Factory implements Provider {
    public final Provider activityStarterProvider;
    public final Provider assistManagerProvider;
    public final Provider cameraLauncherLazyProvider;
    public final Provider centralSurfacesProvider;
    public final Provider commandQueueProvider;
    public final Provider contextProvider;
    public final Provider coverHostProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider disableFlagsLoggerProvider;
    public final Provider displayIdProvider;
    public final Provider dozeServiceHostProvider;
    public final Provider headsUpManagerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider metricsLoggerProvider;
    public final Provider notificationStackScrollLayoutControllerProvider;
    public final Provider powerManagerProvider;
    public final Provider qsHostProvider;
    public final Provider quickSettingsControllerProvider;
    public final Provider remoteInputQuickSettingsDisablerProvider;
    public final Provider resourcesProvider;
    public final Provider searcleManagerProvider;
    public final Provider shadeControllerProvider;
    public final Provider shadeViewControllerProvider;
    public final Provider statusBarHideIconsForBouncerManagerProvider;
    public final Provider statusBarKeyguardViewManagerProvider;
    public final Provider systemBarAttributesListenerProvider;
    public final Provider userTrackerProvider;
    public final Provider vibratorHelperProvider;
    public final Provider vibratorOptionalProvider;
    public final Provider wakefulnessLifecycleProvider;

    public CentralSurfacesCommandQueueCallbacks_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21, Provider provider22, Provider provider23, Provider provider24, Provider provider25, Provider provider26, Provider provider27, Provider provider28, Provider provider29, Provider provider30, Provider provider31) {
        this.coverHostProvider = provider;
        this.centralSurfacesProvider = provider2;
        this.quickSettingsControllerProvider = provider3;
        this.contextProvider = provider4;
        this.resourcesProvider = provider5;
        this.shadeControllerProvider = provider6;
        this.commandQueueProvider = provider7;
        this.shadeViewControllerProvider = provider8;
        this.remoteInputQuickSettingsDisablerProvider = provider9;
        this.metricsLoggerProvider = provider10;
        this.keyguardUpdateMonitorProvider = provider11;
        this.keyguardStateControllerProvider = provider12;
        this.headsUpManagerProvider = provider13;
        this.wakefulnessLifecycleProvider = provider14;
        this.deviceProvisionedControllerProvider = provider15;
        this.statusBarKeyguardViewManagerProvider = provider16;
        this.assistManagerProvider = provider17;
        this.dozeServiceHostProvider = provider18;
        this.notificationStackScrollLayoutControllerProvider = provider19;
        this.statusBarHideIconsForBouncerManagerProvider = provider20;
        this.powerManagerProvider = provider21;
        this.vibratorHelperProvider = provider22;
        this.vibratorOptionalProvider = provider23;
        this.disableFlagsLoggerProvider = provider24;
        this.displayIdProvider = provider25;
        this.systemBarAttributesListenerProvider = provider26;
        this.cameraLauncherLazyProvider = provider27;
        this.userTrackerProvider = provider28;
        this.qsHostProvider = provider29;
        this.activityStarterProvider = provider30;
        this.searcleManagerProvider = provider31;
    }

    public static CentralSurfacesCommandQueueCallbacks newInstance(CoverHost coverHost, CentralSurfaces centralSurfaces, QuickSettingsController quickSettingsController, Context context, Resources resources, ShadeController shadeController, CommandQueue commandQueue, ShadeViewController shadeViewController, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, MetricsLogger metricsLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, HeadsUpManager headsUpManager, WakefulnessLifecycle wakefulnessLifecycle, DeviceProvisionedController deviceProvisionedController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, AssistManager assistManager, DozeServiceHost dozeServiceHost, NotificationStackScrollLayoutController notificationStackScrollLayoutController, StatusBarHideIconsForBouncerManager statusBarHideIconsForBouncerManager, PowerManager powerManager, VibratorHelper vibratorHelper, Optional optional, DisableFlagsLogger disableFlagsLogger, int i, SystemBarAttributesListener systemBarAttributesListener, Lazy lazy, UserTracker userTracker, QSHost qSHost, ActivityStarter activityStarter, SearcleManager searcleManager) {
        return new CentralSurfacesCommandQueueCallbacks(coverHost, centralSurfaces, quickSettingsController, context, resources, shadeController, commandQueue, shadeViewController, remoteInputQuickSettingsDisabler, metricsLogger, keyguardUpdateMonitor, keyguardStateController, headsUpManager, wakefulnessLifecycle, deviceProvisionedController, statusBarKeyguardViewManager, assistManager, dozeServiceHost, notificationStackScrollLayoutController, statusBarHideIconsForBouncerManager, powerManager, vibratorHelper, optional, disableFlagsLogger, i, systemBarAttributesListener, lazy, userTracker, qSHost, activityStarter, searcleManager);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((CoverHost) this.coverHostProvider.get(), (CentralSurfaces) this.centralSurfacesProvider.get(), (QuickSettingsController) this.quickSettingsControllerProvider.get(), (Context) this.contextProvider.get(), (Resources) this.resourcesProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (ShadeViewController) this.shadeViewControllerProvider.get(), (RemoteInputQuickSettingsDisabler) this.remoteInputQuickSettingsDisablerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (AssistManager) this.assistManagerProvider.get(), (DozeServiceHost) this.dozeServiceHostProvider.get(), (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get(), (StatusBarHideIconsForBouncerManager) this.statusBarHideIconsForBouncerManagerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (VibratorHelper) this.vibratorHelperProvider.get(), (Optional) this.vibratorOptionalProvider.get(), (DisableFlagsLogger) this.disableFlagsLoggerProvider.get(), ((Integer) this.displayIdProvider.get()).intValue(), (SystemBarAttributesListener) this.systemBarAttributesListenerProvider.get(), DoubleCheck.lazy(this.cameraLauncherLazyProvider), (UserTracker) this.userTrackerProvider.get(), (QSHost) this.qsHostProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SearcleManager) this.searcleManagerProvider.get());
    }
}
