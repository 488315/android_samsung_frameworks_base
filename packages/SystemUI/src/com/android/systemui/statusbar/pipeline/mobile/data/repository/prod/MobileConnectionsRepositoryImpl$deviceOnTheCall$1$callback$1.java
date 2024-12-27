package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        ((ChannelCoroutine) sendChannel).mo2552trySendJP2dKIU(Boolean.valueOf(z));
    }
}
