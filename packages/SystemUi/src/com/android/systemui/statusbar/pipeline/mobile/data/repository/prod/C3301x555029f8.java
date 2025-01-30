package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl$activeMobileDataSubscriptionId$1$callback$1 */
/* loaded from: classes2.dex */
public final class C3301x555029f8 extends TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;

    public C3301x555029f8(ProducerScope producerScope) {
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        if (i == -1) {
            ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2872trySendJP2dKIU(null);
            return;
        }
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2872trySendJP2dKIU(Integer.valueOf(i));
    }
}
