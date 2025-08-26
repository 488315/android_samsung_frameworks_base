package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteRegistrationStateResult;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1 extends TelephonyCallback implements TelephonyCallback.CarrierNetworkListener, TelephonyCallback.CarrierRoamingNtnListener, TelephonyCallback.DataActivityListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataEnabledListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.CallStateListener, TelephonyCallback.SemSatelliteStateListener {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;
    public final /* synthetic */ MobileInputLogger $logger;
    public final /* synthetic */ MobileConnectionRepositoryImpl $this_run;

    public MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1(MobileInputLogger mobileInputLogger, MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, ProducerScope producerScope) {
        this.$logger = mobileInputLogger;
        this.$this_run = mobileConnectionRepositoryImpl;
        this.$$this$callbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.CallStateListener
    public final void onCallStateChanged(int i) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i2 = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(20);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnCallStateChanged(i));
    }

    @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
    public final void onCarrierNetworkChange(boolean z) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(22);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnCarrierNetworkChange(z));
    }

    public final void onCarrierRoamingNtnModeChanged(boolean z) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(23);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnCarrierRoamingNtnModeChanged(z));
    }

    public final void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        MobileInputLogger mobileInputLogger = this.$logger;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(18);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = ntnSignalStrength.getLevel();
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength));
    }

    @Override // android.telephony.TelephonyCallback.DataActivityListener
    public final void onDataActivity(int i) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i2 = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.int2 = i;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnDataActivity(i));
    }

    @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
    public final void onDataConnectionStateChanged(int i, int i2) {
        this.$logger.logOnDataConnectionStateChanged(i, i2, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnDataConnectionStateChanged(i));
    }

    public final void onDataEnabledChanged(boolean z, int i) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i2 = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i2;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnDataEnabledChanged(z));
    }

    @Override // android.telephony.TelephonyCallback.DisplayInfoListener
    public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
        this.$logger.logOnDisplayInfoChanged(telephonyDisplayInfo, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnDisplayInfoChanged(telephonyDisplayInfo));
    }

    public final void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(19);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = SemSatelliteServiceState.radioStateToString(semSatelliteServiceState.getRadioState());
        SemSatelliteRegistrationStateResult registrationState = semSatelliteServiceState.getRegistrationState();
        logMessageImpl.str2 = registrationState != null ? SemSatelliteRegistrationStateResult.regStateToString(registrationState.getRegState()) : null;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.onSemSatelliteServiceStateChanged(semSatelliteServiceState));
    }

    public final void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        MobileInputLogger mobileInputLogger = this.$logger;
        int i = this.$this_run.subId;
        mobileInputLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$$ExternalSyntheticLambda0 mobileInputLogger$$ExternalSyntheticLambda0 = new MobileInputLogger$$ExternalSyntheticLambda0(17);
        LogBuffer logBuffer = mobileInputLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = semSatelliteSignalStrength.getLevel();
        logMessageImpl.int2 = i;
        logBuffer.commit(logMessageObtain);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.onSemSatelliteSignalStrengthChanged(semSatelliteSignalStrength));
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(ServiceState serviceState) {
        this.$logger.logOnServiceStateChanged(this.$this_run.subId, serviceState);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnServiceStateChanged(serviceState));
    }

    @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        this.$logger.logOnSignalStrengthsChanged(signalStrength, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new CallbackEvent.OnSignalStrengthChanged(signalStrength));
    }
}
