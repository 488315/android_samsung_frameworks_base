package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.PowerManager;
import com.android.systemui.Flags;
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
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class AospPolicyModule_ProvideBatteryControllerFactory implements Provider {
    public final javax.inject.Provider bgHandlerProvider;
    public final javax.inject.Provider broadcastDispatcherProvider;
    public final javax.inject.Provider contextProvider;
    public final javax.inject.Provider demoModeControllerProvider;
    public final javax.inject.Provider dumpManagerProvider;
    public final javax.inject.Provider enhancedEstimatesProvider;
    public final javax.inject.Provider loggerProvider;
    public final javax.inject.Provider mainHandlerProvider;
    public final javax.inject.Provider powerManagerProvider;

    public AospPolicyModule_ProvideBatteryControllerFactory(javax.inject.Provider provider, javax.inject.Provider provider2, javax.inject.Provider provider3, javax.inject.Provider provider4, javax.inject.Provider provider5, javax.inject.Provider provider6, javax.inject.Provider provider7, javax.inject.Provider provider8, javax.inject.Provider provider9) {
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
        Intent registerReceiver;
        BatteryControllerImpl batteryControllerImpl = new BatteryControllerImpl(context, enhancedEstimates, powerManager, broadcastDispatcher, demoModeController, dumpManager, batteryControllerLogger, handler, handler2);
        BatteryControllerLogger batteryControllerLogger2 = batteryControllerImpl.mLogger;
        boolean z = batteryControllerImpl.mHasReceivedBattery;
        batteryControllerLogger2.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BatteryControllerLogger$logBatteryControllerInit$2 batteryControllerLogger$logBatteryControllerInit$2 = new Function1() { // from class: com.android.systemui.statusbar.policy.BatteryControllerLogger$logBatteryControllerInit$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "BatteryController INIT (" + Integer.toHexString(logMessage.getInt1()) + ") hasReceivedBattery=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = batteryControllerLogger2.logBuffer;
        LogMessage obtain = logBuffer.obtain("BatteryControllerLog", logLevel, batteryControllerLogger$logBatteryControllerInit$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = System.identityHashCode(batteryControllerImpl);
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        Flags.FEATURE_FLAGS.getClass();
        batteryControllerImpl.registerReceiver$1();
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
        return provideBatteryController((Context) this.contextProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (PowerManager) this.powerManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BatteryControllerLogger) this.loggerProvider.get(), (Handler) this.mainHandlerProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}
