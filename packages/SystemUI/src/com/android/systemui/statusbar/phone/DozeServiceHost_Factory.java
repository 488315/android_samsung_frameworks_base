package com.android.systemui.statusbar.phone;

import android.os.PowerManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class DozeServiceHost_Factory implements Provider {
    public final Provider assistManagerLazyProvider;
    public final Provider authControllerProvider;
    public final Provider batteryControllerProvider;
    public final Provider biometricUnlockControllerLazyProvider;
    public final Provider deviceProvisionedControllerProvider;
    public final Provider dozeInteractorProvider;
    public final Provider dozeLogProvider;
    public final Provider dozeScrimControllerProvider;
    public final Provider headsUpManagerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider mLooperSlowLogControllerProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider notificationIconAreaControllerProvider;
    public final Provider notificationShadeWindowControllerProvider;
    public final Provider notificationWakeUpCoordinatorProvider;
    public final Provider powerManagerProvider;
    public final Provider pulseExpansionHandlerProvider;
    public final Provider scrimControllerProvider;
    public final Provider shadeLockscreenInteractorProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider wakefulnessLifecycleProvider;

    public DozeServiceHost_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17, Provider provider18, Provider provider19, Provider provider20, Provider provider21) {
        this.dozeLogProvider = provider;
        this.powerManagerProvider = provider2;
        this.wakefulnessLifecycleProvider = provider3;
        this.statusBarStateControllerProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
        this.headsUpManagerProvider = provider6;
        this.batteryControllerProvider = provider7;
        this.scrimControllerProvider = provider8;
        this.biometricUnlockControllerLazyProvider = provider9;
        this.assistManagerLazyProvider = provider10;
        this.dozeScrimControllerProvider = provider11;
        this.keyguardUpdateMonitorProvider = provider12;
        this.pulseExpansionHandlerProvider = provider13;
        this.notificationShadeWindowControllerProvider = provider14;
        this.notificationWakeUpCoordinatorProvider = provider15;
        this.authControllerProvider = provider16;
        this.notificationIconAreaControllerProvider = provider17;
        this.shadeLockscreenInteractorProvider = provider18;
        this.dozeInteractorProvider = provider19;
        this.mLooperSlowLogControllerProvider = provider20;
        this.mPluginAODManagerLazyProvider = provider21;
    }

    public static DozeServiceHost newInstance(DozeLog dozeLog, PowerManager powerManager, WakefulnessLifecycle wakefulnessLifecycle, SysuiStatusBarStateController sysuiStatusBarStateController, DeviceProvisionedController deviceProvisionedController, HeadsUpManager headsUpManager, BatteryController batteryController, ScrimController scrimController, Lazy lazy, Lazy lazy2, DozeScrimController dozeScrimController, KeyguardUpdateMonitor keyguardUpdateMonitor, PulseExpansionHandler pulseExpansionHandler, NotificationShadeWindowController notificationShadeWindowController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, AuthController authController, NotificationIconAreaController notificationIconAreaController, ShadeLockscreenInteractor shadeLockscreenInteractor, DozeInteractor dozeInteractor) {
        return new DozeServiceHost(dozeLog, powerManager, wakefulnessLifecycle, sysuiStatusBarStateController, deviceProvisionedController, headsUpManager, batteryController, scrimController, lazy, lazy2, dozeScrimController, keyguardUpdateMonitor, pulseExpansionHandler, notificationShadeWindowController, notificationWakeUpCoordinator, authController, notificationIconAreaController, shadeLockscreenInteractor, dozeInteractor);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DozeServiceHost dozeServiceHost = new DozeServiceHost((DozeLog) this.dozeLogProvider.get(), (PowerManager) this.powerManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (ScrimController) this.scrimControllerProvider.get(), DoubleCheck.lazy(this.biometricUnlockControllerLazyProvider), DoubleCheck.lazy(this.assistManagerLazyProvider), (DozeScrimController) this.dozeScrimControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (PulseExpansionHandler) this.pulseExpansionHandlerProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (NotificationWakeUpCoordinator) this.notificationWakeUpCoordinatorProvider.get(), (AuthController) this.authControllerProvider.get(), (NotificationIconAreaController) this.notificationIconAreaControllerProvider.get(), (ShadeLockscreenInteractor) this.shadeLockscreenInteractorProvider.get(), (DozeInteractor) this.dozeInteractorProvider.get());
        dozeServiceHost.mLooperSlowLogController = (LooperSlowLogController) this.mLooperSlowLogControllerProvider.get();
        dozeServiceHost.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        return dozeServiceHost;
    }
}
