package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import com.android.systemui.kairos.CoalescingEventProducerScope;
import com.android.systemui.kairos.internal.BuildScopeImpl$coalescingEvents$1$1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import java.time.Duration;

/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1 extends TelephonyCallback implements TelephonyCallback.CarrierNetworkListener, TelephonyCallback.CarrierRoamingNtnListener, TelephonyCallback.DataActivityListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataEnabledListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.EmergencyCallbackModeListener {
    public final /* synthetic */ CoalescingEventProducerScope $$this$coalescingEvents;
    public final /* synthetic */ MobileInputLogger $logger;
    public final /* synthetic */ MobileConnectionRepositoryKairosImpl this$0;

    public MobileConnectionRepositoryKairosImpl$callbackEvents$1$2$callback$1(MobileInputLogger mobileInputLogger, MobileConnectionRepositoryKairosImpl mobileConnectionRepositoryKairosImpl, CoalescingEventProducerScope coalescingEventProducerScope) {
        this.$logger = mobileInputLogger;
        this.this$0 = mobileConnectionRepositoryKairosImpl;
        this.$$this$coalescingEvents = coalescingEventProducerScope;
    }

    public final void onCallbackModeStopped(int i, int i2, int i3) {
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnCallBackModeStopped(i));
    }

    @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
    public final void onCarrierNetworkChange(boolean z) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.this$0.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(22);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnCarrierNetworkChange(z));
    }

    public final void onCarrierRoamingNtnModeChanged(boolean z) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.this$0.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(23);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnCarrierRoamingNtnModeChanged(z));
    }

    @Override // android.telephony.TelephonyCallback.DataActivityListener
    public final void onDataActivity(int i) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i2 = this.this$0.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i;
        logBuffer.commit(logMessageObtain);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnDataActivity(i));
    }

    @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
    public final void onDataConnectionStateChanged(int i, int i2) {
        this.$logger.logOnDataConnectionStateChanged(i, i2, this.this$0.subId);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnDataConnectionStateChanged(i));
    }

    public final void onDataEnabledChanged(boolean z, int i) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i2 = this.this$0.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnDataEnabledChanged(z));
    }

    @Override // android.telephony.TelephonyCallback.DisplayInfoListener
    public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
        this.$logger.logOnDisplayInfoChanged(telephonyDisplayInfo, this.this$0.subId);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnDisplayInfoChanged(telephonyDisplayInfo));
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(ServiceState serviceState) {
        this.$logger.logOnServiceStateChanged(this.this$0.subId, serviceState);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnServiceStateChanged(serviceState));
    }

    @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        this.$logger.logOnSignalStrengthsChanged(signalStrength, this.this$0.subId);
        ((BuildScopeImpl$coalescingEvents$1$1) this.$$this$coalescingEvents).emit(new CallbackEvent.OnSignalStrengthChanged(signalStrength));
    }

    public final void onCallbackModeRestarted(int i, Duration duration, int i2) {
    }

    public final void onCallbackModeStarted(int i, Duration duration, int i2) {
    }
}
