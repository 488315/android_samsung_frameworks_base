package com.android.systemui.statusbar.pipeline.satellite.data.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1 extends TelephonyCallback implements TelephonyCallback.RadioPowerStateListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public DeviceBasedSatelliteRepositoryImpl$radioPowerState$1$cb$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    public final void onRadioPowerStateChanged(int i) {
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo3456trySendJP2dKIU(Integer.valueOf(i));
    }
}
