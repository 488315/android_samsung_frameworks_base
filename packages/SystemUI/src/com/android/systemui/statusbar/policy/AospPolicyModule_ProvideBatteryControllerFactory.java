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
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.power.EnhancedEstimates;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class AospPolicyModule_ProvideBatteryControllerFactory implements Provider {
    public final Provider bgHandlerProvider;
    public final Provider broadcastDispatcherProvider;
    public final Provider contextProvider;
    public final Provider demoModeControllerProvider;
    public final Provider dumpManagerProvider;
    public final Provider enhancedEstimatesProvider;
    public final Provider loggerProvider;
    public final Provider mainHandlerProvider;
    public final Provider powerManagerProvider;

    public AospPolicyModule_ProvideBatteryControllerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.contextProvider = provider;
        this.enhancedEstimatesProvider = provider2;
        this.powerManagerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.demoModeControllerProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.loggerProvider = provider7;
        this.mainHandlerProvider = provider8;
        this.bgHandlerProvider = provider9;
    }

    public static BatteryControllerImpl provideBatteryController(Context context, EnhancedEstimates enhancedEstimates, PowerManager powerManager, BroadcastDispatcher broadcastDispatcher, DemoModeController demoModeController, DumpManager dumpManager, BatteryControllerLogger batteryControllerLogger, Handler handler, Handler handler2) {
        Intent intentRegisterReceiver;
        BatteryControllerImpl batteryControllerImpl = new BatteryControllerImpl(context, enhancedEstimates, powerManager, broadcastDispatcher, demoModeController, dumpManager, batteryControllerLogger, handler, handler2);
        BatteryControllerLogger batteryControllerLogger2 = batteryControllerImpl.mLogger;
        boolean z = batteryControllerImpl.mHasReceivedBattery;
        batteryControllerLogger2.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BatteryControllerLogger$$ExternalSyntheticLambda1 batteryControllerLogger$$ExternalSyntheticLambda1 = new BatteryControllerLogger$$ExternalSyntheticLambda1(3);
        LogBuffer logBuffer = batteryControllerLogger2.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = System.identityHashCode(batteryControllerImpl);
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        batteryControllerImpl.registerReceiver$1();
        if (!batteryControllerImpl.mHasReceivedBattery && (intentRegisterReceiver = batteryControllerImpl.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null && !batteryControllerImpl.mHasReceivedBattery) {
            batteryControllerImpl.onReceive(batteryControllerImpl.mContext, intentRegisterReceiver);
        }
        batteryControllerImpl.mDemoModeController.addCallback((DemoMode) batteryControllerImpl);
        DumpManager dumpManager2 = batteryControllerImpl.mDumpManager;
        dumpManager2.getClass();
        DumpManager.registerDumpable$default(dumpManager2, "BatteryController", batteryControllerImpl);
        batteryControllerImpl.updatePowerSave();
        if (batteryControllerImpl.mFetchingEstimate) {
            return batteryControllerImpl;
        }
        batteryControllerImpl.mFetchingEstimate = true;
        batteryControllerImpl.mBgHandler.post(new BatteryControllerImpl$$ExternalSyntheticLambda0(batteryControllerImpl, 0));
        return batteryControllerImpl;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideBatteryController((Context) this.contextProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BatteryControllerLogger) this.loggerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
