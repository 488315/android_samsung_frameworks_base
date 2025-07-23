package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1 extends TelephonyCallback implements TelephonyCallback.CallStateListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public MobileConnectionsRepositoryImpl$deviceOnTheCall$1$callback$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.CallStateListener
    public final void onCallStateChanged(int i) {
        SendChannel sendChannel = this.$$this$conflatedCallbackFlow;
        boolean z = true;
        if (i != 2 && i != 1) {
            z = false;
        }
        ((ChannelCoroutine) sendChannel).mo3456trySendJP2dKIU(Boolean.valueOf(z));
    }
}
