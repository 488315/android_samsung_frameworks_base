package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.TelephonyCallback;
import java.time.Duration;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
public final class MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1 extends TelephonyCallback implements TelephonyCallback.ActiveDataSubscriptionIdListener, TelephonyCallback.EmergencyCallbackModeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final /* synthetic */ ProducerScope $$this$callbackFlow;

    public MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
    public final void onActiveDataSubscriptionIdChanged(int i) {
        if (i != -1) {
            ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda0(i, 2));
        } else {
            ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda3());
        }
    }

    public final void onCallbackModeStarted(int i, Duration duration, int i2) {
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda0(i, 1));
    }

    public final void onCallbackModeStopped(int i, int i2, int i3) {
        ((ChannelCoroutine) this.$$this$callbackFlow).mo3476trySendJP2dKIU(new MobileConnectionsRepositoryKairosImpl$telephonyManagerState$1$1$callback$1$$ExternalSyntheticLambda0(i, 0));
    }

    public final void onCallbackModeRestarted(int i, Duration duration, int i2) {
    }
}
