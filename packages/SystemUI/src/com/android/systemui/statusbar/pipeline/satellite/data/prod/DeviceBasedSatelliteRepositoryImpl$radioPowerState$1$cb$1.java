package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

public final class DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1 extends TelephonyCallback implements TelephonyCallback.RadioPowerStateListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    public final void onRadioPowerStateChanged(int i) {
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(Integer.valueOf(i));
    }
}
