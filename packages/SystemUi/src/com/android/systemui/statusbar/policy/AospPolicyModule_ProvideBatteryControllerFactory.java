package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.power.EnhancedEstimates;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AospPolicyModule_ProvideBatteryControllerFactory implements Provider {
    public final Provider bgHandlerProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider demoModeControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider enhancedEstimatesProvider;
    public final Provider mainHandlerProvider;
    public final Provider powerManagerProvider;

    public AospPolicyModule_ProvideBatteryControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.enhancedEstimatesProvider = provider2;
        this.powerManagerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.demoModeControllerProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.mainHandlerProvider = provider7;
        this.bgHandlerProvider = provider8;
    }

    public static BatteryControllerImpl provideBatteryController(Context context, EnhancedEstimates enhancedEstimates, PowerManager powerManager, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, DumpManager dumpManager, Handler handler, Handler handler2) {
        Intent registerReceiver;
        BatteryControllerImpl batteryControllerImpl = new BatteryControllerImpl(context, enhancedEstimates, powerManager, broadcastDispatcher, demoModeController, dumpManager, handler, handler2);
        batteryControllerImpl.registerReceiver$2();
        if (!batteryControllerImpl.mHasReceivedBattery && (registerReceiver = batteryControllerImpl.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null && !batteryControllerImpl.mHasReceivedBattery) {
            batteryControllerImpl.onReceive(batteryControllerImpl.mContext, registerReceiver);
        }
        batteryControllerImpl.mDemoModeController.addCallback((DemoMode) batteryControllerImpl);
        DumpManager dumpManager2 = batteryControllerImpl.mDumpManager;
        dumpManager2.getClass();
        DumpManager.registerDumpable$default(dumpManager2, "BatteryController", batteryControllerImpl);
        batteryControllerImpl.updatePowerSave();
        if (!batteryControllerImpl.mFetchingEstimate) {
            batteryControllerImpl.mFetchingEstimate = true;
            batteryControllerImpl.mBgHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, 0));
        }
        return batteryControllerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBatteryController((Context) this.contextProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
