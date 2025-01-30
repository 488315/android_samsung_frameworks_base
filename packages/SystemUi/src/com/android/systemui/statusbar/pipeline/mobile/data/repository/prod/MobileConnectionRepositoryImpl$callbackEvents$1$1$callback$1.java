package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.CallbackEvent;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileConnectionRepositoryImpl$callbackEvents$1$1$callback$1 extends TelephonyCallback implements TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataActivityListener, TelephonyCallback.CarrierNetworkListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.DataEnabledListener, TelephonyCallback.CallStateListener {
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
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnCallStateChanged(i));
    }

    @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
    public final void onCarrierNetworkChange(boolean z) {
        this.$logger.logOnCarrierNetworkChange(this.$this_run.subId, z);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnCarrierNetworkChange(z));
    }

    @Override // android.telephony.TelephonyCallback.DataActivityListener
    public final void onDataActivity(int i) {
        this.$logger.logOnDataActivity(i, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnDataActivity(i));
    }

    @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
    public final void onDataConnectionStateChanged(int i, int i2) {
        this.$logger.logOnDataConnectionStateChanged(i, i2, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnDataConnectionStateChanged(i));
    }

    public final void onDataEnabledChanged(boolean z, int i) {
        this.$logger.logOnDataEnabledChanged(this.$this_run.subId, z);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnDataEnabledChanged(z));
    }

    @Override // android.telephony.TelephonyCallback.DisplayInfoListener
    public final void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
        this.$logger.logOnDisplayInfoChanged(telephonyDisplayInfo, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnDisplayInfoChanged(telephonyDisplayInfo));
    }

    @Override // android.telephony.TelephonyCallback.ServiceStateListener
    public final void onServiceStateChanged(ServiceState serviceState) {
        this.$logger.logOnServiceStateChanged(this.$this_run.subId, serviceState);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnServiceStateChanged(serviceState));
    }

    @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        this.$logger.logOnSignalStrengthsChanged(signalStrength, this.$this_run.subId);
        ((ChannelCoroutine) this.$$this$callbackFlow).mo2872trySendJP2dKIU(new CallbackEvent.OnSignalStrengthChanged(signalStrength));
    }
}
