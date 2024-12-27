package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 extends TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        if (i == -1) {
            ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(null);
            return;
        }
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(Integer.valueOf(i));
    }
}
