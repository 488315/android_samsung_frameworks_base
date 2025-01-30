package com.android.systemui.classifier;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.time.SystemClock;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FalsingCollectorImpl_Factory implements Provider {
    public final Provider batteryControllerProvider;
    public final Provider dockManagerProvider;
    public final Provider falsingDataProvider;
    public final Provider falsingManagerProvider;
    public final Provider historyTrackerProvider;
    public final Provider keyguardStateControllerProvider;
    public final Provider keyguardUpdateMonitorProvider;
    public final Provider mainExecutorProvider;
    public final Provider proximitySensorProvider;
    public final Provider shadeExpansionStateManagerProvider;
    public final Provider statusBarStateControllerProvider;
    public final Provider systemClockProvider;

    public FalsingCollectorImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12) {
        this.falsingDataProvider = provider;
        this.falsingManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.historyTrackerProvider = provider4;
        this.proximitySensorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
        this.shadeExpansionStateManagerProvider = provider8;
        this.batteryControllerProvider = provider9;
        this.dockManagerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.systemClockProvider = provider12;
    }

    public static FalsingCollectorImpl newInstance(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, ShadeExpansionStateManager shadeExpansionStateManager, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new FalsingCollectorImpl(falsingDataProvider, falsingManager, keyguardUpdateMonitor, historyTracker, proximitySensor, statusBarStateController, keyguardStateController, shadeExpansionStateManager, batteryController, dockManager, delayableExecutor, systemClock);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return newInstance((FalsingDataProvider) this.falsingDataProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (HistoryTracker) this.historyTrackerProvider.get(), (ProximitySensor) this.proximitySensorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}
