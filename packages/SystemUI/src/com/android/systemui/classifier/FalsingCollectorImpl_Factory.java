package com.android.systemui.classifier;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.time.SystemClock;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Provider;

/* loaded from: classes.dex */
public final class FalsingCollectorImpl_Factory implements Provider {
    public final Provider batteryControllerProvider;
    public final Provider communalInteractorLazyProvider;
    public final Provider deviceEntryInteractorProvider;
    public final Provider dockManagerProvider;
    public final Provider falsingDataProvider;
    public final Provider falsingManagerProvider;
    public final Provider historyTrackerProvider;
    public final Provider javaAdapterProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider mainExecutorProvider;
    public final Provider proximitySensorProvider;
    public final Provider sceneContainerOcclusionInteractorProvider;
    public final Provider shadeInteractorLazyProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider systemClockProvider;
    public final Provider userInteractorProvider;

    public FalsingCollectorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13, Provider provider14, Provider provider15, Provider provider16, Provider provider17) {
        this.falsingDataProvider = provider;
        this.falsingManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.historyTrackerProvider = provider4;
        this.proximitySensorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
        this.shadeInteractorLazyProvider = provider8;
        this.batteryControllerProvider = provider9;
        this.dockManagerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.javaAdapterProvider = provider12;
        this.systemClockProvider = provider13;
        this.userInteractorProvider = provider14;
        this.communalInteractorLazyProvider = provider15;
        this.deviceEntryInteractorProvider = provider16;
        this.sceneContainerOcclusionInteractorProvider = provider17;
    }

    public static FalsingCollectorImpl newInstance(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, Lazy lazy, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, JavaAdapter javaAdapter, SystemClock systemClock, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5) {
        return new FalsingCollectorImpl(falsingDataProvider, falsingManager, keyguardUpdateMonitor, historyTracker, proximitySensor, statusBarStateController, keyguardStateController, lazy, batteryController, dockManager, delayableExecutor, javaAdapter, systemClock, lazy2, lazy3, lazy4, lazy5);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new FalsingCollectorImpl((FalsingDataProvider) this.falsingDataProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (HistoryTracker) this.historyTrackerProvider.get(), (ProximitySensor) this.proximitySensorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), DoubleCheck.lazy(this.shadeInteractorLazyProvider), (BatteryController) this.batteryControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (JavaAdapter) this.javaAdapterProvider.get(), (SystemClock) this.systemClockProvider.get(), DoubleCheck.lazy(this.userInteractorProvider), DoubleCheck.lazy(this.communalInteractorLazyProvider), DoubleCheck.lazy(this.deviceEntryInteractorProvider), DoubleCheck.lazy(this.sceneContainerOcclusionInteractorProvider));
    }
}
