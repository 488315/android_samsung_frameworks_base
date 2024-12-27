package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1 extends TelephonyCallback implements TelephonyCallback.CarrierNetworkListener, TelephonyCallback.CarrierRoamingNtnModeListener, TelephonyCallback.DataActivityListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataEnabledListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.CallStateListener, TelephonyCallback.SemSatelliteStateListener {
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
        this.$logger.logOnCallStateChanged(i, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnCallStateChanged(i));
    }

    @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
    public final void onCarrierNetworkChange(boolean z) {
        this.$logger.logOnCarrierNetworkChange(this.$this_run.subId, z);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnCarrierNetworkChange(z));
    }

    public final void onCarrierRoamingNtnModeChanged(boolean z) {
        this.$logger.logOnCarrierRoamingNtnModeChanged(this.$this_run.subId, z);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnCarrierRoamingNtnModeChanged(z));
    }

    public final void onCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        this.$logger.logNtnSignalStrengthChanged(ntnSignalStrength);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnCarrierRoamingNtnSignalStrengthChanged(ntnSignalStrength));
    }

    @Override // android.telephony.TelephonyCallback.DataActivityListener
    public final void onDataActivity(int i) {
        this.$logger.logOnDataActivity(i, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnDataActivity(i));
    }

    @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
    public final void onDataConnectionStateChanged(int i, int i2) {
        this.$logger.logOnDataConnectionStateChanged(i, i2, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnDataConnectionStateChanged(i));
    }

    public final void onDataEnabledChanged(boolean z, int i) {
        this.$logger.logOnDataEnabledChanged(this.$this_run.subId, z);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnDataEnabledChanged(z));
    }

    @Override // android.telephony.TelephonyCallback.DisplayInfoListener
    public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
        this.$logger.logOnDisplayInfoChanged(telephonyDisplayInfo, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnDisplayInfoChanged(telephonyDisplayInfo));
    }

    public final void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
        this.$logger.logOnSemSatelliteServiceStateChanged(semSatelliteServiceState, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.onSemSatelliteServiceStateChanged(semSatelliteServiceState));
    }

    public final void onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) {
        this.$logger.logOnSemSatelliteSignalStrengthChanged(semSatelliteSignalStrength, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.onSemSatelliteSignalStrengthChanged(semSatelliteSignalStrength));
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(ServiceState serviceState) {
        this.$logger.logOnServiceStateChanged(this.$this_run.subId, serviceState);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnServiceStateChanged(serviceState));
    }

    @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        this.$logger.logOnSignalStrengthsChanged(signalStrength, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2552trySendJP2dKIU(new CallbackEvent.OnSignalStrengthChanged(signalStrength));
    }
}
