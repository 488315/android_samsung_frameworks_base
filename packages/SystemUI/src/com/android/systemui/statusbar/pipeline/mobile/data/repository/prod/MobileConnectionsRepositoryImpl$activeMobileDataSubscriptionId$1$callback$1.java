package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 extends TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        if (i == -1) {
            ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo3456trySendJP2dKIU(null);
            return;
        }
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo3456trySendJP2dKIU(Integer.valueOf(i));
    }
}
